package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.internal.HtmlDeepParser;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.internal.HtmlDeepParser.HtmlMatch.COMMENT;
import static com.vladsch.flexmark.parser.internal.HtmlDeepParser.HtmlMatch.OPEN_TAG;

public class HtmlBlockParser extends AbstractBlockParser {

    public static final String HTML_COMMENT_OPEN = "<!--";
    public static final String HTML_COMMENT_CLOSE = "-->";

    private static class Patterns {
        public final int COMMENT_PATTERN_INDEX;
        public final Pattern[][] BLOCK_PATTERNS;

        public Patterns(Parsing parsing, DataHolder options) {
            this.COMMENT_PATTERN_INDEX = 2;

            // dynamic block tags
            StringBuilder sb = new StringBuilder();
            String delimiter = "";
            for (String tag : Parser.HTML_BLOCK_TAGS.getFrom(options)) {
                sb.append(delimiter)
                        .append("\\Q")
                        .append(tag)
                        .append("\\E");
                delimiter = "|";
            }

            boolean forTranslator = Parser.HTML_FOR_TRANSLATOR.getFrom(options);
            if (forTranslator) {
                sb.append(delimiter)
                        .append(Parser.TRANSLATION_HTML_BLOCK_TAG_PATTERN.getFrom(options));
                delimiter = "|";
            }

            String blockTags = sb.toString();

            this.BLOCK_PATTERNS = new Pattern[][] {
                    { null, null }, // not used (no type 0)
                    {
                            Pattern.compile("^<(?:script|pre|style)(?:\\s|>|$)", Pattern.CASE_INSENSITIVE),
                            Pattern.compile("</(?:script|pre|style)>", Pattern.CASE_INSENSITIVE)
                    },
                    {
                            Pattern.compile("^" + HTML_COMMENT_OPEN),
                            Pattern.compile(HTML_COMMENT_CLOSE)
                    },
                    {
                            Pattern.compile("^<[?]"),
                            Pattern.compile("\\?>")
                    },
                    {
                            Pattern.compile("^<![A-Z]"),
                            Pattern.compile(">")
                    },
                    {
                            Pattern.compile("^<!\\[CDATA\\["),
                            Pattern.compile("\\]\\]>")
                    },
                    {
                            Pattern.compile("^</?(?:" + blockTags + ")(?:\\s|[/]?[>]|$)", Pattern.CASE_INSENSITIVE),
                            null // terminated by blank line
                    },
                    {
                            Pattern.compile("^(?:" + parsing.OPENTAG + '|' + parsing.CLOSETAG + ")\\s*$", Pattern.CASE_INSENSITIVE),
                            null // terminated by blank line
                    }
            };
        }
    }

    private final HtmlBlockBase block;
    private final Pattern closingPattern;
    private final HtmlDeepParser deepParser;

    private boolean finished = false;
    private BlockContent content = new BlockContent();
    private final boolean parseInnerHtmlComments;
    //private final boolean htmlBlockDeepParser;
    private final boolean myHtmlBlockDeepParseNonBlock;
    private final boolean myHtmlBlockDeepParseBlankLineInterrupts;
    private final boolean myHtmlBlockDeepParseMarkdownInterruptsClosed;
    private final boolean myHtmlBlockDeepParseBlankLineInterruptsPartialTag;
    private final boolean myHtmlBlockDeepParseIndentedCodeInterrupts;
    //private final boolean myHtmlBlockDeepParseFirstOpenTagOnOneLine;

    HtmlBlockParser(DataHolder options, Pattern closingPattern, boolean isComment, HtmlDeepParser deepParser) {
        this.closingPattern = closingPattern;
        this.block = isComment ? new HtmlCommentBlock() : new HtmlBlock();
        this.deepParser = deepParser;
        this.parseInnerHtmlComments = options.get(Parser.PARSE_INNER_HTML_COMMENTS);
        //this.htmlBlockDeepParser = options.get(Parser.HTML_BLOCK_DEEP_PARSER);
        this.myHtmlBlockDeepParseNonBlock = options.get(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK);
        this.myHtmlBlockDeepParseBlankLineInterrupts = options.get(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS);
        this.myHtmlBlockDeepParseMarkdownInterruptsClosed = options.get(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED);
        this.myHtmlBlockDeepParseBlankLineInterruptsPartialTag = options.get(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG);
        this.myHtmlBlockDeepParseIndentedCodeInterrupts = options.get(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS);
        //this.myHtmlBlockDeepParseOpenTagsOnOneLine = options.get(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (deepParser != null) {
            if (state.isBlank()) {
                if (deepParser.isHtmlClosed() || myHtmlBlockDeepParseBlankLineInterrupts && !deepParser.haveOpenRawTag() || (myHtmlBlockDeepParseBlankLineInterruptsPartialTag && deepParser.isBlankLineInterruptible())) {
                    return BlockContinue.none();
                }
            }

            return BlockContinue.atIndex(state.getIndex());
        } else {
            if (finished) {
                return BlockContinue.none();
            }

            // Blank line ends type 6 and type 7 blocks
            if (state.isBlank() && closingPattern == null) {
                return BlockContinue.none();
            } else {
                return BlockContinue.atIndex(state.getIndex());
            }
        }
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        if (deepParser != null) {
            if (content.getLineCount() > 0) {
                // not the first line, which is already parsed
                deepParser.parseHtmlChunk(line, false, myHtmlBlockDeepParseNonBlock, false);
            }
        } else {
            if (closingPattern != null && closingPattern.matcher(line).find()) {
                finished = true;
            }
        }

        content.add(line, state.getIndent());
    }

    @Override
    public boolean canInterruptBy(final BlockParserFactory blockParserFactory) {
        return myHtmlBlockDeepParseMarkdownInterruptsClosed
                && deepParser != null
                && !(blockParserFactory instanceof HtmlBlockParser.Factory
                || (!myHtmlBlockDeepParseIndentedCodeInterrupts && blockParserFactory instanceof IndentedCodeBlockParser.BlockFactory))
                && deepParser.isHtmlClosed();
    }

    @Override
    public boolean canContain(final ParserState state, final BlockParser blockParser, final Block block) {
        return false;
    }

    @Override
    public boolean isInterruptible() {
        return myHtmlBlockDeepParseMarkdownInterruptsClosed && deepParser != null && deepParser.isHtmlClosed();
    }

    @Override
    public boolean isRawText() {
        return true;
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content);
        content = null;

        // split out inner comments
        if (!(block instanceof HtmlCommentBlock) && parseInnerHtmlComments) {
            // need to break it up into non-comments and comments
            int lastIndex = 0;
            BasedSequence chars = block.getContentChars();
            if (chars.eolLength() > 0) chars = chars.midSequence(0, -1);
            // RegEx for HTML can go into an infinite loop, we do manual search to avoid this
            //Matcher matcher = state.getParsing().HTML_COMMENT.matcher(chars);
            //while (matcher.find()) {
            //    int index = matcher.start();
            //    if (lastIndex < index) {
            //        HtmlInnerBlock html = new HtmlInnerBlock(chars.subSequence(lastIndex, index));
            //        block.appendChild(html);
            //    }
            //
            //    lastIndex = matcher.end();
            //    HtmlInnerBlockComment htmlComment = new HtmlInnerBlockComment(chars.subSequence(index, lastIndex));
            //    block.appendChild(htmlComment);
            //}
            int length = chars.length();
            while (lastIndex < length) {
                // find the opening HTML comment
                int index = chars.indexOf(HTML_COMMENT_OPEN, lastIndex);
                if (index < 0) break;

                // now lets find -->
                int end = chars.indexOf(HTML_COMMENT_CLOSE, index + HTML_COMMENT_OPEN.length());

                // if unterminated, ignore
                if (end < 0) break;

                if (lastIndex < index) {
                    HtmlInnerBlock html = new HtmlInnerBlock(chars.subSequence(lastIndex, index));
                    block.appendChild(html);
                }

                lastIndex = end + HTML_COMMENT_CLOSE.length();
                HtmlInnerBlockComment htmlComment = new HtmlInnerBlockComment(chars.subSequence(index, lastIndex));
                block.appendChild(htmlComment);
            }

            if (lastIndex > 0) {
                if (lastIndex < chars.length()) {
                    HtmlInnerBlock html = new HtmlInnerBlock(chars.subSequence(lastIndex, chars.length()));
                    block.appendChild(html);
                }
            }
        }
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<Class<? extends CustomBlockParserFactory>>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<Class<? extends CustomBlockParserFactory>>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
                    //FencedCodeBlockParser.Factory.class,
                    //HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private Patterns myPatterns = null;
        private final boolean myHtmlCommentBlocksInterruptParagraph;
        private final boolean myHtmlBlockDeepParser;
        private final boolean myHtmlBlockDeepParseNonBlock;
        private final boolean myHtmlBlockDeepParseFirstOpenTagOnOneLine;
        private final boolean myHtmlBlockCommentOnlyFullLine;
        private final boolean myHtmlBlockStartOnlyOnBlockTags;

        private BlockFactory(DataHolder options) {
            super(options);
            myHtmlCommentBlocksInterruptParagraph = Parser.HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH.getFrom(options);
            this.myHtmlBlockDeepParser = options.get(Parser.HTML_BLOCK_DEEP_PARSER);
            this.myHtmlBlockDeepParseNonBlock = options.get(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK);
            this.myHtmlBlockDeepParseFirstOpenTagOnOneLine = options.get(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE);
            this.myHtmlBlockCommentOnlyFullLine = options.get(Parser.HTML_BLOCK_COMMENT_ONLY_FULL_LINE);
            this.myHtmlBlockStartOnlyOnBlockTags = options.get(Parser.HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence line = state.getLine();

            if (state.getIndent() < 4 && line.charAt(nextNonSpace) == '<' && !(matchedBlockParser.getBlockParser() instanceof HtmlBlockParser)) {
                if (myHtmlBlockDeepParser) {
                    HtmlDeepParser deepParser = new HtmlDeepParser(Parser.HTML_BLOCK_TAGS.getFrom(state.getProperties()));
                    deepParser.parseHtmlChunk(line.subSequence(nextNonSpace, line.length()), myHtmlBlockStartOnlyOnBlockTags, myHtmlBlockDeepParseNonBlock, myHtmlBlockDeepParseFirstOpenTagOnOneLine);
                    if (deepParser.hadHtml()) {
                        // have our html block start
                        if ((deepParser.getHtmlMatch() == OPEN_TAG || (!myHtmlCommentBlocksInterruptParagraph && deepParser.getHtmlMatch() == COMMENT))
                                && (!deepParser.isFirstBlockTag() && matchedBlockParser.getBlockParser().getBlock() instanceof Paragraph)) {
                        } else {
                            // not paragraph or can interrupt paragraph
                            return BlockStart.of(new HtmlBlockParser(state.getProperties(), null, deepParser.getHtmlMatch() == COMMENT, deepParser)).atIndex(state.getIndex());
                        }
                    }
                } else {
                    for (int blockType = 1; blockType <= 7; blockType++) {
                        // Type 7 cannot interrupt a paragraph or may not start a block altogether
                        if (blockType == 7 && (myHtmlBlockStartOnlyOnBlockTags || matchedBlockParser.getBlockParser().getBlock() instanceof Paragraph)) {
                            continue;
                        }

                        if (myPatterns == null) {
                            myPatterns = new Patterns(state.getParsing(), state.getProperties());
                        }

                        Pattern opener = myPatterns.BLOCK_PATTERNS[blockType][0];
                        Pattern closer = myPatterns.BLOCK_PATTERNS[blockType][1];
                        final Matcher matcher = opener.matcher(line.subSequence(nextNonSpace, line.length()));
                        boolean matches = matcher.find();

                        // TEST: non-interrupting of paragraphs by HTML comments
                        if (matches && (myHtmlCommentBlocksInterruptParagraph || blockType != myPatterns.COMMENT_PATTERN_INDEX || !(matchedBlockParser.getBlockParser() instanceof ParagraphParser))) {
                            // Issue #158, HTML Comment followed by text
                            if (blockType == myPatterns.COMMENT_PATTERN_INDEX && myHtmlBlockCommentOnlyFullLine) {
                                final Matcher endMatcher = myPatterns.BLOCK_PATTERNS[myPatterns.COMMENT_PATTERN_INDEX][1].matcher(line.subSequence(matcher.end(), line.length()));
                                if (endMatcher.find()) {
                                    // see if nothing follows
                                    BasedSequence trailing = line.subSequence(endMatcher.end(), line.length()).trim();
                                    if (!trailing.equals("-->")) {
                                        return BlockStart.none();
                                    }
                                }
                            }
                            return BlockStart.of(new HtmlBlockParser(state.getProperties(), closer, blockType == myPatterns.COMMENT_PATTERN_INDEX, null)).atIndex(state.getIndex());
                        }
                    }
                }
            }
            return BlockStart.none();
        }
    }
}

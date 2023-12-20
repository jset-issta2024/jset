package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.internal.DocumentParser;
import com.vladsch.flexmark.parser.internal.InlineParserImpl;
import com.vladsch.flexmark.parser.internal.LinkRefProcessorData;
import com.vladsch.flexmark.parser.internal.PostProcessorManager;
import com.vladsch.flexmark.util.IParse;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.DataSet;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Parses input text to a tree of nodes.
 * <p>
 * Start with the {@link #builder} method, configure the parser and build it. Example:
 * <pre>{@code
 * Parser parser = Parser.builder().build();
 * Node document = parser.parse("input text");
 * }</pre>
 */
public class Parser implements IParse {
    public static final DataKey<Iterable<Extension>> EXTENSIONS = BuilderBase.EXTENSIONS;

    public static final DataKey<KeepType> REFERENCES_KEEP = new DataKey<>("REFERENCES_KEEP", KeepType.FIRST);
    public static final DataKey<ReferenceRepository> REFERENCES = new DataKey<>("REFERENCES", new DataValueFactory<ReferenceRepository>() {
        @Override
        public ReferenceRepository create(DataHolder options) {
            return new ReferenceRepository(options);
        }
    });

    public static final DataKey<Boolean> ASTERISK_DELIMITER_PROCESSOR = new DataKey<>("ASTERISK_DELIMITER_PROCESSOR", true);

    public static final DataKey<Boolean> TRACK_DOCUMENT_LINES = new DataKey<>("TRACK_DOCUMENT_LINES", false);

    public static final DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<>("BLOCK_QUOTE_PARSER", true);
    public static final DataKey<Boolean> BLOCK_QUOTE_EXTEND_TO_BLANK_LINE = new DataKey<>("BLOCK_QUOTE_EXTEND_TO_BLANK_LINE", false);
    public static final DataKey<Boolean> BLOCK_QUOTE_IGNORE_BLANK_LINE = new DataKey<>("BLOCK_QUOTE_IGNORE_BLANK_LINE", false);
    public static final DataKey<Boolean> BLOCK_QUOTE_ALLOW_LEADING_SPACE = new DataKey<>("BLOCK_QUOTE_ALLOW_LEADING_SPACE", true);
    public static final DataKey<Boolean> BLOCK_QUOTE_INTERRUPTS_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_INTERRUPTS_PARAGRAPH", true);
    public static final DataKey<Boolean> BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH", true);

    /**
     * @deprecated use BLOCK_QUOTE_EXTEND_TO_BLANK_LINE
     */
    @Deprecated
    public static final DataKey<Boolean> BLOCK_QUOTE_TO_BLANK_LINE = BLOCK_QUOTE_EXTEND_TO_BLANK_LINE;

    public static final DataKey<Boolean> FENCED_CODE_BLOCK_PARSER = new DataKey<>("FENCED_CODE_BLOCK_PARSER", true);
    public static final DataKey<Boolean> MATCH_CLOSING_FENCE_CHARACTERS = new DataKey<>("MATCH_CLOSING_FENCE_CHARACTERS", true);
    public static final DataKey<Boolean> FENCED_CODE_CONTENT_BLOCK = new DataKey<>("FENCED_CODE_CONTENT_BLOCK", false);

    public static final DataKey<Boolean> CODE_SOFT_LINE_BREAKS = new DataKey<>("CODE_SOFT_LINE_BREAKS", false);
    /**
     * @deprecated use FENCED_CODE_CONTENT_BLOCK
     */
    @Deprecated public static final DataKey<Boolean> CODE_CONTENT_BLOCK = FENCED_CODE_CONTENT_BLOCK;

    public static final DataKey<Boolean> HARD_LINE_BREAK_LIMIT = new DataKey<>("HARD_LINE_BREAK_LIMIT", false);

    public static final DataKey<Boolean> HEADING_PARSER = new DataKey<>("HEADING_PARSER", true);
    public static final DataKey<Integer> HEADING_SETEXT_MARKER_LENGTH = new DataKey<>("HEADING_SETEXT_MARKER_LENGTH", 1);
    public static final DataKey<Boolean> HEADING_NO_ATX_SPACE = new DataKey<>("HEADING_NO_ATX_SPACE", false);
    public static final DataKey<Boolean> HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE = new DataKey<>("HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE", false);
    public static final DataKey<Boolean> HEADING_NO_LEAD_SPACE = new DataKey<>("HEADING_NO_LEAD_SPACE", false);
    public static final DataKey<Boolean> HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH = new DataKey<>("HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH", true);

    public static final DataKey<Boolean> HTML_BLOCK_PARSER = new DataKey<>("HTML_BLOCK_PARSER", true);
    public static final DataKey<Boolean> HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH = new DataKey<>("HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH", true);
    public static final DataKey<Boolean> HTML_FOR_TRANSLATOR = new DataKey<>("HTML_FOR_TRANSLATOR", false);

    public static final DataKey<Boolean> INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS = new DataKey<>("INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS", false);

    public static final DataKey<Boolean> INDENTED_CODE_BLOCK_PARSER = new DataKey<>("INDENTED_CODE_BLOCK_PARSER", true);
    public static final DataKey<Boolean> INDENTED_CODE_NO_TRAILING_BLANK_LINES = new DataKey<>("INDENTED_CODE_NO_TRAILING_BLANK_LINES", true);

    public static final DataKey<Boolean> INTELLIJ_DUMMY_IDENTIFIER = new DataKey<>("INTELLIJ_DUMMY_IDENTIFIER", false);

    public static final DataKey<Boolean> MATCH_NESTED_LINK_REFS_FIRST = new DataKey<>("MATCH_NESTED_LINK_REFS_FIRST", true);
    public static final DataKey<Boolean> PARSE_INNER_HTML_COMMENTS = new DataKey<>("PARSE_INNER_HTML_COMMENTS", false);
    public static final DataKey<Boolean> PARSE_MULTI_LINE_IMAGE_URLS = new DataKey<>("PARSE_MULTI_LINE_IMAGE_URLS", false);
    public static final DataKey<Boolean> PARSE_JEKYLL_MACROS_IN_URLS = new DataKey<>("PARSE_JEKYLL_MACROS_IN_URLS", false);
    public static final DataKey<Boolean> SPACE_IN_LINK_URLS = new DataKey<>("SPACE_IN_LINK_URLS", false);
    public static final DataKey<Boolean> SPACE_IN_LINK_ELEMENTS = new DataKey<>("SPACE_IN_LINK_ELEMENTS", false);
    public static final DataKey<Boolean> WWW_AUTO_LINK_ELEMENT = new DataKey<>("WWW_AUTO_LINK_ELEMENT", false);

    public static final DataKey<Boolean> REFERENCE_PARAGRAPH_PRE_PROCESSOR = new DataKey<>("REFERENCE_BLOCK_PRE_PROCESSOR", true);
    public static final DataKey<Boolean> THEMATIC_BREAK_PARSER = new DataKey<>("THEMATIC_BREAK_PARSER", true);
    public static final DataKey<Boolean> THEMATIC_BREAK_RELAXED_START = new DataKey<>("THEMATIC_BREAK_RELAXED_START", true);

    public static final DataKey<Boolean> UNDERSCORE_DELIMITER_PROCESSOR = new DataKey<>("UNDERSCORE_DELIMITER_PROCESSOR", true);
    public static final DataKey<Boolean> BLANK_LINES_IN_AST = new DataKey<>("BLANK_LINES_IN_AST", false);

    /**
     * STRONG_WRAPS_EMPHASIS default false, when true makes parsing CommonMark Spec 0.27 compliant
     */
    public static final DataKey<Boolean> STRONG_WRAPS_EMPHASIS = new DataKey<>("STRONG_WRAPS_EMPHASIS", false);

    /**
     * LINKS_ALLOW_MATCHED_PARENTHESES default true, when false makes parsing CommonMark Spec 0.27 compliant
     */
    public static final DataKey<Boolean> LINKS_ALLOW_MATCHED_PARENTHESES = new DataKey<>("LINKS_ALLOW_MATCHED_PARENTHESES", true);

    // the meat of differences in emulation
    public static final DataKey<Boolean> LIST_BLOCK_PARSER = new DataKey<>("LIST_BLOCK_PARSER", true);
    public static final DataKey<ParserEmulationProfile> PARSER_EMULATION_PROFILE = new DataKey<>("PARSER_EMULATION_PROFILE", ParserEmulationProfile.COMMONMARK);

    // deep HTML block parsing
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSER = new DataKey<>("HTML_BLOCK_DEEP_PARSER", false);
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_NON_BLOCK = new DataKey<>("HTML_BLOCK_DEEP_PARSE_NON_BLOCK", true);
    public static final DataKey<Boolean> HTML_BLOCK_COMMENT_ONLY_FULL_LINE = new DataKey<>("HTML_BLOCK_COMMENT_ONLY_FULL_LINE", false);
    public static final DataKey<Boolean> HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS = new DynamicDefaultKey<Boolean>("HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS", HTML_BLOCK_DEEP_PARSER);
    public static final DataKey<List<String>> HTML_BLOCK_TAGS = new DataKey<List<String>>("HTML_BLOCK_TAGS", new DataValueFactory<List<String>>() {
        @Override
        public List<String> create(final DataHolder value) {
            return Arrays.asList(
                    "address",
                    "article",
                    "aside",
                    "base",
                    "basefont",
                    "blockquote",
                    "body",
                    "caption",
                    "center",
                    "col",
                    "colgroup",
                    "dd",
                    "details",
                    "dialog",
                    "dir",
                    "div",
                    "dl",
                    "dt",
                    "fieldset",
                    "figcaption",
                    "figure",
                    "footer",
                    "form",
                    "frame",
                    "frameset",
                    "h1",
                    "h2",
                    "h3",
                    "h4",
                    "h5",
                    "h6",
                    "head",
                    "header",
                    "hr",
                    "html",
                    "iframe",
                    "legend",
                    "li",
                    "link",
                    "main",
                    "math",
                    "menu",
                    "menuitem",
                    "meta",
                    "nav",
                    "noframes",
                    "ol",
                    "optgroup",
                    "option",
                    "p",
                    "param",
                    "section",
                    "source",
                    "summary",
                    "table",
                    "tbody",
                    "td",
                    "tfoot",
                    "th",
                    "thead",
                    "title",
                    "tr",
                    "track",
                    "ul"
            );
        }
    });

    /**
     * Blank line interrupts HTML block when not in raw tag, otherwise only when closed
     */
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS = new DataKey<>("HTML_BL OCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS", true);

    /**
     * open tags must be contained on one line
     */
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE = new DataKey<>("HTML_BL HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE", false);

    /**
     * Other markdown elements can interrupt a closed block without an intervening blank line
     */
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED = new DataKey<>("HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED", false);

    /**
     * blank line interrupts partially open tag ie. &lt;TAG without a corresponding &gt;
     */
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG = new DataKey<>("HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG", true);

    /**
     * Indented code can interrupt HTML block
     */
    public static final DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS = new DataKey<>("HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS", false);

    /**
     * Used by formatter for translation parsing
     */
    public static final DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = new DataKey<>("TRANSLATION_HTML_BLOCK_TAG_PATTERN", "__(?:\\d+)_");
    public static final DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = new DataKey<>("TRANSLATION_HTML_INLINE_TAG_PATTERN", "_(?:\\d+)_");

    /**
     * @deprecated
     */
    @Deprecated public static final DataKey<ParserEmulationProfile> PARSER_EMULATION_FAMILY = PARSER_EMULATION_PROFILE;

    // LISTS_ITEM_INDENT is also the INDENTED CODE INDENT parser emulation family either does not use it or expects the number of columns to next indent item (in this case indented code is the same)
    // LISTS_CODE_INDENT can be the same as LISTS_ITEM_INDENT or double that where indentation counts from first list item indent
    public static final DataKey<Integer> LISTS_CODE_INDENT = new DataKey<>("LISTS_CODE_INDENT", 4);
    public static final DataKey<Integer> LISTS_ITEM_INDENT = new DataKey<>("LISTS_ITEM_INDENT", 4);

    // new item with content indent >= this value cause an empty item with code indent child, weird standard, so far CommonMark only
    public static final DataKey<Integer> LISTS_NEW_ITEM_CODE_INDENT = new DataKey<>("LISTS_NEW_ITEM_CODE_INDENT", 4);

    // space must follow a list item marker to be recognized as such
    public static final DataKey<Boolean> LISTS_ITEM_MARKER_SPACE = new DataKey<>("LISTS_ITEM_MARKER_SPACE", false);

    // strings for list marker suffixes which offset the content, to properly support gfm task lists with content offset matching the suffix end
    // LIST_ITEM_MARKER_SPACE is applied after the suffix if it is present, and before. Spaces around the suffix are implicitly allowed
    public static final DataKey<String[]> LISTS_ITEM_MARKER_SUFFIXES = new DataKey<String[]>("LISTS_ITEM_MARKER_SUFFIXES", new String[] { });
    public static final DataKey<Boolean> LISTS_NUMBERED_ITEM_MARKER_SUFFIXED = new DataKey<>("LISTS_NUMBERED_ITEM_MARKER_SUFFIXED", true);

    // List parsing options beyond major parser family
    public static final DataKey<Boolean> LISTS_AUTO_LOOSE = new DataKey<>("LISTS_AUTO_LOOSE", true);
    public static final DataKey<Boolean> LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS = new DataKey<>("LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN = new DataKey<>("LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH = new DataKey<>("LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM = new DataKey<>("LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM", false);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE", true);
    public static final DataKey<Boolean> LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE", false);
    public static final DataKey<Boolean> LISTS_DELIMITER_MISMATCH_TO_NEW_LIST = new DataKey<>("LISTS_DELIMITER_MISMATCH_TO_NEW_LIST", true);
    public static final DataKey<Boolean> LISTS_END_ON_DOUBLE_BLANK = new DataKey<>("LISTS_END_ON_DOUBLE_BLANK", false);
    public static final DataKey<Boolean> LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST = new DataKey<>("LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST", true);
    public static final DataKey<Boolean> LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST = new DataKey<>("LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST", false);
    public static final DataKey<Boolean> LISTS_ORDERED_ITEM_DOT_ONLY = new DataKey<>("LISTS_ORDERED_ITEM_DOT_ONLY", false);
    public static final DataKey<Boolean> LISTS_ORDERED_LIST_MANUAL_START = new DataKey<>("LISTS_ORDERED_LIST_MANUAL_START", true);
    public static final DataKey<Boolean> LISTS_ITEM_CONTENT_AFTER_SUFFIX = new DataKey<>("LISTS_ITEM_CONTENT_AFTER_SUFFIX", false);

    // List Item paragraph interruption capabilities
    // in general:
    // for empty, empty and non-empty flags must be true for condition to be true
    // for ordered-non-one, ordered-ono-one and ordered non-one must be true for condition to be true
    //
    // so disabling the non-empty flag, also disable the corresponding empty ones
    // so disabling the ordered flag, also disable the corresponding ordered-non-one ones
    //

    public static final DataKey<Boolean> LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH", false);

    public static final DataKey<Boolean> LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH", false);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH", false);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH", false);

    public static final DataKey<Boolean> LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);

    // must also be able to interrupt with corresponding non empty list item type (bullet, ordered, ordered non-one)
    public static final DataKey<Boolean> LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);

    // whether these can start sub-lists when they are not preceded by a blank line and are indented to be interpreted as sub listk
    // must also be able to interrupt item paragraphs of corresponding list item type (bullet, ordered, ordered non-one)
    public static final DataKey<Boolean> LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    public static final DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    public static final DataKey<String> LISTS_ITEM_PREFIX_CHARS = new DataKey<>("LISTS_ITEM_PREFIX_CHARS", "*-+");

    // separate setting for CODE_BLOCK_INDENT
    public static final DataKey<Integer> CODE_BLOCK_INDENT = new DynamicDefaultKey<Integer>("CODE_BLOCK_INDENT", LISTS_ITEM_INDENT);

    private final List<CustomBlockParserFactory> blockParserFactories;
    private final Map<Character, DelimiterProcessor> delimiterProcessors;
    private final BitSet delimiterCharacters;
    private final BitSet specialCharacters;
    private final Builder builder;
    private final PostProcessorManager.PostProcessorDependencies postProcessorDependencies;
    private final DocumentParser.ParagraphPreProcessorDependencies paragraphPreProcessorFactories;
    private final DocumentParser.BlockPreProcessorDependencies blockPreProcessorDependencies;
    private final LinkRefProcessorData linkRefProcessors;
    private final List<InlineParserExtensionFactory> inlineParserExtensionFactories;
    private final InlineParserFactory inlineParserFactory;
    private final DataHolder options;

    private Parser(Builder builder) {
        this.builder = new Builder(builder); // make a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.blockParserFactories = DocumentParser.calculateBlockParserFactories(this.options, builder.blockParserFactories);
        this.inlineParserFactory = builder.inlineParserFactory == null ? DocumentParser.INLINE_PARSER_FACTORY : builder.inlineParserFactory;
        this.paragraphPreProcessorFactories = DocumentParser.calculateParagraphPreProcessors(this.options, builder.paragraphPreProcessorFactories, this.inlineParserFactory);
        this.blockPreProcessorDependencies = DocumentParser.calculateBlockPreProcessors(this.options, builder.blockPreProcessorFactories, this.inlineParserFactory);
        this.delimiterProcessors = InlineParserImpl.calculateDelimiterProcessors(this.options, builder.delimiterProcessors);
        this.delimiterCharacters = InlineParserImpl.calculateDelimiterCharacters(this.options, delimiterProcessors.keySet());
        this.linkRefProcessors = InlineParserImpl.calculateLinkRefProcessors(this.options, builder.linkRefProcessors);
        this.specialCharacters = InlineParserImpl.calculateSpecialCharacters(this.options, delimiterCharacters);
        this.postProcessorDependencies = PostProcessorManager.calculatePostProcessors(this.options, builder.postProcessorFactories);
        this.inlineParserExtensionFactories = builder.inlineParserExtensionFactories;
    }

    /**
     * Create a new builder for configuring a {@link Parser}.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    public Document parse(BasedSequence input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(input);
        return postProcess(document);
    }

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    public Document parse(String input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(CharSubSequence.of(input));
        return postProcess(document);
    }

    /**
     * Parse the specified reader into a tree of nodes. The caller is responsible for closing the reader.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the reader to parse
     * @return the root node
     * @throws IOException when reading throws an exception
     */
    public Document parseReader(Reader input) throws IOException {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(input);
        return postProcess(document);
    }

    private Document postProcess(Document document) {
        document = PostProcessorManager.processDocument(document, postProcessorDependencies);
        return document;
    }

    public Parser withOptions(DataHolder options) {
        return options == null ? this : new Parser(new Builder(builder, options));
    }

    @Override
    public DataHolder getOptions() {
        return new DataSet(builder);
    }

    @Override
    public boolean transferReferences(Document document, Document included) {
        return transferReferences(document, included, null);
    }

    @Override
    public boolean transferReferences(Document document, Document included, Boolean onlyIfUndefined) {
        // transfer references from included to document
        boolean transferred = false;

        if (options.contains(EXTENSIONS)) {
            for (Extension extension : options.get(EXTENSIONS)) {
                if (extension instanceof ReferenceHoldingExtension) {
                    ReferenceHoldingExtension parserExtension = (ReferenceHoldingExtension) extension;
                    if (parserExtension.transferReferences(document, included)) transferred = true;
                }
            }
        }

        // transfer references
        if (document.contains(REFERENCES) && included.contains(REFERENCES)) {
            if (transferReferences(REFERENCES.getFrom(document), REFERENCES.getFrom(included),
                    onlyIfUndefined != null ? onlyIfUndefined : REFERENCES_KEEP.getFrom(document) == KeepType.FIRST)
            ) {
                transferred = true;
            }
        }

        if (transferred) {
            document.set(HtmlRenderer.RECHECK_UNDEFINED_REFERENCES, true);
        }
        return transferred;
    }

    public static <T extends Node> boolean transferReferences(NodeRepository<T> destination, NodeRepository<T> included, boolean onlyIfUndefined) {
        return NodeRepository.transferReferences(destination, included, onlyIfUndefined);
    }

    /**
     * Builder for configuring a {@link Parser}.
     */
    public static class Builder extends BuilderBase<Builder> {
        private final List<CustomBlockParserFactory> blockParserFactories = new ArrayList<CustomBlockParserFactory>();
        private final List<DelimiterProcessor> delimiterProcessors = new ArrayList<DelimiterProcessor>();
        private final List<PostProcessorFactory> postProcessorFactories = new ArrayList<PostProcessorFactory>();
        private final List<ParagraphPreProcessorFactory> paragraphPreProcessorFactories = new ArrayList<ParagraphPreProcessorFactory>();
        private final List<BlockPreProcessorFactory> blockPreProcessorFactories = new ArrayList<BlockPreProcessorFactory>();
        private final List<LinkRefProcessorFactory> linkRefProcessors = new ArrayList<LinkRefProcessorFactory>();
        private final List<InlineParserExtensionFactory> inlineParserExtensionFactories = new ArrayList<InlineParserExtensionFactory>();
        private InlineParserFactory inlineParserFactory = null;

        public Builder(DataHolder options) {
            super(options);
            loadExtensions();
        }

        public Builder() {
            super();
        }

        public Builder(Builder other) {
            super(other);

            blockParserFactories.addAll(other.blockParserFactories);
            delimiterProcessors.addAll(other.delimiterProcessors);
            postProcessorFactories.addAll(other.postProcessorFactories);
            paragraphPreProcessorFactories.addAll(other.paragraphPreProcessorFactories);
            blockPreProcessorFactories.addAll(other.blockPreProcessorFactories);
            linkRefProcessors.addAll(other.linkRefProcessors);
            inlineParserFactory = other.inlineParserFactory;
            inlineParserExtensionFactories.addAll(other.inlineParserExtensionFactories);
        }

        public Builder(Builder other, DataHolder options) {
            this(other);
            withOptions(options);
        }

        /**
         * @return the configured {@link Parser}
         */
        public Parser build() {
            return new Parser(this);
        }

        @Override
        protected void removeApiPoint(final Object apiPoint) {
            if (apiPoint instanceof CustomBlockParserFactory) this.blockParserFactories.remove(apiPoint);
            else if (apiPoint instanceof DelimiterProcessor) this.delimiterProcessors.remove(apiPoint);
            else if (apiPoint instanceof PostProcessorFactory) this.postProcessorFactories.remove(apiPoint);
            else if (apiPoint instanceof ParagraphPreProcessorFactory) this.paragraphPreProcessorFactories.remove(apiPoint);
            else if (apiPoint instanceof BlockPreProcessorFactory) this.blockPreProcessorFactories.remove(apiPoint);
            else if (apiPoint instanceof LinkRefProcessorFactory) this.linkRefProcessors.remove(apiPoint);
            else if (apiPoint instanceof InlineParserExtensionFactory) this.inlineParserExtensionFactories.remove(apiPoint);
            else if (apiPoint instanceof InlineParserFactory) this.inlineParserFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(final Extension extension) {
            if (extension instanceof ParserExtension) {
                ParserExtension parserExtension = (ParserExtension) extension;
                parserExtension.parserOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(final Extension extension) {
            if (extension instanceof ParserExtension) {
                ParserExtension parserExtension = (ParserExtension) extension;
                parserExtension.extend(this);
                return true;
            }
            return false;
        }

        /**
         * Adds a custom block parser factory.
         * <p>
         * Note that custom factories are applied <em>before</em> the built-in factories. This is so that
         * extensions can change how some syntax is parsed that would otherwise be handled by built-in factories.
         * "With great power comes great responsibility."
         *
         * @param blockParserFactory a block parser factory implementation
         * @return {@code this}
         */
        public Builder customBlockParserFactory(CustomBlockParserFactory blockParserFactory) {
            blockParserFactories.add(blockParserFactory);
            addExtensionApiPoint(blockParserFactory);
            return this;
        }

        public Builder customInlineParserExtensionFactory(InlineParserExtensionFactory inlineParserExtensionFactory) {
            inlineParserExtensionFactories.add(inlineParserExtensionFactory);
            addExtensionApiPoint(inlineParserExtensionFactory);
            return this;
        }

        public Builder customInlineParserFactory(InlineParserFactory blockParserFactory) {
            if (inlineParserFactory != null) {
                throw new IllegalStateException("custom inline parser factory is already set to " + inlineParserFactory.getClass().getName());
            }
            inlineParserFactory = blockParserFactory;
            addExtensionApiPoint(blockParserFactory);
            return this;
        }

        public Builder customDelimiterProcessor(DelimiterProcessor delimiterProcessor) {
            delimiterProcessors.add(delimiterProcessor);
            addExtensionApiPoint(delimiterProcessor);
            return this;
        }

        public Builder postProcessorFactory(PostProcessorFactory postProcessorFactory) {
            postProcessorFactories.add(postProcessorFactory);
            addExtensionApiPoint(postProcessorFactory);
            return this;
        }

        public Builder paragraphPreProcessorFactory(ParagraphPreProcessorFactory paragraphPreProcessorFactory) {
            paragraphPreProcessorFactories.add(paragraphPreProcessorFactory);
            addExtensionApiPoint(paragraphPreProcessorFactory);
            return this;
        }

        public Builder blockPreProcessorFactory(BlockPreProcessorFactory blockPreProcessorFactory) {
            blockPreProcessorFactories.add(blockPreProcessorFactory);
            addExtensionApiPoint(blockPreProcessorFactory);
            return this;
        }

        public Builder linkRefProcessorFactory(LinkRefProcessorFactory linkRefProcessor) {
            linkRefProcessors.add(linkRefProcessor);
            addExtensionApiPoint(linkRefProcessor);
            return this;
        }
    }

    /**
     * Extension for {@link Parser}.
     * <p>
     * Implementations of this interface should done by all Extensions that extend the core parser
     * <p>
     * Each will be called via {@link ParserExtension#extend(Builder)} method giving it a chance to call back
     * on the builder methods to register parser extension points
     */
    public interface ParserExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options that must be common to all extensions.
         *
         * @param options option set that will be used for the builder
         */
        void parserOptions(MutableDataHolder options);

        /**
         * This method is called on all extensions so that they can register their custom processors
         *
         * @param parserBuilder parser builder with which to register extensions
         * @see Builder#customBlockParserFactory(CustomBlockParserFactory)
         * @see Builder#customInlineParserExtensionFactory(InlineParserExtensionFactory)
         * @see Builder#customInlineParserFactory(InlineParserFactory)
         * @see Builder#customDelimiterProcessor(DelimiterProcessor)
         * @see Builder#postProcessorFactory(PostProcessorFactory)
         * @see Builder#paragraphPreProcessorFactory(ParagraphPreProcessorFactory)
         * @see Builder#blockPreProcessorFactory(BlockPreProcessorFactory)
         * @see Builder#linkRefProcessorFactory(LinkRefProcessorFactory)
         */
        void extend(Builder parserBuilder);
    }

    /**
     * Should be implemented by all extensions that create a node repository or other references in the
     * document. It is used by the parser to transfer references from included document to the document
     * that is doing the inclusion so that during rendering references in the included document will
     * appear as local references to the document being rendered.
     * <p>
     * Extension for {@link Parser}.
     */
    public interface ReferenceHoldingExtension extends Extension {
        /**
         * This method is called to transfer references from included document to the source document
         *
         * @param document destination document for references
         * @param included source document for references
         * @return true if there were references to transfer
         */
        boolean transferReferences(MutableDataHolder document, DataHolder included);
    }

    /**
     * Add extension(s) to the extension list
     *
     * @param options    mutable options holding existing extensions
     * @param extensions extension to add
     * @return mutable options
     */
    public static MutableDataHolder addExtensions(MutableDataHolder options, Extension... extensions) {
        Iterable<Extension> extensionIterable = options.get(Parser.EXTENSIONS);
        ArrayList<Extension> extensionList = new ArrayList<Extension>();

        extensionList.addAll(Arrays.asList(extensions));
        for (Extension extension : extensionIterable) {
            extensionList.add(extension);
        }
        options.set(Parser.EXTENSIONS, extensionList);
        return options;
    }

    /**
     * Remove extension(s) of given class from the extension list
     *
     * @param options    mutable options holding existing extensions
     * @param extensions extension classes to remove
     * @return mutable options
     */
    public static MutableDataHolder removeExtensions(MutableDataHolder options, Class... extensions) {
        Iterable<Extension> extensionIterable = options.get(Parser.EXTENSIONS);
        HashSet<Extension> extensionList = new HashSet<Extension>();

        for (Extension extension : extensionIterable) {
            boolean keep = true;
            for (Class clazz : extensions) {
                if (clazz.isInstance(extension)) {
                    keep = false;
                    break;
                }
            }
            if (keep) {
                extensionList.add(extension);
            }
        }

        options.set(Parser.EXTENSIONS, extensionList);
        return options;
    }
}

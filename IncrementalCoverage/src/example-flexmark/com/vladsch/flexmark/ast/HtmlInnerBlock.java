package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Inline HTML element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlInnerBlock extends HtmlBlockBase {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    public HtmlInnerBlock() {
    }

    public HtmlInnerBlock(BasedSequence chars) {
        super(chars);
    }
}

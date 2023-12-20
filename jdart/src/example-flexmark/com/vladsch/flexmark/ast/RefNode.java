package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.ReferencingNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class RefNode extends LinkNode implements LinkRefDerived, ReferencingNode<ReferenceRepository, Reference> {
    protected BasedSequence textOpeningMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence textClosingMarker = BasedSequence.NULL;
    protected BasedSequence referenceOpeningMarker = BasedSequence.NULL;
    protected BasedSequence reference = BasedSequence.NULL;
    protected BasedSequence referenceClosingMarker = BasedSequence.NULL;
    protected boolean isDefined = false;

    @Override
    public BasedSequence[] getSegments() {
        if (isReferenceTextCombined()) {
            return new BasedSequence[] {
                    referenceOpeningMarker,
                    reference,
                    referenceClosingMarker,
                    textOpeningMarker,
                    text,
                    textClosingMarker,
            };
        } else {
            return new BasedSequence[] {
                    textOpeningMarker,
                    text,
                    textClosingMarker,
                    referenceOpeningMarker,
                    reference,
                    referenceClosingMarker,
            };
        }
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (isReferenceTextCombined()) {
            delimitedSegmentSpanChars(out, referenceOpeningMarker, reference, referenceClosingMarker, "reference");
            delimitedSegmentSpanChars(out, textOpeningMarker, text, textClosingMarker, "text");
        } else {
            delimitedSegmentSpanChars(out, textOpeningMarker, text, textClosingMarker, "text");
            delimitedSegmentSpanChars(out, referenceOpeningMarker, reference, referenceClosingMarker, "reference");
        }
    }

    public RefNode() {
    }

    public RefNode(BasedSequence chars) {
        super(chars);
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence reference, BasedSequence referenceClosingMarker) {
        super(textOpeningMarker.baseSubSequence(textOpeningMarker.getStartOffset(), referenceClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.reference = reference;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public RefNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence reference, BasedSequence referenceClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.reference = reference;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker) {
        super(textOpeningMarker.baseSubSequence(textOpeningMarker.getStartOffset(), textClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
    }

    public RefNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence referenceClosingMarker) {
        super(textOpeningMarker.baseSubSequence(textOpeningMarker.getStartOffset(), referenceClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public void setReferenceChars(BasedSequence referenceChars) {
        int referenceCharsLength = referenceChars.length();
        int openingOffset = referenceChars.charAt(0) == '!' ? 2 : 1;
        this.referenceOpeningMarker = referenceChars.subSequence(0, openingOffset);
        this.reference = referenceChars.subSequence(openingOffset, referenceCharsLength - 1).trim();
        this.referenceClosingMarker = referenceChars.subSequence(referenceCharsLength - 1, referenceCharsLength);
    }

    public void setTextChars(BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 1);
        this.text = textChars.subSequence(1, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }

    public boolean isReferenceTextCombined() {
        return text == BasedSequence.NULL;
    }

    @Override
    public boolean isDefined() {
        return isDefined;
    }

    public void setDefined(boolean defined) {
        isDefined = defined;
    }

    @Override
    public boolean isTentative() {
        return !isDefined;
    }

    public boolean isDummyReference() {
        return textOpeningMarker != BasedSequence.NULL && text == BasedSequence.NULL && textClosingMarker != BasedSequence.NULL;
    }

    public BasedSequence getText() {
        return text;
    }

    @Override
    public BasedSequence getReference() {
        return reference;
    }

    @Override
    public Reference getReferenceNode(Document document) {
        return getReferenceNode(document.get(Parser.REFERENCES));
    }

    @Override
    public Reference getReferenceNode(ReferenceRepository repository) {
        if (repository == null) return null;
        String normalizeRef = repository.normalizeKey(reference);
        return repository.get(normalizeRef);
    }

    public BasedSequence getTextOpeningMarker() {
        return textOpeningMarker;
    }

    public void setTextOpeningMarker(BasedSequence textOpeningMarker) {
        this.textOpeningMarker = textOpeningMarker;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getTextClosingMarker() {
        return textClosingMarker;
    }

    public void setTextClosingMarker(BasedSequence textClosingMarker) {
        this.textClosingMarker = textClosingMarker;
    }

    public BasedSequence getReferenceOpeningMarker() {
        return referenceOpeningMarker;
    }

    public void setReferenceOpeningMarker(BasedSequence referenceOpeningMarker) {
        this.referenceOpeningMarker = referenceOpeningMarker;
    }

    public void setReference(BasedSequence reference) {
        this.reference = reference;
    }

    public BasedSequence getDummyReference() {
        if (isDummyReference()) {
            return getChars().baseSubSequence(textOpeningMarker.getStartOffset(), textClosingMarker.getEndOffset());
        }
        return BasedSequence.NULL;
    }

    public BasedSequence getReferenceClosingMarker() {
        return referenceClosingMarker;
    }

    public void setReferenceClosingMarker(BasedSequence referenceClosingMarker) {
        this.referenceClosingMarker = referenceClosingMarker;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + text + ", reference=" + reference;
    }
}

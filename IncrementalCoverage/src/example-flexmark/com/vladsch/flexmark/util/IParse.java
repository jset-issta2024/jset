package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface to generic parser for RenderingTestCase customizations
 */
public interface IParse {
    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    Node parse(BasedSequence input);

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    Node parse(String input);

    /**
     * Parse the specified reader into a tree of nodes. The caller is responsible for closing the reader.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the reader to parse
     * @return the root node
     * @throws IOException when reading throws an exception
     */
    Node parseReader(Reader input) throws IOException;

    /**
     * Return an IParse instance configured for passed in options
     *
     * @param options options to use for a new instance
     * @return a new instance of IParse implementation with the given options applied
     */
    IParse withOptions(DataHolder options);

    /**
     * Get Options for parsing
     *
     * @return DataHolder for options
     */
    DataHolder getOptions();

    /**
     * Transfer reference definition between documents
     *
     * @param document destination document
     * @param included source document
     * @return true if any references were transferred
     * @deprecated use {@link #transferReferences(Document, Document, Boolean)}
     */
    @Deprecated
    boolean transferReferences(Document document, Document included);

    /**
     * Transfer reference definition between documents
     *
     * @param document        destination document
     * @param included        source document
     * @param onlyIfUndefined true if only should transfer references not already defined in the destination document,
     *                        false to transfer all,
     *                        null to use repository's KEEP_TYPE to make the determination (if KEEP_FIRST then only transfer if undefined,
     * @return true if any references were transferred
     */
    boolean transferReferences(Document document, Document included, Boolean onlyIfUndefined);
}

package com.vladsch.flexmark.html.renderer;

/**
 * Specifies the node part for which attributes can be provided
 */
public class AttributablePart {
    public static final AttributablePart NODE = new AttributablePart("NODE");
    public static final AttributablePart NODE_POSITION = new AttributablePart("NODE_POSITION");
    public static final AttributablePart LINK = new AttributablePart("LINK");
    public static final AttributablePart ID = new AttributablePart("ID");

    private final String myName;

    public AttributablePart(String name) {
        this.myName = name;
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

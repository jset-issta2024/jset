/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * Sets the parsed data to a {@link pobs.PWrapper wrapped} string.
 * @author	Martijn W. van der Lee
 * @see		pobs.action.PAppendString
 */
public class PSetString implements pobs.PAction {
    public pobs.PWrapper wrapped;

    /**
     * Sole constructor.
     * @param	wrapped	a wrapped string.
     */
    public PSetString(pobs.PWrapper wrapped) {
        super();

        this.wrapped = wrapped;
    }

    public void perform(pobs.PTarget target, java.lang.String data) {
        if (this.wrapped.object instanceof java.lang.String)
            this.wrapped.object = data;
    }
}

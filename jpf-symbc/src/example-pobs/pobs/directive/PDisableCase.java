/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.directive;

import pobs.PDirective;

/**
 * Disables the case sensitive directive.
 * 
 * @author	Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PDisableCase implements PDirectiveControl {
	public Object modifyState(PDirective directive) {
		Boolean result = directive.isCaseSensitive() ? Boolean.TRUE 
													 : Boolean.FALSE;
		directive.setCaseSensitive(false);
		return result;
	}

	public void reestablishPreviousState(PDirective directive,
										 Object previousState) {
		directive.setCaseSensitive(previousState == Boolean.TRUE);
	}
}

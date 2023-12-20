package com.github.situx.compiler.treej;

import com.github.situx.compiler.visitorj.Visitor;

import java.util.List;

public class EnumDef implements Node{
public List<String> enumdefs;
/**
 * @param enumdefs
 */
public EnumDef(List<String> enumdefs) {
	super();
	this.enumdefs = enumdefs;
}

@Override
public String toString() {
	return this.getClass().getSimpleName();
}

	@Override
	public void welcome(Visitor v) {
		v.visit(this);
		
	}

}

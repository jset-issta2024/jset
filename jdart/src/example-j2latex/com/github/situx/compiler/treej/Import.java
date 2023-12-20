package com.github.situx.compiler.treej;

import com.github.situx.compiler.visitorj.Visitor;

import java.util.List;

public class Import implements Node {
public List<String> imports;
public boolean stat;
	/**
 * @param imports
 */
public Import(List<String> imports,boolean stat) {
	super();
	this.imports = imports;
	this.stat=stat;
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

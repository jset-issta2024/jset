package com.github.situx.compiler.treej;

import com.github.situx.compiler.visitorj.Visitor;

import java.util.List;

public class Params implements Node {
public List<Node> types,modlist;
public List<String> idents;
	/**
 * @param types
 * @param idents
 */
public Params(List<Node> types,List<Node> modlist,List<String> idents) {
	super();
	this.types = types;
	this.modlist=modlist;
	this.idents = idents;
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

package com.github.situx.compiler.treej;

import com.github.situx.compiler.visitorj.Visitor;

import java.util.List;

public class Comment implements Node {
public String comment;
public List<String> attokens;
	/**
 * @param comment
 */
public Comment(String comment,List<String> attokens) {
	super();
	this.comment = comment;
	this.attokens = attokens;
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

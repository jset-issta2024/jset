package com.github.situx.compiler.treej;

import com.github.situx.compiler.visitorj.Visitor;

import java.util.List;

public class Program implements Node {
	public List<ClassDef> classes;
	public List<Node> enums;
	public Node imports;
	public String packagename;
	
	
	public Program(List<ClassDef> classes,Node imports,List<Node> enums,List<FunctionDef> funs, String packagename) {
		super();
		this.classes=classes;
		this.imports=imports;
		this.enums=enums;
		this.packagename = packagename;
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

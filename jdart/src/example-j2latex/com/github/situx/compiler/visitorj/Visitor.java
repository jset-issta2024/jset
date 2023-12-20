package com.github.situx.compiler.visitorj;

import com.github.situx.compiler.treej.Number;
import com.github.situx.compiler.treej.*;


public interface Visitor {
	void visit(FunctionDef functionDef);
	void visit(Add n);
	void visit(Minus n);
	void visit(Mult n);
	void visit(Div n);
	void visit(Variable variable);
	void visit(FunCall funCall);
	void visit(Literal literal);
	void visit(Program program);
	void visit(Greater greater);
	void visit(Equals equals);
	void visit(AndOr and);
	void visit(SingleCase singleCase);
	void visit(Sem sem);
	void visit(Modulo modulo);
	void visit(Shift shift);
	void visit(Loop loop);
	void visit(Modifier modifier);
	void visit(ClassDef classdef);
	void visit(IfDef ifDef);
	void visit(ReturnType returnType);
	void visit(Params params);
	void visit(Comment comment);
	void visit(FieldDef fieldDef);
	void visit(Import import1);
	void visit(Switch switch1);
	void visit(Casebody casebody);
	void visit(TryCatch tryCatch);
	void visit(EnumDef enumDef);
	void visit(Number number);
	void visit(StringOrCharConst stringOrCharConst);
	void visit(Except except);
	void visit(Assignment assignment);
	void visit(Atom atom);
	void visit(NotDef notDef);
	void visit(Imps imps);
}

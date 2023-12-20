package com.cloudability.bling.ast;

public class UnaryExpression extends Expression {

    private final Expression expression;
    private final Operator operator;

    public UnaryExpression(Expression expression, Operator operator) {
        if (!Operator.MINUS.equals(operator)) {
            throw new IllegalArgumentException(String.format("can't use %s as a unary operator", operator));
        }
        this.expression = expression;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", operator, expression);
    }

    @Override
    public double evaluate() {
        return - expression.evaluate();
    }
    
}
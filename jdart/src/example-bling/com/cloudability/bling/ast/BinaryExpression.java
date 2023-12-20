package com.cloudability.bling.ast;

public class BinaryExpression extends Expression {

    private final Expression leftSide;
    private final Operator operator;
    private final Expression rightSide;

    public BinaryExpression(Expression leftSide, Operator operator, Expression rightSide) {
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }

    public Expression getLeftSide() {
        return leftSide;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s)", operator, leftSide, rightSide);
    }

    @Override
    public double evaluate() {
        double leftValue = leftSide.evaluate();
        double rightValue = rightSide.evaluate();
        switch (operator) {
            case PLUS : return leftValue + rightValue;
            case MINUS : return leftValue - rightValue;
            case MULTIPLY : return leftValue * rightValue;
            case DIVIDE : return leftValue / rightValue;
            case POWER : return Math.pow(leftValue, rightValue);
        }
        throw new RuntimeException("Unknown operator: " + operator);
    }
    
}
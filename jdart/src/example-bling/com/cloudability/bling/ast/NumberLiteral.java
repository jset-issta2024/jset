package com.cloudability.bling.ast;

public class NumberLiteral extends Expression {

    private final double value;

    public NumberLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public double evaluate() {
        return value;
    }

}
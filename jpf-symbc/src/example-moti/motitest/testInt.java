package motitest;

import gov.nasa.jpf.symbc.Debug;

public class testInt {

    public static void branch(int x, int y) {
        if (x < 0) {
            x = -x;
        }
        if (y < 0) {
            y = -y;
        }
        if (x < y) {
            System.out.println("abs(x)<abs(y)");
        } else if (x == 0) {
            System.out.println("x==y==0");
        } else {
            System.out.println("x>=y>=0");
        }
    }

    public static void main(String[] args) {
        branch(1, 2);
    }

}


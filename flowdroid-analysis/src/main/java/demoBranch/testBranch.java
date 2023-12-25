package demoBranch;

import java.util.Scanner;

public class testBranch {
    public static void main(String[] args) {

//        Scanner console = new Scanner(System.in);
//        double price = console.nextDouble();
//        double cost = 0;
//
//        if (price < 100) {
//            cost = price;
//        } else if ((price >= 100) && (price < 500)) {
//            cost = price * 0.9;
//        } else if ((price >= 500) && (price < 2000)) {
//            cost = price * 0.8;
//        } else if ((price >= 2000) && (price < 5000)) {
//            cost = price * 0.7;
//        } else {
//            cost = price * 0.6;
//        }
//        System.out.println(cost);

        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt();
        score = makeSymbolicInt(score);
        if (score > 630) {
            if (score > 650) {
                System.out.println("C9");
            } else {
                System.out.println("985");
            }
        } else {
            if (score > 600) {
                System.out.println("college");
            } else if (score > 450 && score < 600) {
                System.out.println("university");
            } else {
                System.out.println("fail");
            }
        }

    }

    private static int makeSymbolicInt(int score) {
        return score;
    }
}
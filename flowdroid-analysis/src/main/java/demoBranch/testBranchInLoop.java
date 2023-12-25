package demoBranch;

public class testBranchInLoop {
    public static void main(String[] args) {
        int a = 5;
        int b = 8;
        for (int i = 0; i < 5; i++) {
            if (a + i > b) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }
        }
    }
}

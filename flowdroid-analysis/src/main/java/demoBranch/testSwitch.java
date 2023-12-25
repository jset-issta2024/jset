package demoBranch;

public class testSwitch {
    public static void main(String args[]) {
        char grade = 'C';
        char number = '1';

        switch (grade) {
            case 'A':
                System.out.println("well");
                switch (number) {
                    case '1':
                        System.out.println("1");
                        break;
                    case '2':
                        System.out.println("2");
                        break;
                }
                break;
            case 'B':
            case 'C':
                System.out.println("good");
                break;
            case 'D':
                System.out.println("normal");
                break;
            case 'F':
                System.out.println("continue");
                break;
            default:
                System.out.println("unknown");
        }
        System.out.println("your grade is " + grade);
    }
}
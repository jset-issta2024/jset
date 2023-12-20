package motitest;

public class testString {

    static public int m1(char[] c) {
        String str = new String(c);
        System.out.println("Parameters - " + str);
        int state = 0;
//    if(c == null || c.length == 0)  {
//      return -1;
//    }
        for(int i =0; i < c.length; i++) {
            if(c[i] == '[') state = 1;
            else if (state == 1 & c[i] == '{') state = 2;
            else if (state == 2 & c[i] == '<') state = 3;
            else if (state == 3 & c[i] == '*')  {
                state = 4;
                if(c.length == 15) {
                    state = state  + 0;
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("-------- In main!");
        String s = "ab{}cd";
        char[] chars = s.toCharArray();
//        char[] c = {'a', 'b', '{', '}', 'c', 'd'};
        int t = m1(chars);
        System.out.println("--------------------" + t);
    }
}


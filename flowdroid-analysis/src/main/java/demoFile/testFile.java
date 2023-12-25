package demoFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class testFile {
    public static void main(String[] args) {
        String fileName = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/src/flowDroidAnalysis/demoFile/demoFile.txt";
        File file = new File(fileName);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            while ((tempChar = reader.read()) != -1) {
                if (tempChar != '\r') {
                    char outChar = makeSymbolicChar((char) tempChar);
                    System.out.print((char) outChar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static char makeSymbolicChar(char tempChar) {
        char temp = (char) (tempChar + 1);
        return temp;
    }
}

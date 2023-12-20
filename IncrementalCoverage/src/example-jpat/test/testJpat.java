package test;

import coverage.SubjectExecutor;
import jpat.Digest;
import jpat.PepTools;
import jpat.Peptide;

import java.io.IOException;

public class testJpat extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "jpat";
        inputFileName = args[0];
        new testJpat().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        String protease = "KR";  //Trypsin protease
        char first = PepTools.getChar(input, 0);
        Digest digest = new Digest(input, protease, true);
        Peptide[] peptides = digest.seqDigest(); // use protease to divide mSequence
        char last = PepTools.getChar(input, input.length() - 1);
    }
}


package jpat.test;
/*
 * File: [CLASS NAME].java
 *
 * File: JPATTest.java
 * Author : Michael Jones
 *
 * Last Modified On: $Date: 1998/12/14 19:45:44 $
 * Last Modified By: $Author: mjones $
 *
 */

import jpat.*;

import java.io.*;

/**
 * [DESCRIPTIVE COMMENT]
 *
 * @see [OTHER CLASSES (optional)]
 */

public class JPATTest {
	String mSequence = "MDSVCPQGKYIHPQNNSICCTKCHKGTYLYNDCPGPGQDTDCRECESGSFTASENHLRHCLSCSKCRKEMGQVEISSCTV";

	public void test(String test) {
		if (test.equals("-testDigest")) {
			doDigestTest();
		} else if (test.equals("-testFragment")) {
//					doFragmentTest();
		} else System.out.println("Usage java JPATTest -testDigest -testFragment");

	}

	// Interface implementations

	// Public methods
	public static void main(String args[]) {
		args = new String[]{"-testDigest"};
		if (args.length != 1) {
			System.out.println("Usage java JPATTest -testDigest -testFragment");
			System.exit(1);
		}
		new JPATTest().test(args[0]);
	}

	public void e1() {
	}

	;

	public void e2() {
	}

	;

	// Protected methods
/*
		Digests a large protein sequence
*/
	void doDigestTest() {

		String protease = "KR";  //Trypsin protease
		char first = PepTools.getChar(mSequence, 0);
		if (first == 'A') {
			e1();
		}
		Digest digest = new Digest(mSequence, protease, true);
		Peptide[] peptides = digest.seqDigest(); // use protease to divide mSequence
		char last = PepTools.getChar(mSequence, mSequence.length() - 1);
		if (last == 'B') {
			e2();
		}
//		int i=0;
//		try{
//			while(true){
//					System.out.println(peptides[i].cTerm + " " + peptides[i].pepSequence + " " + peptides[i].nTerm);
//					i++;
//			}
//		}catch(ArrayIndexOutOfBoundsException e){
//			
//		}
	}
}


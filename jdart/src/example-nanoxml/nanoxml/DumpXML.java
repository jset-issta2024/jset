package nanoxml;

import java.io.CharArrayReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class DumpXML
{
    public static void main(String[] args) throws Exception
      
    {
        String input_xml = "<s>good</s>";
    	start(input_xml.toCharArray());
//       testBug1(); 
//       testSPFbug1();
//    	StringBuffer buf = new StringBuffer();
//       tryDebug_resolveEntity(buf);
    }

    //public static String input_xml = "<\n>&;";
    /***
     * test driver for test all funs. 
     * search depth=27 can find a bug. in resolveEntity().
     * @throws Exception 
     */
    
    public static void start(char[] data) {
    	try {
    		count = 0;

            XMLElement xml = new XMLElement();           //2

            DumpXML.count = 0;
            xml.parseFromReader(new CharArrayReader(data));                 //3


                //	Debug.printCurrentPC();

//    		System.out.println(xml);                      //4


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /***
     * test for the bug find in testDriver1.
     * really recreate the bug
     */
    public static void testBug1() {
//    	XMLElement xml = new XMLElement();      
    	StringBuffer buf = new StringBuffer();
//    	try {
//    		simulate_resolveEntity(buf);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    
    public static void testSPFbug1() {
    	StringBuffer bug = new StringBuffer();
    	testSPFbug1(bug);
    }
    
    
    public static void testSPFbug1(StringBuffer buf) {
    	XMLElement xml = new XMLElement();
    	char ch = '\0';
        StringBuffer keyBuf = new StringBuffer();
//        int count = 0;
//        for (;;) {
//            ch = (char)Debug.makeSymbolicInteger("char_" + count++);
//            Verify.ignoreIf(ch < 0);
//            if (ch == ';') {
//                break;
//            }
//            keyBuf.append(ch);
//        }
        String key = keyBuf.toString();
//        Debug.printPC("########################################prepare to out of range index, PC: \n");
        System.out.println("########################################" + key);
        //debug
        if (key.charAt(0) == '#') {
            try {
                if (key.charAt(1) == 'x') {
                    ch = (char) Integer.parseInt(key.substring(2), 16);
                } else {
                    ch = (char) Integer.parseInt(key.substring(1), 10);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            } 
            buf.append(ch);
        } else {
            char[] value = (char[]) xml.entities.get(key);
            if (value == null) {
                throw xml.unknownEntity(key);
            }
            buf.append(value);
        }
    }
    
    
    public static void tryDebug_resolveEntity(StringBuffer buf)
        throws IOException
    {
    	StringBuffer keyBuf = new StringBuffer();
        char ch ='1';
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char2");
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char3");
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char4");
        keyBuf.append(ch);
        
        String key = keyBuf.toString();
        ch = (char) Integer.parseInt(key.substring(2), 16);
//        Debug.printPC("########################################prepare to out of range index, PC: \n");
        if (key.charAt(0) == '#') {
            try {
                if (key.charAt(1) == 'x') {
                    ch = (char) Integer.parseInt(key.substring(2), 16);
                } else {
                    ch = (char) Integer.parseInt(key.substring(1), 10);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            }
            buf.append(ch);
        } else {
//            char[] value = (char[]) this.entities.get(key);
//            if (value == null) {
//                throw this.unknownEntity(key);
//            }
//            buf.append(value);
        }
    }


	public static int count = 0;
	public static int maxSymbolNum = 20;

    
    
}
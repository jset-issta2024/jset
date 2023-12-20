package com.iheartradio.m3u8;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

class M3uScanner {
    //private final Scanner mScanner;
    private final boolean mSupportsByteOrderMark;
    private final StringBuilder mInput = new StringBuilder();

    private boolean mCheckedByteOrderMark;
    
    private final String[] mScanner;
    private int pos;
    private final int len;
/*
    M3uScanner(InputStream inputStream, Encoding encoding) {
    	//changed by ylluo,to avoid using some methods that not Completed in jpf
    	mScanner = new Scanner(inputStream).useLocale(Locale.US).useDelimiter(Constants.PARSE_NEW_LINE);
        //mScanner = new Scanner(inputStream, encoding.value).useLocale(Locale.US).useDelimiter(Constants.PARSE_NEW_LINE);
        mSupportsByteOrderMark = encoding.supportsByteOrderMark;
    }

    String getInput() {
        return mInput.toString();
    }

    boolean hasNext() {
        return mScanner.hasNext();
    }

    String next() throws ParseException {
        String line = mScanner.next();

        if (mSupportsByteOrderMark && !mCheckedByteOrderMark) {
            if (!line.isEmpty() && line.charAt(0) == Constants.UNICODE_BOM) {
                line = line.substring(1);
            }

            mCheckedByteOrderMark = true;
        }

        mInput.append(line).append("\n");
        return line;
    }
    */
    M3uScanner(InputStream inputStream, Encoding encoding) throws IOException{
    	byte[] flush = new byte[1024];
    	int t = -1;
    	t = inputStream.read(flush);
    	String str = new String(flush, 0, t);
    	//System.out.println(str);
    	
    	mScanner = str.split("\n");
    	mSupportsByteOrderMark = encoding.supportsByteOrderMark;
    	pos = 0;
    	len = mScanner.length;
  
    }
    
    String getInput() {
        return mInput.toString();
    }
    
    boolean hasNext() {
        //return mScanner.hasNext();
    	return (pos<len);
    }
    
    String next() throws ParseException {
        String line = mScanner[pos].toString();
        pos++;
        if (mSupportsByteOrderMark && !mCheckedByteOrderMark) {
            if (!line.isEmpty() && line.charAt(0) == Constants.UNICODE_BOM) {
                line = line.substring(1);
            }

            mCheckedByteOrderMark = true;
        }

        mInput.append(line).append("\n");
        return line;
    }
}

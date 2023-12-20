package com.mpatric.mp3agic.MP3Decoder;


//import gov.nasa.jpf.jdart.Debug;

import java.io.IOException;
import java.io.InputStream;

public class MP3Decoder{
    private InputStream is;
    private int curPos = 0;
    private int bitmapOffset; // starting position of image data
    private int width; // image width in pixels
    private int height; // image height in pixels
    private short bitsPerPixel; // 1, 4, 8, or 24 (no color map)
//    private int compression; // 0 (none), 1 (8-bit RLE), or 2 (4-bit RLE)
    private int actualSizeOfBitmap;
    private int scanLineSize;
    private int actualColorsUsed;
    private byte r[], g[], b[]; // color palette
    private int noOfEntries;
    private byte[] byteData; // Unpacked data
    private int[] intData; // Unpacked data

    public MP3Decoder()
    {
    }

    static int count=0;

     
    protected int readInt() throws IOException
    {
    	
        int b1 = is.read();
//        char b11 = Debug.makeConcolicChar("sym_" + curPos, ""+b1);
        char b11 = (char) b1;
        System.out.println(b1+"sym_" + curPos+" : " + (int)b11);
        curPos++;
        int b2 = is.read();
//        char b21 = Debug.makeConcolicChar("sym_" + curPos, ""+b2);
        char b21 = (char) b2;
        System.out.println(b2+"sym_" + curPos+" : " + (int)b21);
        curPos++;
        int b3 = is.read();
//        char b31 = Debug.makeConcolicChar("sym_" + curPos, ""+b3);
        char b31 = (char) b3;
        System.out.println(b3+"sym_" + curPos+" : " + (int)b31);
        curPos++;
        int b4 = is.read();
//        char b41 = Debug.makeConcolicChar("sym_" + curPos, ""+b4);
        char b41 = (char) b4;
        System.out.println(b4+"sym_" + curPos+" : " + (int)b41);
        curPos++;
        return ((((int)b41) << 24) + (((int)b31) << 16) + (((int)b21) << 8) + (((int)b11) << 0));

    }

    protected short readShort() throws IOException
    {
    	int b1 = is.read();
//        char b11 = Debug.makeConcolicChar("sym_" + curPos, ""+b1);
        char b11 = (char) b1;
        System.out.println(b1+"sym_" + curPos+" : " + (int)b11);
        curPos++;    	
    	int b2 = is.read();
//        char b21 = Debug.makeConcolicChar("sym_" + curPos, ""+b2);
        char b21 = (char) b2;
        System.out.println(b2+"sym_" + curPos+" : " + (int)b21);
        curPos++;
    	return (short) ((((int)b21) << 8) + (int)b11);//(short) ((b2 << 8) + b1);
    }

    protected void getFileHeader() throws IOException, Exception
    {
        // Actual contents (14 bytes):
        int fileType = 17481;// always "BM"
        fileType = readShort();
        
        System.out.println("****************************************************");
        System.out.println(fileType);
        System.out.println("****************************************************");
        
        if (fileType != 17481) throw new Exception("Not a MP3 file"); // wrong file type
        bitmapOffset = readInt();
    }

    protected void getBitmapHeader() throws IOException
    {
        int sizeOfBitmap; // size of bitmap in bytes (may be 0: if so, calculate)
        int colorsUsed; // no. of colors in palette (if 0, calculate)
        width = readInt();
        System.out.println("-----width : " + width);
        height = readInt();
        System.out.println("-----height : " + height);        
        bitsPerPixel = readShort();
//        compression = readInt();
        sizeOfBitmap = readInt();
        colorsUsed = readInt();
        // Scan line is padded with zeroes to be a multiple of four bytes
        scanLineSize = ((width * bitsPerPixel + 31) / 32) * 4;
        if (sizeOfBitmap != 0) actualSizeOfBitmap = sizeOfBitmap;
        else
        // a value of 0 doesn't mean zero - it means we have to calculate it
        actualSizeOfBitmap = scanLineSize * height;
        if (colorsUsed != 0) actualColorsUsed = colorsUsed;
        else
        // a value of 0 means we determine this based on the bits per pixel
        System.out.println("---bitsPerPixel : " + bitsPerPixel);
        if (bitsPerPixel < 16) actualColorsUsed = 1 << bitsPerPixel;
        else actualColorsUsed = 0; // no palette
        System.out.println("---actualColorsUsed : " + actualColorsUsed);
    }
  
    protected void getPalette() throws Exception
    {
        noOfEntries = actualColorsUsed;
        //IJ.write("noOfEntries: " + noOfEntries);
        System.out.println("-----noOfEntries : " + noOfEntries);
        if (noOfEntries > 1024) { 
//            if (width > 0 && height < 0) {
//            	System.out.println("----negative length-----");
//            }
            throw new Exception();
        }
        if (noOfEntries > 0)
        {        
        	System.out.println("numberofEntry:+"+noOfEntries);
            r = new byte[noOfEntries];
            g = new byte[noOfEntries];
            b = new byte[noOfEntries];
            for (int i = 0; i < noOfEntries; i++)
            {
                b[i] = (byte) is.read();
                g[i] = (byte) is.read();
                r[i] = (byte) is.read();
                curPos += 4;
            }
        }
    }

    protected void unpack(byte[] rawData, int rawOffset, int[] intData, int intOffset, int w)
    {
        int j = intOffset;
        int k = rawOffset;
        int mask = 0xff;
        for (int i = 0; i < w; i++)
        {
            int b0 = (((int) (rawData[k++])) & mask);
            int b1 = (((int) (rawData[k++])) & mask) << 8;
            int b2 = (((int) (rawData[k++])) & mask) << 16;
            intData[j] = 0xff000000 | b0 | b1 | b2;
            j++;
        }
    }

    protected void unpack(byte[] rawData, int rawOffset, int bpp, byte[] byteData, int byteOffset, int w) throws Exception
    {
        int j = byteOffset;
        int k = rawOffset;
        byte mask;
        int pixPerByte;
        switch (bpp)
        {
        case 1:
            mask = (byte) 0x01;
            pixPerByte = 8;
            break;
        case 4:
            mask = (byte) 0x0f;
            pixPerByte = 2;
            break;
        case 8:
            mask = (byte) 0xff;
            pixPerByte = 1;
            break;
        default:
            throw new Exception("Unsupported bits-per-pixel value");
        }
        for (int i = 0;;)
        {
            int shift = 8 - bpp;
            for (int ii = 0; ii < pixPerByte; ii++)
            {
                byte br = rawData[k];
                br >>= shift;
                byteData[j] = (byte) (br & mask);
                //System.out.println("Setting byteData[" + j + "]=" + Test.byteToHex(byteData[j]));
                j++;
                i++;
                if (i == w) return;
                shift -= bpp;
            }
            k++;
        }
    }

    protected int readScanLine(byte[] b, int off, int len) throws IOException
    {
        int bytesRead = 0;
        int l = len;
        int r = 0;
        while (len > 0)
        {  
            bytesRead = is.read(b, off, len);
            if (bytesRead == -1) return r == 0 ? -1 : r;
            if (bytesRead == len) return l;
            len -= bytesRead;
            off += bytesRead;
            r += bytesRead;
        }
        return l;
    }

    protected void getPixelData() throws IOException, Exception
    {
        byte[] rawData; // the raw unpacked data
        // Skip to the start of the bitmap data (if we are not already there)
        long skip = bitmapOffset - curPos;
        if (skip > 0)
        {
            is.skip(skip);
            curPos += skip;
        }
        int len = scanLineSize;
        System.out.println("-----bitsPerPixel : " + bitsPerPixel);
        System.out.println("-----actualSizeOfBitmap : " + actualSizeOfBitmap);
        System.out.println("width*height:"+width*height);
        if (bitsPerPixel > 8) intData = new int[width * height];
        else byteData = new byte[width * height];
        System.out.println("width*height2:"+width*height);
        System.out.println("actualSizeOfBitmap+"+actualSizeOfBitmap);
        if (actualSizeOfBitmap > 1024) {
        	throw new Exception(); 
        }
        System.out.println("actualSizeOfBitmap2+"+actualSizeOfBitmap);
        if (actualSizeOfBitmap > 0 && height > 0) { // added by zhenbang to avoid integer overflow "height"	
        	rawData = new byte[actualSizeOfBitmap];
        	int rawOffset = 0;
        	int offset = (height - 1) * width;
        	for (int i = height - 1; i >= 0; i--)
        	{
        		//System.out.println(i);
        		int n=0;
        		// the original is : int n=readScanLine(rawData,rawOffset,len); which will throw index illegal: len is very big, but rawData's size is actualSizeOfBitmap(only 1024), the guard is add by yhb!
        		if(len<actualSizeOfBitmap)
        		n = readScanLine(rawData, rawOffset, len);
        		if (n < len) throw new Exception("Scan line ended prematurely after " + n + " bytes");
        		if (bitsPerPixel > 8 && intData.length > 0)
        		{
        			// Unpack and create one int per pixel
        			unpack(rawData, rawOffset, intData, offset, width);
        		}
        		else if (byteData.length > 0) //added by zhenbang to avoid the array index bug in unpack
        		{
        			// Unpack and create one byte per pixel
        			unpack(rawData, rawOffset, bitsPerPixel, byteData, offset, width);
        		}
        		rawOffset += len;
        		offset -= width;
        	}
        }
    }

    public void read(InputStream is) throws IOException, Exception
    {
        this.is = is;
        
        getFileHeader();
        getBitmapHeader();
        getPalette();
        getPixelData();
    }

    
}
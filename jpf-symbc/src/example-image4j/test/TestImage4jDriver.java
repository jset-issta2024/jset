package test;

//import gov.nasa.jpf.jdart.Debug;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.sf.image4j.codec.bmp.BMPDecoder;

public class TestImage4jDriver {
	
	public static void main(String[] args) throws Exception {
		start();
	}
	
	public static void start() throws Exception{

//	    /home/lmx/Documents/GitHub/jpf8/jpf-symbc/src/example-image4j/test/input.bmp

        File inFile = new File("src/example-image4j/test/input.bmp");
        
        // load and decode BMP file
		BufferedImage image = BMPDecoder.read(new File("jpf-symbc/src/example-image4j/test/input.bmp"));
        
        System.out.println("BMP decoding...OK");
        
        // display summary of decoded images
        
        System.out.println("  image summary:");
        
        int bpp = image.getColorModel().getPixelSize();
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("    # " + ": size="+width+"x"+height+"; colour depth="+bpp+" bpp");
        
        // save images as separate BMP files
        System.out.println("  saving separate images:");
        
        String name = "inFile";
        File bmpFile = new File(name+".bmp");
        
        // write BMP
        System.out.println("    writing '"+name+".bmp'");
        net.sf.image4j.codec.bmp.BMPEncoder.write(image, bmpFile);
           
        System.out.println("BMP encoding...OK");
	}
	
}

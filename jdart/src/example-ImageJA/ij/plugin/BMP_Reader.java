package ij.plugin;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import ij.*;
import ij.io.*;



/** This plugin reads BMP files. If 'arg' is empty, it
        displays a file open dialog and opens and displays the 
        selected file. If 'arg' is a path, it opens the 
        specified file and the calling routine can display it using
        "((ImagePlus)IJ.runPlugIn("ij.plugin.BMP_Reader", path)).show()".
        */
public class BMP_Reader extends ImagePlus implements PlugIn {

        public void run(String arg) {
                OpenDialog od = new OpenDialog("Open BMP...", arg);
                String directory = od.getDirectory();
                String name = od.getFileName();
                System.out.println(name);
                if (name==null)
                        return;
                String path = directory + name;
                System.out.println(path);

                //IJ.showStatus("Opening: " + path);
                BMPDecoder bmp = new BMPDecoder();
                FileInputStream  is = null;
                try {
                        is = new FileInputStream(path);
                        bmp.read(is);
                } catch (Exception e) {
                        String msg = e.getMessage();
                        if (msg==null || msg.equals(""))
                                msg = ""+e;
                        IJ.error("BMP Reader", msg);
                        return;
                } finally {
					if (is!=null) {
						try {
							is.close();
						} catch (IOException e) {}
					}
				}

                MemoryImageSource mis = bmp.makeImageSource();
                if (mis==null) IJ.log("BMP_Reader: mis=null");
                Image img = Toolkit.getDefaultToolkit().createImage(mis);
                FileInfo fi = new FileInfo();
                fi.fileFormat = FileInfo.BMP;
                fi.fileName = name;
                fi.directory = directory;
                setImage(img);
                setTitle(name);
                setFileInfo(fi);
                if (bmp.topDown)
                    getProcessor().flipVertical();
                if (arg.equals(""))
                    show();
        }
        
}



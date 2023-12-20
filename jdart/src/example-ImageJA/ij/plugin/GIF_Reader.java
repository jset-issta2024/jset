package ij.plugin;
import ij.*;
import ij.process.*;
import ij.io.*;

import java.awt.*;

/** This plugin opens GIFs and Animated GIFs. */
public class GIF_Reader extends ImagePlus implements PlugIn {

	public void run(String arg) {
		OpenDialog od = new OpenDialog("Open GIF...", arg);
		String name = od.getFileName();
		if (name==null)
			return;
		String dir = od.getDirectory();
		GifDecoder d = new GifDecoder();
		System.out.println(dir+name);
		int status = d.read(dir+name);
		int n = d.getFrameCount();
		ImageStack stack = null;
		if (n==1) {
			Image img = Toolkit.getDefaultToolkit().createImage(dir+name);
			setImage(img);
			setTitle(name);
		} else {
			for (int i=0; i < n; i++) {
				ImageProcessor frame = d.getFrame(i);
				if (i==0)
					stack = new ImageStack(frame.getWidth(), frame.getHeight());
				int t = d.getDelay(i);	// display duration of frame in milliseconds
				stack.addSlice(null, frame);
			}
			if (stack==null)
				return;
			setStack(name, stack);
			if (getType()==COLOR_RGB)
				Opener.convertGrayJpegTo8Bits(this);
		}
		FileInfo fi = new FileInfo();
		fi.fileFormat = fi.GIF_OR_JPG;
		fi.fileName = name;
		fi.directory = dir;
		setFileInfo(fi);
	}

}
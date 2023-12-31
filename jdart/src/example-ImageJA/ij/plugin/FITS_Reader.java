package ij.plugin;
import java.awt.*;
import java.io.*;
import java.util.zip.GZIPInputStream;
import ij.*;
import ij.io.*;
import ij.process.*;
import ij.measure.*;

/** Opens and displays FITS images. The FITS format is 
	described at "http://fits.gsfc.nasa.gov/fits_standard.html".
*/
public class FITS_Reader extends ImagePlus implements PlugIn {

	public void run(String arg) {
		OpenDialog od = new OpenDialog("Open FITS...", arg);
		String directory = od.getDirectory();
		String fileName = od.getFileName();
		if (fileName==null)
			return;
		IJ.showStatus("Opening: " + directory + fileName);
		FitsDecoder fd = new FitsDecoder(directory, fileName);
		FileInfo fi = null;
		try {
			fi = fd.getInfo();
		} catch (IOException e) {}
		if (fi!=null && fi.width>0 && fi.height>0 && fi.offset>0) {
			FileOpener fo = new FileOpener(fi);
			ImagePlus imp = fo.openImage();
			setStack(fileName, imp.getStack());
			Calibration cal = imp.getCalibration();
			if (fi.fileType==FileInfo.GRAY16_SIGNED && fd.bscale==1.0 && fd.bzero==32768.0)
				cal.setFunction(Calibration.NONE, null, "Gray Value");
			setCalibration(cal);
			setProperty("Info", fd.getHeaderInfo());
			setFileInfo(fi); // needed for File->Revert
			if (arg.equals("")) show();
		} else
			IJ.error("This does not appear to be a FITS file.");
		IJ.showStatus("");
	}

}


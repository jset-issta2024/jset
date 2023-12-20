package test;

import org.joda.time.tz.Provider;
import org.joda.time.tz.ZoneInfoCompiler;
import org.joda.time.tz.ZoneInfoProvider;

import java.io.*;


public class JodatimeExample {
    static final String BROKEN_TIMEZONE_FILE =
            "# Incomplete Rules for building America/Los_Angeles time zone.\n" +
            "\n" +
            "Rule    US  1918    1919    -   Mar lastSun 2:00    1:00    D\n" +
            "Rule    \n" ; // this line is intentionally incomplete
    static final String BROKEN_TIMEZONE_FILE_2 =
            "# Incomplete Zone for building America/Los_Angeles time zone.\n" +
            "\n" +
            "Rule    CA  1948    only    -   Mar 14  2:00    1:00    D\n" +
            "Rule    CA  1949    only    -   Jan  1  2:00    0   S\n" +
            "\n" +
            "Zone "; // this line is intentionally left incomplete
    
    private Provider compileAndLoad(String data) throws Exception {
        File tempDir = createDataFile(data);
        File destDir = makeTempDir();

        ZoneInfoCompiler.main(new String[] {
            "-src", tempDir.getAbsolutePath(),
            "-dst", destDir.getAbsolutePath(),
            "tzdata"
        });

        // Mark all files to be deleted on exit.
        deleteOnExit(destDir);

        return new ZoneInfoProvider(destDir);
    }
    private File createDataFile(String data) throws IOException {
        File tempDir = makeTempDir();

        File tempFile = new File(tempDir, "tzdata");
        tempFile.deleteOnExit();

        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));

        FileOutputStream out = new FileOutputStream(tempFile);
        byte[] buf = new byte[1000];
        int amt;
        while ((amt = in.read(buf)) > 0) {
            out.write(buf, 0, amt);
        }
        out.close();
        in.close();

        return tempDir;
    }

    private File makeTempDir() {
        File tempDir = new File(".");//System.getProperty("java.io.tmpdir"));
        tempDir = new File(tempDir, "joda-test");//-" + (new java.util.Random().nextInt() & 0xffffff));
        tempDir.mkdirs();
        tempDir.deleteOnExit();
        return tempDir;
    }

    private void deleteOnExit(File tempFile) {
        tempFile.deleteOnExit();
        if (tempFile.isDirectory()) {
            File[] files = tempFile.listFiles();
            for (int i=0; i<files.length; i++) {
                deleteOnExit(files[i]);
            }
        }
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JodatimeExample().start();
	}
	
	public void start(){
       try{
    	   Provider provider = compileAndLoad(BROKEN_TIMEZONE_FILE);
    	 
       }catch(Exception e){
    	   e.printStackTrace();
       }
	}

}

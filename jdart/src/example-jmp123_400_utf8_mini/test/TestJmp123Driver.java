package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jmp123.demo.MiniPlayer;

public class TestJmp123Driver {
	
	public static void main(String[] args) throws Exception {
		start();
	}
	
	public static void start() throws Exception{
		
		MiniPlayer player = new MiniPlayer(null);	// null改为new Audio()可正常播放

		try {
			long t0 = System.nanoTime();

			System.out.println(System.getProperty("user.dir"));

			String msg = player.open("jdart/src/example-jmp123_400_utf8_mini/test/test2.mp3");
			player.run();

			long t1 = System.nanoTime() - t0;

//			File file = new File("src/example-jmp123_400_utf8_mini/test/dummyframes.mp3");
//			long length = file.length();
//			int frames = player.getFrameCount();
//			System.out.println(msg);
//			System.out.printf("length: %d bytes, %d frames\n", length, frames);
//			System.out.printf("elapsed time: %,dns (%.9fs, %.2f fps)\n", t1, t1/1e9, frames/(t1/1e9));
//
//	        InputStream inputstream = new FileInputStream(file);
//
//	      int curPos = 0;
//	      for (;;) {
//	          int n = inputstream.read(); // 反复调用read()方法，直到返回-1
//	          System.out.println(n);
//	          char symbolic = Debug.makeConcolicChar("sym_" + curPos, ""+n);
//	          System.out.println(n+"sym_" + curPos+" : " + (int)symbolic);
//	          curPos++;
//	          if (n == -1) {
//	              break;
//	          }
//	          System.out.println(n); // 打印byte的值
//	      }
//	      inputstream.close(); // 关闭流

			int frames = player.getFrameCount();
			System.out.println(msg);
			System.out.printf("elapsed time: %,dns (%.9fs, %.2f fps)\n", t1, t1/1e9, frames/(t1/1e9));

		} catch (IOException e) {
			e.printStackTrace();
		}     

		
	}
	
	


}

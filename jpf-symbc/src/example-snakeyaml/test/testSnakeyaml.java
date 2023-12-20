package test;

//import gov.nasa.jpf.jdart.Debug;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class testSnakeyaml {

	public static void main(String[] args) throws IOException {
		start();
	}
	public static void start() throws IOException {

		try {
			Yaml yaml = new Yaml();
			String s = "ip: '192.168.102.31'";

//		File dumpFile = new File("src/example-snakeyaml/test/test.yaml");

			//获取test.yaml文件中的配置数据，然后转换为obj，
			File file = new File("jpf-symbc/src/example-snakeyaml/test/test.yaml");
			FileInputStream in = new FileInputStream(file);
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			s = new String(buffer, "UTF-8");

			Iterable<Object> ret = yaml.loadAll(s);
			for (Object o : ret) {
//	        System.out.println(o);
			}
		} catch(ScannerException e) {

		}
	}

}

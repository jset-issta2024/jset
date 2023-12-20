package test;

import java.io.FileReader;
import com.esotericsoftware.yamlbeans.YamlReader;

public class TestYamlbeansDriver {

	public static void main(String[] args) throws Exception {
		start();
	}
	
	public static void start() throws Exception{
	    
	    YamlReader reader = new YamlReader(new FileReader("src/example-yamlbeans/test/test.yml"));
	    System.out.println(reader);
	    Object object = reader.read();
	    System.out.println(object);
		
	}
	
	


}

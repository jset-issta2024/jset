package test;

import org.yaml.snakeyaml.Yaml;

public class testSnakeyaml {

	public static void main(String[] args) {
		String s="ip: '192.168.102.31'";
		char[] data = s.toCharArray();
		start(data);
	}

	public static void start(char[] data) {
		Yaml yaml = new Yaml();
		String s = data.toString();

//		File dumpFile = new File("src/example-snakeyaml/test/test.yaml");
		//获取test.yaml文件中的配置数据，然后转换为obj，
//		File file=new File("src/example-snakeyaml/test/test.yaml");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		s=new String(buffer,"UTF-8");
		try {
			Iterable<Object> ret = yaml.loadAll(s);
			for (Object o : ret) {
				System.out.println(o);
			}
		} catch (org.yaml.snakeyaml.parser.ParserException e) {
			e.printStackTrace();
		}

	}

}

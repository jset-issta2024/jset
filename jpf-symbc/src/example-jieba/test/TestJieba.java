package test;

//import gov.nasa.jpf.jdart.Debug;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import gov.nasa.jpf.symbc.Debug;

public class TestJieba {
	public static void main(String[] args){
		start();
	}
	public static void start() {
		//method sentenceProcess is the basic function
		//mode(search、index）all use sentenceProcess，and index is more complicated than search
		//index have to work with dictionary
	    JiebaSegmenter segmenter = new JiebaSegmenter();
	    String s = "这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。";
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
				System.out.print((int)data[i] + " ");
			} 
		} 
		String str = new String(data);
		System.out.println();
		segmenter.process(str, SegMode.SEARCH);
	    //System.out.println(segmenter.process(s, SegMode.SEARCH).toString());
	}
}

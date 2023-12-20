package kr.ac.cau.popl.gauthierplm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TagRecord {
	private Map<String, Integer> record;
	
	public TagRecord() {
		this.record = new HashMap<String, Integer>();
	}
	
	public void record(Token tagName) {
		String name = tagName.toString().toLowerCase();
		//System.out.println("ssssss :"+record.get(name));
		int res = 0;
		if (record.get(name)!=null){
			res = record.get(name);
		}
		record.put(name, res + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		
		if (record.size() != 0) {
			for(Entry<String, Integer> entry : record.entrySet()) {
			    String key = entry.getKey();
			    Integer value = entry.getValue();
				
			    sb.append("<").append(key).append("> ").append(value).append("\n");
			}
		}
		else { 
			sb.append("No tag found");
		}
		
		return sb.toString();
	}
}

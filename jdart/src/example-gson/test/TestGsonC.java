package test;

import com.google.gson.Gson;

public class TestGsonC {
	public static void main(String[] args) throws Exception{
		Gson gson = new Gson();
		
		int[] ints = {1, 2, 3, 4, 5};
		String[] strings = {"abc", "def", "ghi"};

		// Serialization
		gson.toJson(ints);     // ==> [1,2,3,4,5]
		String str = gson.toJson(strings);  // ==> ["abc", "def", "ghi"]
		// Deserialization
		int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class); 
		
		System.out.println(str);
	}

	
}

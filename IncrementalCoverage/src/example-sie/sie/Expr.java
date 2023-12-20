package sie;

import java.util.ArrayList;

public class Expr {
	public static ArrayList<Node> numbers=new ArrayList<Node>();
	public static ArrayList<Node> ops=new ArrayList<Node>();
	
	public static int eval(){

		int res=0;
		
		for(Node opNode : ops){
			char op=opNode.data;
			char left=numbers.get(0).data;
			numbers.remove(0);
			char right=numbers.get(0).data;
			numbers.remove(0);
			if(op=='*'){
//				numbers.add()

			}
			if(op=='+'){
//				
			}
			
			numbers.add(new Node('1'));
		}

		return res;
	}
}

package test;

public class ShowMemory {
	
	public static void main(String[] args){
		ShowMemory.showMemory();
	}

	public static void showMemory() {
		Runtime runtime = Runtime.getRuntime();
		int free = (int)runtime.freeMemory()/1024/1024;
		int total = (int) runtime.totalMemory()/1024/1024;
		System.out.println("free:"+free+"Mb, total:"+total+"Mb");
		
	}

}

package demoJni;

public class testJni {
    static {
        System.loadLibrary("hello"); // Load native library at runtime
        // hello.dll (Windows) or libhello.so (Unixes)
    }

    // Declare a native method sayHello() that receives nothing and returns void
    private native void sayHello();

    // Test Driver
    public static void main(String[] args) {

        String path = testJni.class.getClassLoader().getResource("//").getPath();
        System.load(path + "demoLib01.dll");
        System.load(path + "demoLib02.dll");
        System.out.println(System.getProperty("java.library.path"));

        new testJni().sayHello();  // invoke the native method
    }
}
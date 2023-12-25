package demoReflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class testReflection {

    private String name;
    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Class demo = Class.forName("com.FanSe.DemoOne");

        Method setNameMethod = demo.getMethod("setName", String.class);

        Constructor demooneConstructor = demo.getConstructor();

        Object newInstance = demooneConstructor.newInstance();

        setNameMethod.invoke(newInstance, "me");

        Method getName = demo.getMethod("getName");

        System.out.println(getName.invoke(newInstance) + "good");
    }

}
package yyl.example.basic.reflect;

/**
 * java.lang.Class.getEnclosingClass() 方法返回直接封闭类的底层类。如果这个类是一个顶级类此方法返回null。
 */
public class EnclosingClassTest {

    public static void main(String[] args) {
        // 得到子类对象的外围类
        System.out.println(Inner.class.getEnclosingClass());
        // 得到匿名子类对象的外围类
        System.out.println(new Object() {}.getClass().getEnclosingClass());
        // 一个顶级类此方法返回null
        System.out.println(EnclosingClassTest.class.getEnclosingClass());
    }

    private static class Inner {}
}

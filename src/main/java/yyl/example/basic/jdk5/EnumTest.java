package yyl.example.basic.jdk5;

/**
 * JDK1.5引入了新的类型——枚举<br>
 * 有了枚举，可以把相关的常量分组到一个枚举类型里，而且枚举提供了比常量更多的方法。<br>
 * 所有的枚举都继承自java.lang.Enum类。由于Java 不支持多继承，所以枚举对象不能再继承其他类。<br>
 * 枚举类型对象之间的值比较，可以使用==，直接来比较值，是否相等的，可以不使用equals方法<br>
 */
public class EnumTest {

    /** 常量枚举 */
    public enum Signal {
        RED, GREEN, BLUE
    }

    public static void main(String[] args) {

        // 使用name 获得枚举
        Signal blue1 = Signal.valueOf("BLUE");
        Signal blue2 = Enum.valueOf(Signal.class, "BLUE");
        System.out.println("(blue1==blue2) = " + (blue1 == blue2));

        // 遍历枚举
        Signal[] signals = Signal.values();
        for (int i = 0; i < signals.length; i++) {
            Signal signal = signals[i];
            System.out.println(signal.name() + " " + signal.ordinal());
        }

        // 随机获得一个枚举值
        Signal random = signals[((int) (Math.random() * signals.length))];

        // 枚举可以使用 switch
        switch (random) {
            case RED:
                System.out.println("random=red");
                break;
            case GREEN:
                System.out.println("random=green");
                break;
            case BLUE:
                System.out.println("random=blue");
                break;
            default:
                System.out.println("random=other");
        }
    }
}

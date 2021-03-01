package yyl.example.basic.test;

/**
 * 输出退格符号<br>
 * (\b的含义是，将光标从当前位置向前（左）移动（不是删除）一个字符（遇到\n或\r则停止移动），并从此位置开始输出后面的字符)<br>
 * 可以用来做单行输出，例如显示进度百分比，或者进度条
 */
public class PrintlnBackspaceTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Progress: ");
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print('#');
            }
            for (int j = i; j < 100; j++) {
                System.out.print('-');
            }

            System.out.print(' ');
            for (int j = i < 10 ? 2 : i < 100 ? 1 : 0; j > 0; j--) {
                System.out.print(' ');
            }
            System.out.print(i);
            System.out.print('%');
            Thread.sleep(100);
            for (int j = 0; j < 105; j++) {
                System.out.print('\b');
            }
        }
        System.out.println();
    }
}

package yyl.example.basic.print;

/**
 * “动态更新的进度条” 或者百分比滚动条。核心思路是：<br>
 * 使用 \r 回到行首覆盖当前行<br>
 * 不打印 \n，否则会换行<br>
 * 根据进度输出字符或百分比<br>
 * 需要在CMD/终端里查看运行结果<br>
 */
public class ProgressBarExample {
    public static void main(String[] args) throws InterruptedException {
        int total = 100;
        for (int i = 0; i <= total; i++) {
            System.out.print("\rProgress: " + i + "%");
            Thread.sleep(50);
        }
        System.out.println("\nDone!");
    }
}
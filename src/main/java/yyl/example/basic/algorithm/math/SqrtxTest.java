package yyl.example.basic.algorithm.math;

/**
 * 平方根运算
 */
public class SqrtxTest {

    public static void main(String[] args) {
        System.out.println(sqrt(0));// 0.00
        System.out.println(sqrt(1));// 1.00
        System.out.println(sqrt(4));// 2.00
        System.out.println(sqrt(10));// 3.16
        System.out.println(sqrt(2147395599));// 46339.99
    }

    /**
     * 牛顿法求解平方根 牛顿迭代法（Newton's method）又称为牛顿-拉夫逊（拉弗森）方法（Newton-Raphson method）<br>
     * 它是牛顿在17世纪提出的一种在实数域和复数域上近似求解方程的方法。<br>
     * 假首先猜测一个值z=x/2，然后根据迭代公式 z{n+1} = (z{n} + x/z{n})/2，算出z2，再将z2代公式的右边算出z3，然后继续迭代计算，直到 z{n}和z{n+1}的差的绝对值小于某个值，即认为找到了精确的平方根。<br>
     * 牛顿迭代法一般不讨论其时间复杂度，迭代法的总的计算量由迭代次数决定，而迭代次数与阈值、初始值、收敛后的值有关。<br>
     * 根据牛顿法的原理可知，迭代的次数越多，近似值越逼近真实值，当然我们会通过设置精度来限制它的迭代次数。<br>
     * 开平方所需的迭代次数随被开方数的大小变化，当被开方数范围在10-1000之间时，迭代次数仅为6-9次。<br>
     * 时间复杂度：O(1) ~ O(logN)，时间复杂度与精度有关，一般情况可以认为是常数级别，又因为牛顿法每次迭代的误差至少会减少一半，最差情况复杂度也会小于O(logN)。 <br>
     * 空间复杂度：O(1)
     * @param x 求解正平方根的数
     * @return 正平方根的解
     */
    public static double sqrt(double x) {
        if (x < 0) {
            return Double.NaN;
        }
        double e = 1e-15; // 0.000000000000001
        double z = x;
        double y = (z + x / z) / 2;
        while (Math.abs(z - y) > e) {
            z = y;
            y = (z + x / z) / 2;
        }
        return z;
    }
}

package yyl.example.basic.classloader;

import yyl.example.basic.test.DateFormatTest;

public class PackageTest {

    public static void main(String[] args) {
        Package pkg1 = PackageTest.class.getPackage();
        Package pkg2 = Package.getPackage("yyl.example.basic.classloader");
        System.out.println(pkg1 == pkg2);

        // 获得已知包
        System.out.println(Package.getPackages().length);

        // 这时候又知道了一个新的包(DateFormatTest所在包)
        DateFormatTest.class.toString();
        System.out.println(Package.getPackages().length);

        // 这个包不存在 (返回Null)
        Package.getPackage("non_existent");
        System.out.println(Package.getPackages().length);

        // 打印所有已知包
        for (Package pkg : Package.getPackages()) {
            System.out.println(pkg);
        }

    }
}

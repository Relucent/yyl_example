package yyl.example.basic.reflect;

/**
 * 包信息
 */
public class PackageTest {
    public static void main(String[] args) {
        Package packageInfo = String.class.getPackage();
        System.out.println("Name:                    " + packageInfo.getName());
        // 返回此程序包提供的实现的程序包的规范(如果信息不明确则返回NULL)
        System.out.println("SpecificationTitle:      " + packageInfo.getSpecificationTitle());
        System.out.println("SpecificationVendor:     " + packageInfo.getSpecificationVendor());
        System.out.println("SpecificationVersion:    " + packageInfo.getSpecificationVersion());
        // 返回此程序包提供的执行的版本信息(如果信息不明确则返回NULL)
        System.out.println("ImplementationTitle:     " + packageInfo.getImplementationTitle());
        System.out.println("ImplementationVendor     " + packageInfo.getImplementationVendor());
        System.out.println("ImplementationVersion:   " + packageInfo.getImplementationVersion());
    }
}

package yyl.example.demo.zip4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * zip4j是一个支持ZIP加密、解密压缩的类库<br>
 * 官网地址： http://www.lingala.net/zip4j/ <br>
 */
public class Zip4JExample {

    private static String TEMP_DIR = "D:/temp/";
    private static Charset UTF_8 = Charset.forName("UTF-8");
    private static String PASSWORD = "password";


    public static void main(String[] args) throws ZipException, IOException {

        File file = new File(TEMP_DIR + System.currentTimeMillis() + ".zip");
        file.getParentFile().mkdirs();

        zip(file);
        upzip(file);
    }


    /** 压缩文件 */
    private static void zip(File file) throws ZipException, IOException {

        ZipFile zipFile = new ZipFile(file);

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);// 压缩级别
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_128);// 加密等级
        parameters.setEncryptFiles(true);// 加密
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);// 加密方法
        parameters.setPassword(PASSWORD);// 设置密码

        parameters.setSourceExternalStream(true);// 标记使用流的方式添加文件

        try (InputStream input = toInputStream("江南可采莲，莲叶何田田。鱼戏莲叶间。鱼戏莲叶东，鱼戏莲叶西，鱼戏莲叶南，鱼戏莲叶北。 ")) {
            parameters.setFileNameInZip("汉乐府/江南.txt");
            zipFile.addStream(input, parameters);
        }
        try (InputStream input = toInputStream("千山鸟飞绝，万径人踪灭。\n孤舟蓑笠翁，独钓寒江雪。 ")) {
            parameters.setFileNameInZip("江雪.txt");
            zipFile.addStream(input, parameters);
        }

        try (InputStream input = toInputStream("岱宗夫如何？齐鲁青未了。\n造化钟神秀，阴阳割昏晓。\n荡胸生曾云，决眦入归鸟。\n会当凌绝顶，一览众山小。")) {
            parameters.setFileNameInZip("杜甫/望岳.txt");
            zipFile.addStream(input, parameters);
        }

        try (InputStream input = toInputStream("天门中断楚江开，碧水东流至此回。\n两岸青山相对出，孤帆一片日边来。   ")) {
            parameters.setFileNameInZip("李白/望天门山.txt");
            zipFile.addStream(input, parameters);
        }
        try (InputStream input = toInputStream("日照香炉生紫烟，遥看瀑布挂前川。\n飞流直下三千尺，疑是银河落九天。  ")) {
            parameters.setFileNameInZip("李白/望庐山瀑布.txt");
            zipFile.addStream(input, parameters);
        }

        System.out.println("zipPath: " + file.getAbsolutePath());
    }


    /** 解压文件 */
    private static void upzip(File file) throws ZipException, IOException {
        ZipFile zipFile = new ZipFile(file);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(PASSWORD);
        }
        for (Object fh : zipFile.getFileHeaders()) {
            FileHeader header = (FileHeader) fh;
            System.out.println(header.getFileName());
            // header.isDirectory()
            // ZipInputStream zipInput = zipFile.getInputStream(header);
        }
        // zip文件名去掉后缀，作为解压的目录名
        String path = file.getAbsolutePath();
        String destPath = path.substring(0, path.lastIndexOf('.'));
        System.out.println("destPath: " + destPath);
        zipFile.extractAll(destPath);
    }

    private static InputStream toInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(UTF_8));
    }
}

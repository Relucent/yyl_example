package yyl.example.basic.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 用到了sun.misc，只支持旧版本JDK
 */
public class FileBase64 {

    public static void main(String[] args) {
        InputStream input = null;
        try {
            input = FileBase64.class.getResourceAsStream("sample.png");
            String result = getBase64(input);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {}
        }
    }

    @SuppressWarnings("restriction")
    public static String getBase64(InputStream input) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copyLarge(input, output, buffer);
        String result = new sun.misc.BASE64Encoder().encode(output.toByteArray());
        return result;
    }

    public static long copyLarge(InputStream input, OutputStream output, byte buffer[]) throws IOException {
        long count = 0L;
        for (int n = 0; -1 != (n = input.read(buffer));) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}

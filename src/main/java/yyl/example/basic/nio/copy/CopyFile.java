package yyl.example.basic.nio.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件拷贝
 */
public class CopyFile {
	public static void main(String[] args) throws Exception {
		String infile = "G:\\test.txt";
		String outfile = "G:\\copy" + System.currentTimeMillis() + ".txt";
		// 获取源文件和目标文件的输入输出流

		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(infile);
			fout = new FileOutputStream(outfile);
			// 获取输入输出通道
			FileChannel fcin = fin.getChannel();
			FileChannel fcout = fout.getChannel();
			// 创建缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				// clear方法重设缓冲区，使它可以接受读入的数据
				buffer.clear();
				// 从输入通道中将数据读到缓冲区
				int r = fcin.read(buffer);
				// read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
				if (r == -1) {
					break;
				}
				// flip方法让缓冲区可以将新读入的数据写入另一个通道
				buffer.flip();
				// 从输出通道中将数据写入缓冲区
				fcout.write(buffer);
			}
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
			}
			try {
				fout.close();
			} catch (IOException e) {
			}
		}
	}
}

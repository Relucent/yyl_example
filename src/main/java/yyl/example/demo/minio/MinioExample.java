package yyl.example.demo.minio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.MinioException;

public class MinioExample {

	public static void main(String[] args) throws Exception {
		try {
			MinioClient minioClient = new MinioClient("http://localhost:9000", "admin", "password");

			String bucketName = "test";
			String objectName = "hello.txt";
			byte[] content = "hello".getBytes();
			String contentType = "text/plain";

			boolean isExist = minioClient.bucketExists(bucketName);
			if (!isExist) {
				minioClient.makeBucket(bucketName);
			}

			try (InputStream input = new ByteArrayInputStream(content)) {
				minioClient.putObject(bucketName, objectName, input, contentType);
			}

			ObjectStat stat = minioClient.statObject(bucketName, objectName);
			System.out.println(stat);

			try (InputStream input = minioClient.getObject(bucketName, objectName)) {
				byte[] data = IOUtils.toByteArray(input);
				System.out.println(new String(data));
			}

			minioClient.removeObject(bucketName, objectName);
			minioClient.removeBucket(bucketName);

		} catch (MinioException e) {
			e.printStackTrace();
		}
	}
}

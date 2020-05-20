package yyl.example.demo.minio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;

public class MinioExample {

	public static void main(String[] args) throws Exception {
		try {
			MinioClient minioClient = new MinioClient("http://localhost:9000", "minioadmin", "minioadmin");

			String bucketName = "test";
			String objectName = "hello.txt";
			byte[] content = "hello".getBytes();
			String contentType = "text/plain";

			boolean isExist = minioClient.bucketExists(bucketName);
			if (!isExist) {
				minioClient.makeBucket(bucketName);
			}

			try (InputStream input = new ByteArrayInputStream(content)) {
				PutObjectOptions options = new PutObjectOptions(input.available(), -1);
				options.setContentType(contentType);
				minioClient.putObject(bucketName, objectName, input, options);
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

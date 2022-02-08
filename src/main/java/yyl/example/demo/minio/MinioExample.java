package yyl.example.demo.minio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.MinioException;

public class MinioExample {

    public static void main(String[] args) throws Exception {
        try {
            String endpoint = "http://localhost:9000";
            String accessKey = "minioadmin";
            String secretKey = "minioadmin";
            String bucketName = "test" + System.currentTimeMillis();
            MinioClient client = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            String objectName = "hello.txt";
            byte[] content = "hello".getBytes();
            String contentType = "text/plain";
            try (InputStream input = new ByteArrayInputStream(content)) {
                int length = content.length;
                Map<String, String> headers = new HashMap<>();
                client.putObject(PutObjectArgs.builder()//
                        .bucket(bucketName)//
                        .object(objectName)//
                        .stream(input, length, -1)//
                        .contentType(contentType)//
                        .headers(headers)//
                        .build());
            }
            StatObjectResponse stat = client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            System.out.println(stat);
            try (InputStream input = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build())) {
                byte[] data = IOUtils.toByteArray(input);
                System.out.println(new String(data));
            }
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());

            if (!exists) {
                client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException e) {
            e.printStackTrace();
        }
    }

}

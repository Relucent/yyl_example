package yyl.example.demo.s3;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3Example {

    public static void main(String[] args) throws Exception {

        String secretId = "<secretId>";
        String secretKey = "<secretKey>";
        String sessionToken = "";
        String s3Endpoint = "";
        String bucketName = "";
        String region = "";
        String path = "";

        // 创建连接
        AwsSessionCredentials creds = AwsSessionCredentials.create(secretId, secretKey, sessionToken);

        S3Configuration s3Config = S3Configuration.builder()//
                .pathStyleAccessEnabled(false) // 使用虚拟域名风格
                .build();

        try (S3Client client = S3Client.builder()//
                .serviceConfiguration(b -> b.checksumValidationEnabled(false))//
                .region(Region.of(region))//
                .credentialsProvider(StaticCredentialsProvider.create(creds))//
                .endpointOverride(new URI(s3Endpoint))//
                .serviceConfiguration(s3Config)//
                .build()) {

            ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()//
                    .bucket(bucketName)//
                    .prefix(path + "/")//
                    .build();

            ListObjectsV2Response listObjResponse = client.listObjectsV2(listObjectsReqManual);

            listObjResponse.contents()//
                    .stream()//
                    .map(S3Object::key)//
                    .filter(key -> key.endsWith(".csv"))//
                    .forEach(key -> System.out.println(key));
        }
    }

}
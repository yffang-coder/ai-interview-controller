package com.ruoyi.common.utils;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Component
public class MinioUtil {

    private static final Logger log = LoggerFactory.getLogger(MinioUtil.class);

    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    public MinioUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void init() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                setBucketPolicy(bucketName);
            }
        } catch (Exception e) {
            log.error("初始化MinIO失败：{}", e.getMessage());
        }
    }
    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file, String objectName) {
        try {
            // 如果桶不存在就创建
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                setBucketPolicy(bucketName);
            }

            // 上传文件
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }


            return String.format("%s/%s/%s", endpoint, bucketName, objectName);
        } catch (Exception e) {
            throw new RuntimeException("上传文件到 MinIO 失败: " + e.getMessage(), e);
        }
    }

    public void setBucketPolicy(String bucketName) {
        try {
            // 使用更兼容的 Principal 格式
            String policy = "{\n" +
                    "  \"Version\": \"2012-10-17\",\n" +
                    "  \"Statement\": [\n" +
                    "    {\n" +
                    "      \"Effect\": \"Allow\",\n" +
                    "      \"Principal\": {\"AWS\": \"*\"},\n" +
                    "      \"Action\": [\"s3:GetObject\"],\n" +
                    "      \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(policy)
                    .build());
            log.info("成功为存储桶 {} 设置公开读取策略", bucketName);

            // 验证策略是否应用
            String appliedPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
            log.info("当前存储桶策略: {}", appliedPolicy);
        } catch (Exception e) {
            log.error("设置存储桶 {} 策略失败: {}", bucketName, e.getMessage(), e);
            throw new RuntimeException("设置存储桶策略失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取文件的预签名URL（单位：天）
     */
    public String getPresignedUrl(String objectName, int expireDays) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .method(Method.GET)
                            .expiry(expireDays, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("获取预签名URL失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("删除文件失败: {}", objectName);
        }
    }


}

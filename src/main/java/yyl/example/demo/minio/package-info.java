package yyl.example.demo.minio;
/**
 * MinIO 是一个基于Apache License v2.0开源协议的对象存储服务。<br>
 * 它兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。<br>
 * MinIO是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。<br>
 * <br>
 * 官方网站：<a href="https://min.io">min.io</a><br>
 * 在线文档：<a href="https://docs.min.io">docs.min.io</a><br>
 * 
 * <pre>
 * # 使用 Docker部署
 * docker pull minio/minio
 * docker run -p 9000:9000 minio/minio server /data
 * </pre>
 */
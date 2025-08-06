# 使用官方Java基础镜像
FROM registry.access.redhat.com/ubi8/openjdk-17:latest

# 设置工作目录
WORKDIR /app

# 将构建的jar文件复制到容器中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用运行的端口（与application.properties中配置的端口一致）
EXPOSE 8088
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
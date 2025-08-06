# Spring Boot Demo Project

这是一个基于Spring Boot 3.2.0的示例项目。

## 项目结构

```
project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       └── controller/
│   │   │           └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java
├── pom.xml
└── README.md
```

## 技术栈

- Spring Boot 3.2.0
- Java 17
- Maven
- Spring Web
- Spring Data JPA
- H2 Database
- Spring Boot DevTools

## 运行项目

1. 确保已安装Java 17和Maven
2. 在项目根目录执行：
   ```bash
   mvn spring-boot:run
   ```
3. 访问 http://localhost:8080

## API端点

- `GET /` - 欢迎页面
- `GET /hello` - Hello World接口
- `GET /hello?name=YourName` - 带参数的Hello接口
- `GET /h2-console` - H2数据库控制台

## 开发

项目包含以下功能：
- RESTful API
- 内存数据库H2
- 自动配置
- 开发工具支持 
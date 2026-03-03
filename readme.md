# data-branch-management版本分支管理服务

## 简介

版本分支管理服务，为平台提供地图osm数据和编译数据的版本分支的发布绑定和删除解绑，以及查询等相关功能。

## 技术栈

- **编程语言**: Java
- **框架**: Spring Boot、Spring Cloud
- **构建工具**: Maven
- **数据库**: MongoDB
- **其他技术**: 

## 项目结构
- .
- |-- src
- |   |-- main
- |   |   |-- java
- |   |   |   `-- com
- |   |   |       `-- kuandeng
- |   |   |           `-- data                    (数据相关)
- |   |   |               |-- branch
- |   |   |               |-- config              (配置类相关)
- |   |   |               |-- utils               (工具类相关)
- |   |   |               `-- DataBranchManagementApplication.java   (主应用程序类)
- |   |   `-- resources
- |   |       |-- application.properties          (服务配置文件，包含数据库连接、服务器端口等配置)
- |   |       |-- bootstrap.yml                   (Spring Boot启动配置)
- |   |       `-- logback-spring.xml              (日志配置)
- |   `-- test                                    (测试相关)
- `-- pom.xml                                     (Maven项目对象模型文件，包含项目依赖、插件等配置)


## 构建和运行

### 先决条件

- 安装Java Development Kit (JDK) 版本8。
- 安装Apache Maven。
- 依赖task-frame-management任务框服务，需查询任务框获取adcode相关信息

### 依赖数据库

- MongoDB：创建Database:geometry_management；创建Collection：branches、branches_bindings、data_branch_bind、branches_bindings_requests、urls

### 构建项目

在项目根目录下运行以下命令来构建项目：

```bash
./mvnw clean package
```


### 运行项目

在项目根目录下运行以下命令来运行项目：

```bash
java -Dlog.path=/data/CI/data-branch-management/logs -Xms8g -Xmx8g -server -jar /data/CI/data-branch-management/lib/data-branch-management.jar --server.port=13130 --spring.cloud.config.profile=prod --spring.cloud.config.label=kd --eureka.client.serviceUrl.defaultZone=http://eureka-01:13900/eureka/
```
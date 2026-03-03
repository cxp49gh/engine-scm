# engine-scm (引擎配置管理)

## 项目简介

引擎配置管理服务，提供模板管理、参数定义、运行时配置、快照发布等功能。基于 Spring Cloud + MongoDB 的配置管理平台，支持模板草稿、快照、预发布校验（Dry Run）等核心功能。

## 技术栈

| 技术 | 版本/说明 |
|------|------------|
| Java | 1.8+ |
| Spring Boot | 2.7.18 |
| Spring Cloud | 2021.0.8 |
| Maven | 3.6+ |
| MongoDB | 4.0+ |

### 核心依赖

- `spring-boot-starter-web` - Web 框架 (REST API)
- `spring-boot-starter-data-mongodb` - MongoDB 数据持久化
- `spring-boot-starter-thymeleaf` - 模板引擎
- `spring-cloud-starter-netflix-eureka-client` - 服务注册 (可选)
- `spring-cloud-starter-config` - 配置中心 (可选)
- `spring-cloud-starter-openfeign` - 声明式服务调用
- `spring-cloud-starter-sleuth` - 分布式链路追踪
- `springdoc-openapi-ui` - OpenAPI 3.0 文档 (Swagger)
- `freemarker` - 模板渲染引擎
- `json-schema-validator` - JSON Schema 校验
- `zjsonpatch` - JSON Patch 操作
- `lombok` - 代码简化
- `fastjson` - JSON 处理

## 项目结构

```
engine-scm/
├── src/main/
│   ├── java/com/engine/scm/
│   │   ├── Application.java           # 启动类
│   │   ├── config/                    # 配置类
│   │   │   ├── BuildInfo.java
│   │   │   ├── ConfigurerAdapter.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── OpenApiConfig.java
│   │   │   ├── SwaggerConfig.java
│   │   │   └── TraceFilter.java
│   │   ├── template/                  # 模板管理核心模块
│   │   │   ├── controller/
│   │   │   │   ├── DryRunController.java        # 预发布校验
│   │   │   │   ├── TemplateDraftController.java # 草稿管理
│   │   │   │   ├── TemplatePublishController.java # 发布管理
│   │   │   │   └── TemplateSnapshotController.java # 快照管理
│   │   │   ├── service/              # 服务层
│   │   │   ├── repository/            # 数据访问层
│   │   │   └── model/                 # 数据模型
│   │   ├── other/                     # 通用模块
│   │   │   ├── ChecksumService.java
│   │   │   ├── DiffType.java
│   │   │   ├── DryRunMode.java
│   │   │   ├── DryRunException.java
│   │   │   ├── ParamDef.java
│   │   │   ├── ParamMergeService.java
│   │   │   └── ParamType.java
│   │   └── utils/                     # 工具类
│   │       ├── CustomError.java
│   │       └── MongoDbPageable.java
│   └── resources/
│       └── application.properties     # 配置文件
├── target/                            # 编译输出
│   └── engine-scm.jar                 # 可执行 JAR
├── pom.xml                           # Maven 配置
├── Dockerfile                        # Docker 构建文件
├── build_docker.sh                   # Docker 构建脚本
└── readme.md                         # 项目文档
```

## API 接口

### 1. 模板草稿管理 (`/api/templates/drafts`)

```bash
# 获取草稿列表
GET /api/templates/drafts

# 创建草稿
POST /api/templates/drafts

# 更新草稿
PUT /api/templates/drafts/{id}

# 删除草稿
DELETE /api/templates/drafts/{id}
```

### 2. 模板快照 (`/api/templates/snapshots`)

```bash
# 获取快照列表
GET /api/templates/snapshots

# 创建快照
POST /api/templates/snapshots

# 获取快照详情
GET /api/templates/snapshots/{id}

# 删除快照
DELETE /api/templates/snapshots/{id}
```

### 3. 模板发布 (`/api/templates/publish`)

```bash
# 发布模板
POST /api/templates/publish

# 查询发布状态
GET /api/templates/publish/{id}
```

### 4. 预发布校验 (`/api/template/dry-run`)

```bash
# 执行预发布校验
POST /api/template/dry-run

# 获取校验结果
GET /api/template/dry-run/{id}
```

## 数据模型 (MongoDB Collections)

| Collection | 说明 |
|------------|------|
| `template_draft` | 模板草稿 |
| `template_snapshot` | 模板快照 |
| `runtime_param_def` | 运行时参数定义 |

## 配置文件

### application.properties

```properties
# 服务配置
server.port=15900
server.servlet.context-path=/

# MongoDB 配置
spring.data.mongodb.uri=mongodb://172.28.0.81:27017/engine-scm

# Eureka 配置 (可选，不注册则设为 false)
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

# 配置中心 (可选)
spring.config.import=optional:configserver:

# Actuator 配置
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.security.enabled=false

# API 文档
springdoc.api-docs.enabled=true
swagger-ui.path=/swagger-ui.html
```

## 构建和运行

### 前置条件

- JDK 1.8+
- Maven 3.6+
- MongoDB 4.0+

### 构建项目

```bash
cd engine-scm
mvn clean package -DskipTests
```

### 运行项目

```bash
# 默认配置运行
java -Xms512m -Xmx1024m -jar target/engine-scm.jar

# 自定义配置运行
java -Xms512m -Xmx1024m -jar target/engine-scm.jar \
  --server.port=15900 \
  --spring.data.mongodb.uri=mongodb://localhost:27017/engine-scm
```

### Docker 运行

```bash
# 构建镜像
./build_docker.sh

# 运行容器
docker run -d -p 15900:15900 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host:27017/engine-scm \
  engine-scm:latest
```

## 健康检查

```bash
# 健康状态
curl http://localhost:15900/health

# 服务信息
curl http://localhost:15900/info
```

## API 文档

服务启动后访问：

- **Swagger UI**: http://localhost:15900/swagger-ui.html
- **OpenAPI Docs**: http://localhost:15900/v3/api-docs

## 核心功能说明

### 1. 模板草稿 (Template Draft)
模板的草稿版本管理，支持创建、编辑、删除、版本控制。

### 2. 模板快照 (Template Snapshot)
模板的快照版本，用于记录历史版本，支持回滚。

### 3. 预发布校验 (Dry Run)
在正式发布前执行参数校验，支持：
- 参数合并
- JSON Schema 校验
- 风险评估
- 差异分析

### 4. 模板发布 (Template Publish)
正式的模板发布流程，支持灰度发布和回滚。

## 常见问题

### 1. Config Server 连接失败
```
Could not locate PropertySource: Connection refused
```
**解决**: 配置文件中 `spring.config.import=optional:configserver:`，如无需配置中心可忽略。

### 2. Eureka 连接失败
```
Discovery Client initialized... with initial instances count: 0
```
**解决**: 设置 `eureka.client.register-with-eureka=false`

### 3. MongoDB 连接超时
```
MongoSocketException: Connection refused
```
**解决**: 检查 `spring.data.mongodb.uri` 配置和网络连通性。

## 开发指南

### 添加新接口
1. 在 `template/controller/` 创建 Controller
2. 使用 `@RestController` 和 `@RequestMapping` 注解
3. 注入 Service 层处理业务逻辑

### 添加新实体
1. 在 `template/` 下创建实体类，使用 `@Document` 注解
2. 创建对应的 Repository 接口继承 `MongoRepository`
3. 在 Service 层注入使用

## 性能优化

- JVM 参数: `-Xms512m -Xmx1024m` (根据服务器内存调整)
- MongoDB 连接池根据并发量调整
- 生产环境建议使用连接池和缓存

## 许可证

MIT License
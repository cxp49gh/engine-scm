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

### 实体关系图

```
┌─────────────────────┐         ┌─────────────────────┐
│   template_draft   │         │template_snapshot    │
│   (模板草稿)        │         │   (模板快照)         │
├─────────────────────┤         ├─────────────────────┤
│ id (PK)             │◄────────│ templateId (FK)     │
│ bizCode            │         │ version (PK)        │
│ engineCode         │         │ id (PK)             │
│ linkCode           │         │ templateContent     │
│ name               │         │ params             │
│ currentVersion     │         │ diffFromPrev        │
│ templateContent    │         │ riskLevel          │
│ params             │         │ publishedAt        │
│ status             │         └─────────────────────┘
│ createdAt          │
│ updatedAt          │
└─────────────────────┘
```

| Collection | 说明 |
|------------|------|
| `template_draft` | 模板草稿 |
| `template_snapshot` | 模板快照 |

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

**数据状态流转：**

```
DRAFT (草稿) ──[锁定]──▶ LOCKED (已锁定) ──[发布]──▶ 生成快照
    │                          │
    └──────────────────────────┘
              [解锁]
```

**核心字段：**
| 字段 | 说明 |
|------|------|
| `id` | 草稿唯一标识 (MongoDB ObjectId) |
| `bizCode` | 业务维度 (如: order, payment) |
| `engineCode` | 引擎维度 (如: order-engine) |
| `linkCode` | 环节 (如: create, update) |
| `name` | 模板名称 |
| `currentVersion` | 当前版本号 |
| `templateContent` | 模板内容 (Freemarker/JSON) |
| `params` | 运行时参数定义（含默认值） |
| `status` | 状态: DRAFT / LOCKED / DEPRECATED |

### 2. 模板快照 (Template Snapshot)

模板的快照版本，用于记录历史版本，支持回滚。

**与草稿的关联关系：**

```
┌─────────────────────────────────────────────────────────────┐
│  模板草稿 (TemplateDraft)                                    │
│  id: 69aabb1874a64604f116bb07                               │
│  name: 订单处理引擎配置                                       │
│  status: LOCKED                                             │
└─────────────────────────┬───────────────────────────────────┘
                          │ 发布 (发布时复制草稿内容)
                          ▼
┌─────────────────────────────────────────────────────────────┐
│  模板快照 (TemplateSnapshot)                                  │
│  templateId: 69aabb1874a64604f116bb07  ── 关联草稿ID          │
│  version: v1.0                                            │
│  version: v1.1                                            │
│  version: v2.0                                            │
└─────────────────────────────────────────────────────────────┘
```

**核心字段：**
| 字段 | 说明 |
|------|------|
| `id` | 快照唯一标识 |
| `templateId` | 关联的草稿 ID |
| `version` | 版本号 |
| `templateContent` | 快照内容 |
| `params` | 运行时参数定义（含默认值） |
| `diffFromPrev` | 与上一版本的差异 |
| `riskLevel` | 风险等级: LOW / MEDIUM / HIGH / NONE |
| `publishedAt` | 发布时间 |

### 3. 模板发布 (Template Publish)

正式的模板发布流程，将锁定的草稿发布为快照版本。

**发布流程：**

```
1. 创建/编辑草稿 (DRAFT)
       │
       ▼
2. 锁定草稿 (LOCKED) ─── 锁定后不可编辑，用于发布
       │
       ▼
3. 填写版本号，确认发布
       │
       ▼
4. 生成快照 (Snapshot) ─── 保存历史版本
       │
       ▼
5. 返回发布结果 (快照ID、风险等级)
```

### 4. 运行时参数定义 (Runtime Param Definition)

定义模板运行时需要的参数，支持参数校验、默认值、风险等级等功能。

**核心字段：**
| 字段 | 说明 |
|------|------|
| `id` | 参数定义 ID (如: order-engine-v1) |
| `engineCode` | 引擎维度 |
| `version` | 版本 |
| `params[]` | 参数列表 |

**参数属性：**
| 属性 | 说明 |
|------|------|
| `name` | 参数名 |
| `type` | 类型: STRING, INT, LONG, DOUBLE, BOOLEAN, STRING_LIST, OBJECT, ARRAY |
| `required` | 是否必填 |
| `overridable` | 是否可覆盖 |
| `defaultValue` | 默认值 |
| `min` / `max` | 数值范围 |
| `pattern` | 正则校验 |
| `riskLevel` | 风险等级: LOW / MEDIUM / HIGH |
| `description` | 描述 |

**运行时参数与默认参数的关系：**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  模板草稿 (TemplateDraft)                                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 运行时参数定义 (含默认值)                                              │   │
│  │ params = [                                                            │   │
│  │   {                                                                  │   │
│  │     "name": "maxRetryCount",                                         │   │
│  │     "type": "INT",                                                   │   │
│  │     "required": true,                                                │   │
│  │     "defaultValue": 5,           // 默认值                            │   │
│  │     "overridable": true          // 是否允许运行时覆盖               │   │
│  │   },                                                                 │   │
│  │   {                                                                  │   │
│  │     "name": "timeout",                                                │   │
│  │     "type": "INT",                                                   │   │
│  │     "required": false,                                               │   │
│  │     "defaultValue": 60000                                            │   │
│  │   }                                                                  │   │
│  │ ]                                                                    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
                  │ 参数合并 (ParamMergeService)
                  ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  最终运行时参数 (Merged Parameters)                                           │
│  {                                                                         │
│    "maxRetryCount": 5,       ← 来自 params.defaultValue (可覆盖)          │
│    "timeout": 60000,         ← 来自 params.defaultValue                    │
│    "enableAsync": true,      ← 来自 params.defaultValue (不可覆盖)         │
│    "regionCodes": ["CN"]     ← 来自 params.defaultValue                    │
│  }                                                                         │
└─────────────────────────────────────────────────────────────────────────────┘
```

**合并规则：**

| 场景 | 处理方式 |
|------|----------|
| params 有 defaultValue | 使用 defaultValue |
| 运行时传入参数 | 覆盖默认值（除非 overridable=false） |
| 参数设置 `overridable: false` | 只能使用 defaultValue，不可被运行时覆盖 |

**示例：**

```json
// 运行时参数定义 (order-engine-v1)
{
  "params": [
    {"name": "maxRetryCount", "defaultValue": "3", "overridable": true},
    {"name": "timeout", "defaultValue": "30000", "overridable": true},
    {"name": "enableAsync", "defaultValue": "true", "overridable": false}
  ]
}

// 草稿中的默认参数
{
  "maxRetryCount": 5,
  "timeout": 60000
}

// 最终合并后的参数
{
  "maxRetryCount": 5,      // 使用 defaultParams (被覆盖)
  "timeout": 60000,        // 使用 defaultParams (被覆盖)
  "enableAsync": true      // 使用运行时参数定义默认值 (不可覆盖)
}
```

### 5. 预发布校验 (Dry Run)

在正式发布前执行参数校验，支持：
- 参数合并 (草稿参数 + 运行时参数定义)
- JSON Schema 校验
- 风险评估
- 差异分析

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
<p align="center">
  <img src="https://github.com/user-attachments/assets/4200e3eb-b14e-49c7-a1ce-93f159411152" style="width: 200px; height: 200px; border-radius: 50%; object-fit: cover;" alt="圆形图片">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">ai-interview-controller v1.0.0</h1>
<h4 align="center">基于SpringBoot+Vue前后端分离的Java快速开发框架</h4>


## 项目简介
ai 面试官后端管理系统，基于若依快速开发平台进行业务扩展，包含系统管理、AI 面试业务、日志审计、任务调度、代码生成等能力。

## 技术栈
- Java 17 / Spring Boot 3.3.1 / Spring Security 6.3.1
- MyBatis / MyBatis-Plus / PageHelper
- MySQL / Druid
- Redis / JWT
- Swagger(Springfox 3.0, OpenAPI 3)
- WebSocket(STOMP)
- MinIO

## 模块结构
- ruoyi-admin：启动模块与 Web 层接口
- ruoyi-main：AI 面试业务域模型、服务与 Mapper
- ruoyi-framework：安全、拦截器、WebSocket、缓存、数据源等基础能力
- ruoyi-system：系统管理核心域（用户、角色、菜单、日志等）
- ruoyi-quartz：定时任务调度
- ruoyi-generator：代码生成器
- ruoyi-common：通用工具与基础组件
- sql：数据库初始化脚本

## 业务功能
### AI 面试业务
- 模型管理、分类与分类项、轮播与公告
- 面试记录、满意度调查
- 聊天与消息推送（HTTP + WebSocket）
- 告警与通知
- 微信登录与小程序相关能力

### 系统管理（若依基础能力）
1. 用户管理：系统用户配置与权限分配
2. 部门管理：组织架构与数据权限
3. 岗位管理：用户岗位与职责
4. 菜单管理：路由与权限按钮
5. 角色管理：角色权限与数据范围
6. 字典管理：通用数据维护
7. 参数管理：运行时动态配置
8. 通知公告：信息发布与维护
9. 操作日志/登录日志：安全审计
10. 在线用户：当前会话监控
11. 定时任务：任务调度与日志
12. 代码生成：CRUD 模块生成
13. 系统接口：Swagger 文档
14. 服务/缓存/连接池监控

## 运行环境
- JDK 17
- Maven 3.8+
- MySQL 8.x
- Redis 6.x+
- MinIO（如需文件存储能力）

## 快速启动
1. 初始化数据库  
   - 执行 [ry_20250522.sql](./sql/ry_20250522.sql)  
   - 可选执行初始化脚本：
     - [category_item_init.sql](./ruoyi-admin/src/main/resources/category_item_init.sql)
     - [chat_init.sql](./ruoyi-main/src/main/resources/mapper/ai/chat_init.sql)
2. 修改配置  
   - 数据库与连接池：[application-druid.yml](./ruoyi-admin/src/main/resources/application-druid.yml)  
   - Redis、MinIO、AI 与微信配置：[application.yml](./ruoyi-admin/src/main/resources/application.yml)  
3. 启动服务  
   - IDE 运行 [RuoYiApplication](./ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java)  
   - 或命令行：
     - `mvn -pl ruoyi-admin -am spring-boot:run`
     - `mvn clean package -DskipTests` 后执行 `java -jar ruoyi-admin/target/ruoyi-admin.jar`
4. 访问服务  
   - 服务地址：http://localhost:8080

## 接口文档与监控
- Swagger（默认开启）：http://localhost:8080/dev-api/swagger-ui/index.html
- Swagger 前缀配置：`swagger.pathMapping=/dev-api`
- Druid 监控：/druid（账号 ruoyi / 密码 123456，见数据源配置）

## WebSocket
- STOMP 端点：/ws（支持 SockJS）
- 告警消息：/app/alarm → /topic/alarm
- 聊天推送：/user/queue/chat

## 在线体验

演示地址：http://vue.ruoyi.vip  

## 演示图

<table>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/cd1f90be5f2684f4560c9519c0f2a232ee8.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/1cbcf0e6f257c7d3a063c0e3f2ff989e4b3.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8074972883b5ba0622e13246738ebba237a.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-9f88719cdfca9af2e58b352a20e23d43b12.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-39bf2584ec3a529b0d5a3b70d15c9b37646.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-936ec82d1f4872e1bc980927654b6007307.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-b2d62ceb95d2dd9b3fbe157bb70d26001e9.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d67451d308b7a79ad6819723396f7c3d77a.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/5e8c387724954459291aafd5eb52b456f53.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/644e78da53c2e92a95dfda4f76e6d117c4b.jpg"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8370a0d02977eebf6dbf854c8450293c937.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-49003ed83f60f633e7153609a53a2b644f7.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d4fe726319ece268d4746602c39cffc0621.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-c195234bbcd30be6927f037a6755e6ab69c.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/b6115bc8c31de52951982e509930b20684a.jpg"/></td>
    </tr>
</table>

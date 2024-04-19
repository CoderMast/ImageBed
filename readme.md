## 开始之前

对你有用的话，希望能给项目点个Star，非常感谢。

对于项目的任何问题，或者你在本地部署时遇到的无法解决的问题，都可以提交issues，又可能你遇到的问题别人已经提交了issues，那么你就直接可以得到解决。没有解决的我会在第一时间进行解决和答复

欢迎关注我的微信公众号，可以随时和我交流。

![](https://img2.imgtp.com/2024/04/03/2wunYds8.png)

## 项目介绍

本项目是基于 SpringBoot3 + Vue3 开发的一款前后端分离的图片管理系统，又称为图床系统，可以对图片进行管理。

项目主要使用到的技术有：Java + SpringBoot + Vue + MySQL + Nginx + Maven

项目主要分为：系统设置、用户管理、图片管理、存储库管理这四大主要模块。

## 项目部署

// TODO: 项目尚且在开发阶段，项目部署文档待更新...

### 前端部分

本项目的前端部分使用了 Vue3 ，故在启动之前需要先安装 Node.js，本人在项目的开发阶段一直使用的是 `v20.11.1` 这个版本，未发现任何问题，故推荐使用。

在启动之前，首先需要进入到 `ImageBed/imagebed-frontend/` 目录，可使用 `cd` 命令进入。

1. 安装依赖

```shell
npm install
```
2. 项目运行

```shell
npm run dev
```

### 后端部分

后端为本项目的核心部分「因为本人是Java后端，前端页面仅限于能用」，使用 SpringBoot 框架进行后端 API 接口的开发。

**项目环境**

- MacOS 14.4.1
- IDEA 2024.1
- JDK 17
- SpringBoot 3.2.4
- Maven 3.9.6
- MySQL 8.3.0

**项目运行**

1. 导入 imagebed.sql 文件

2. 修改 `imagebed-backend/src/main/resources/application.yaml` 配置文件中的数据库信息

```yaml
datasource:
  # 这里要注意如果你的MySQL版本低于8，需要修改 driver-class-name，具体如何修改，篇幅限制，请自行百度。
  driver-class-name: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost:3306/image_bed?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
  username: codermast # 修改为你的数据库账号
  password: 123456    # 修改为你的数据库密码
```

3. 导入 Maven 依赖，刷新 pom.xml 文件即可

4. 启动项目，即启动 `imagebed-backend/src/main/java/com/codermast/imagebedbackend/ImageBedBackendApplication.java` 类中的 `main` 方法即可。

## 项目资料

- API接口文档    // TODO:待更新
- XMind思维导图  // TODO:待更新
# 鑫吉点餐平台

## 项目介绍

一款基于Spring Boot 2.0、Spring Cache、MySQL、MyBatisPlus、Docker、Redis、Axios、Vue的前后端分离的外卖点餐平台

## 项目架构

SpringBoot+MySQL+Redis+Vue2+Axios

## 项目源码

| 平台   | 地址                                                         |
| ------ | ------------------------------------------------------------ |
| github | [HRYX02/xingie (github.com)](https://github.com/HRYX02/xingie) |
| 码云   | [赫然玉鑫/reggie (gitee.com)](https://gitee.com/impressively-yuxin/reggie) |

## 项目结构

项目采用单模块的开发方式，结构如下

- `common` 为项目的公共包，各种工具类，公共配置存在该模块

- `filter` 为项目的后端过滤器，对用户登录进行过滤拦截

- `config` 为项目的配置类，对Spring Boot、MP以及Redis的配置

- `controller` 为项目的前端控制器，所有的后端接口均在此包中

- `entity` 为项目数据层与应用层的数据交互类

- `dto` 为项目表现层与应用层的数据交互类

- `dao` 该包中主要为连接MySQL数据库的Mapper接口

- `service` 为项目的业务方法包，项目中所有的业务方法均在此类

- `utils` 为第三方工具包，目前主要有阿里云的短信服务实现类和随机验证码生成类

- `ReggieApplication` 为项目的启动类
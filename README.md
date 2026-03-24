# crm-auth-skill

这是一个用于登录 CRM OAuth 接口并获取 access_token 的本地 skill。

## 功能
- 调用 CRM token 接口
- 返回 access_token
- 支持返回原始响应
- 设计为可分发目录，使用时可自带 JRE，无需安装 Java 或 Maven

## 目录结构
- `run.bat`：启动入口
- `config/skill-config.json`：配置文件
- `app/crm-auth.jar`：打包后的程序
- `runtime/jre/`：内置 Java 运行时（发布时放入）

## 使用前准备
编辑以下文件：

`config/skill-config.json`

填写：
- `crmUsername`
- `crmPassword`

## 使用方式

### 获取 token
```bat
run.bat login
```

### 查看原始响应
```bat
run.bat raw
```

## 输出格式
成功示例：

```json
{"success":true,"action":"login","access_token":"xxxxx"}
```

失败示例：

```json
{"success":false,"action":"login","message":"错误信息"}
```

## 注意事项
- 需确保本机可以访问 CRM 服务地址
- 默认 token 地址为：`http://192.168.1.251:9999/auth/oauth/token`
- 用户名和密码只从配置文件读取
- 如需修改接口地址或认证参数，请编辑配置文件

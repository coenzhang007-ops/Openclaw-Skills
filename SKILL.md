---
name: crm-auth-skill
description: Login to a CRM OAuth endpoint, return an access token, optionally return the raw OAuth response, and optionally write the token into an MCP server application.yml so local MCP tools can authenticate to CRM.
metadata:
  openclaw:
    emoji: "🔐"
    requires:
      files:
        - config/skill-config.json
      bins:
        - cmd
    primaryFile: config/skill-config.json
---

# CRM Auth Skill

登录 CRM OAuth 接口，获取 `access_token`，并可选择把 token 写入本地 MCP Server 的 `application.yml`。

## What this skill does

- 登录 CRM OAuth token 接口
- 返回 `access_token`
- 返回原始响应内容
- 将 token 自动写入 MCP Server 的 `application.yml`

## Entry point

```bat
run.bat
```

## Supported actions

### login
登录并返回 access token：

```bat
run.bat login
```

默认不传参数时，也会执行 `login`：

```bat
run.bat
```

### raw
返回 CRM OAuth 原始响应：

```bat
run.bat raw
```

### write-mcp-token
登录并把 token 写入 MCP Server 的 `application.yml`：

```bat
run.bat write-mcp-token
```

## Configuration

先复制示例配置：

```bat
copy config\skill-config.example.json config\skill-config.json
```

然后编辑：

```json
{
  "tokenUrl": "http://192.168.1.251:9999/auth/oauth/token",
  "basicAuthUsername": "crm-admin",
  "basicAuthPassword": "crm-admin",
  "scope": "server",
  "grantType": "password",
  "crmUsername": "your-username",
  "crmPassword": "your-password",
  "mcpApplicationYmlPath": "D:/Project/mcp/src/main/resources/application.yml"
}
```

### Required fields

- `crmUsername`
- `crmPassword`
- `mcpApplicationYmlPath` （当你要执行 `write-mcp-token` 时必须正确）

### Optional / environment-specific fields

- `tokenUrl`
- `basicAuthUsername`
- `basicAuthPassword`
- `scope`
- `grantType`

## Output format

统一输出 JSON。

### Success examples

```json
{"success":true,"action":"login","access_token":"xxxxx"}
```

```json
{"success":true,"action":"write-mcp-token","message":"token 已写入 MCP application.yml","mcpApplicationYmlPath":"D:/Project/mcp/src/main/resources/application.yml"}
```

### Failure example

```json
{"success":false,"action":"write-mcp-token","message":"错误信息"}
```

## Suggested OpenClaw usage

当用户说这些话时，可以考虑使用这个 skill：

- “登录 CRM 获取 token”
- “刷新 CRM token”
- “把 CRM token 写到 MCP 配置里”
- “帮我更新 MCP server 的 CRM token”

## Notes

- 用户名和密码从 `config/skill-config.json` 读取
- 需确保本机可以访问 CRM 服务
- 默认依赖 skill 自带 `runtime/jre`，无需额外安装 Java
- 如果只想拿 token，不想改 MCP 配置，用 `login`
- 如果想让本地 MCP server 立即能查 CRM，用 `write-mcp-token`

# Skill: crm-auth

## Purpose
登录 CRM OAuth 接口，获取 access_token，并可写入 MCP Server 配置。

## Entry
`run.bat`

## Supported actions
- `login`：返回 access_token
- `raw`：返回原始登录响应
- `write-mcp-token`：登录并把 token 写入 MCP 的 `application.yml`

## Config file
`config/skill-config.json`

## Output
统一输出 JSON。

### 成功
```json
{"success":true,"action":"login","access_token":"xxxxx"}
```

```json
{"success":true,"action":"write-mcp-token","message":"token 已写入 MCP application.yml","mcpApplicationYmlPath":"D:/Project/mcp/src/main/resources/application.yml"}
```

### 失败
```json
{"success":false,"action":"write-mcp-token","message":"错误信息"}
```

## Notes
- 用户名和密码只从配置文件读取
- 配置文件中必须填写：
  - `crmUsername`
  - `crmPassword`
  - `mcpApplicationYmlPath`

# crm-auth-skill

这是一个可供 OpenClaw 使用的本地 skill，用于登录 CRM OAuth 接口并获取 `access_token`，也可以把 token 自动写入本地 MCP server 的 `application.yml`。

## 功能

- 调用 CRM token 接口
- 返回 access_token
- 支持返回原始响应
- 支持把 token 自动写入 MCP Server 的 `application.yml`
- 自带 `run.bat` 启动入口
- 可携带内置 JRE 运行

## 推荐目录结构

```text
crm-auth-skill/
├─ SKILL.md
├─ _meta.json
├─ README.md
├─ run.bat
├─ app/
│  └─ crm-auth.jar
├─ config/
│  ├─ skill-config.example.json
│  └─ skill-config.json
└─ runtime/
   └─ jre/
```

## 用法

### 获取 token

```bat
run.bat login
```

### 返回原始响应

```bat
run.bat raw
```

### 登录并写入 MCP 配置

```bat
run.bat write-mcp-token
```

## 配置

先复制：

```bat
copy config\skill-config.example.json config\skill-config.json
```

再填写：

- `crmUsername`
- `crmPassword`
- `mcpApplicationYmlPath`

## 导入到 OpenClaw

如果你是本地使用，通常把整个目录放进 OpenClaw 的 `skills/` 目录即可。

例如：

```text
C:\Users\EDY\.openclaw\workspace\skills\crm-auth-skill
```

然后 OpenClaw 就可以读取其中的 `SKILL.md` 作为技能说明。

如果你有自己的 OpenClaw skill 导入命令，也建议导入整个目录，而不是只导入 jar。

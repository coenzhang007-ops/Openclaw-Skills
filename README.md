# crm-auth-skill

这是一个可供 OpenClaw 使用的本地 skill，用于登录 CRM OAuth 接口并获取 `access_token`，然后由 OpenClaw 在调用 CRM 相关 MCP tool 时把 token 作为参数传入。

## 功能

- 调用 CRM token 接口
- 返回 access_token
- 支持返回原始响应
- 推荐由 OpenClaw 在调用 MCP tool 时把 token 作为参数传入
- 兼容旧流程：仍支持把 token 写入 MCP Server 的 `application.yml`
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

### 登录并写入 MCP 配置（旧流程兼容）

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
- `mcpApplicationYmlPath`（仅旧流程 `write-mcp-token` 需要）

## 导入到 OpenClaw

如果你是本地使用，通常把整个目录放进 OpenClaw 的 `skills/` 目录即可。

例如：

```text
C:\Users\EDY\.openclaw\workspace\skills\crm-auth-skill
```

然后 OpenClaw 就可以读取其中的 `SKILL.md` 作为技能说明。

如果你有自己的 OpenClaw skill 导入命令，也建议导入整个目录，而不是只导入 jar。

## 推荐的新链路

1. OpenClaw 先执行：

```bat
run.bat login
```

2. 从输出中拿到：

```json
{"success":true,"action":"login","access_token":"xxxxx"}
```

3. 再调用 CRM 相关 MCP tool，并把 token 当成参数传入，例如：

```json
{
  "company": "vadas",
  "accessToken": "xxxxx"
}
```

这样就不需要把 token 固化写进 MCP server 的 yml 配置里了。

另外，这个 skill 现在会优先查找当前工作目录下的 `config/skill-config.json`；如果没找到，则自动回退到 skill 自身目录下的 `config/skill-config.json`，因此从别的目录启动也能正常工作。

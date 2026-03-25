# Import into OpenClaw

## Recommended

将整个目录复制到 OpenClaw 的 skills 目录：

```text
C:\Users\EDY\.openclaw\workspace\skills\crm-auth-skill
```

## After import

1. 复制配置模板：

```bat
copy config\skill-config.example.json config\skill-config.json
```

2. 编辑 `config\skill-config.json`
3. 使用：

```bat
run.bat login
run.bat raw
run.bat write-mcp-token
```

## Why this format works

OpenClaw 主要读取：

- `SKILL.md`：技能说明
- `_meta.json`：技能元信息（便于分发/版本化）
- 目录内脚本与资源：实际执行内容

所以这个目录已经整理成可直接作为 OpenClaw skill 使用的结构。

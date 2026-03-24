# BUILD.md

## 1. 编译打包
在本目录执行：

```bat
mvn clean package
```

打包成功后会生成：

```text
target\crm-auth.jar
```

把它复制到：

```text
app\crm-auth.jar
```

## 2. 放入内置 JRE
把可分发的 JRE 目录复制到：

```text
runtime\jre\
```

要求最终存在：

```text
runtime\jre\bin\java.exe
```

## 3. 配置
编辑：

```text
config\skill-config.json
```

填写 `crmUsername` 和 `crmPassword`。

## 4. 运行

```bat
run.bat login
```

或：

```bat
run.bat raw
```

## 5. 发布
把整个 `crm-auth-skill` 目录打包成 zip 发给别人即可。

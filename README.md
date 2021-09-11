# tencent-androidx-ocr

1. 复制credentials-tmpl.gradle文件，文件名改为credentials.gradle文件，编辑credentials.gradle 文件，配置密钥

2. 上传第三方依赖组件，执行命令 gradlew publish

3. 上传组件， 执行命令 gradlew task uploadArchives

4. 查看第三方依赖：gradlew :library:dependencies
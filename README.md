# tencent-androidx-ocr

1. 复制credentials-tmpl.gradle文件，文件名改为credentials.gradle文件，编辑credentials.gradle 文件，配置密钥

2. 发布第三方依赖组件
   a. 需要把 build.gradle 文件注释删除掉；
      apply from: "./deploy-3rd.gradle"
   b. 要把 library/build.gradle 文件 代码注释掉；
      apply from: "../deploy.gradle"
   c. 执行命令 .\gradlew clean publish

3. 发布工程组件，执行命令 .\gradlew clean publish

4. 查看第三方依赖：gradlew :library:dependencies
## 初始化环境

1. 安装[node](https://nodejs.org/en/)，请自行安装，已安装的跳过
2. 安装reveal-md: `npm install -g reveal-md`
3. 安装[maven](h，已安装的跳过ttp://maven.apache.org/download.cgi)，请自行安装，已安装的跳过
4. 启动服务
    ```bash
    # 编译后端代码
    cd demo && mvn clean package -DskipTests && cd ..

    # 启动server服务
    java -jar demo/server/target/server-0.0.1-SNAPSHOT.jar &
    # 启动client服务
    java -jar demo/client/target/client-0.0.1-SNAPSHOT.jar &

    # 编译前端代码并启动前端服务
    cd extension-demo && npm install && yarn serve & 
    cd ..

    # 启动reveal-md
    reveal-md 1_extensibility_program.md -w &
    ```
    
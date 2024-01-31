# Data Collection System for Psychometric-type Experiments

Users can open a link that will display a session of a custom browser capable of recording their browsing activities (clicks, scroll, etc.).

## Dependencies

- Java Development Kit (JDK) 8 or higher  
- Apache Maven  
- Apache Tomcat
- Docker
- MySQL

## Explanation

A JavaWeb server is used to deploy the entire website system, referred to as Machine A below;  
A remote server acts as the Guacamole Server, referred to as Machine B below;  
A remotely connected machine serves as the remote desktop accessed by volunteers, referred to as Machine C below.

This website system is implemented using javaweb+docker+tomcat+guacamole+playwright to achieve the above functions.

### Workflow Principles

1. When a volunteer enters the website and logs in, they can choose to start the test;
2. After clicking the start button, the Java system first stores a record in the local SQL, filling in the unique ID and user information, etc.;
3. Then the Java system includes this information in a post request sent to the remote Machine C server;
4. When Machine C receives this request (create vnc), it immediately presets the xsession file (playwright codegen command) so that the vnc connection can directly execute this command, pop up the browser specified webpage, and record;
5. Then the Machine C service will execute the "vncserver" command, generate a vnc server locally on Machine C, and respond to Machine A with the generated server number x (i.e., listening port 5090+x);
6. Upon receiving the response, the Java system in Machine A instantiates submergence12138/guacamole_client5.0:latest in the local docker, generating a container;
7. The container will automatically start tomcat and listen on port 8080 (mapped to a specified port on Machine A, starting from 8082), and connect to the specified port of the remote Machine C (i.e., the specified vnc server);
8. The tomcat in this container contains the pre-configured javaweb war package, which can be remotely connected to the specified vnc server of Machine C via the guacamole server of Machine B (port 4822);
9. Upon successful completion of the above steps, Machine A will update all the above information to the record created in mysql at the beginning.
10. At this point, the initialization of the test environment is completed. Machine A will delay 5 seconds to open a new page in the front end to connect to the remote machine (http://{A_hostname}:{port}/tunnell-1/);
11. At this time, the volunteer can perform access operations on this webpage, and playwright will record the user's actions in /opt/actions/;
12. After the volunteer is done, they can directly close the webpage. Click the finish button on the webpage just now to end the test;
13. After clicking the finish button, Machine A will obtain the current record ID and then query the docker container and connection information of the vnc server corresponding to the current ID in the database for ending the service and callback record file;
14. After querying, Machine A will close the corresponding container to release the port and end the service;
15. Then the service of Machine A will send the ID and vnc server number to the service of Machine C;
16. When Machine C receives the request (stop vnc), it will close the service of the corresponding vnc server and respond to Machine A with the string format of the corresponding action record file read from /opt/actions/;
17. When Machine A receives it, it will modify the status of the current record in the database and store the content of the response action record file.

### Environment Deployment on Machine A

Local environment: MacOS 14.2.1 (also compatible with Ubuntu22 and CentOS7.6+ systems, but note that ports 8080 and above need to be opened)  
Deploy the entire javaweb on Machine A (using tomcat 8.5 and above). I currently default to listening on port 8081, with the context path as /data/. Volunteers can access the entry point of the website system via http://{A_hostname}:8081/data/.

1. In docker, you need to pull the specified image from Docker Hub: submergence12138/guacamole_client5.0:latest;
2. Install mysql-server, by default, you need to create the corresponding database and table. You can also configure it yourself. The required content is in src/main/java/com/DataCollection/Utils/db.sql;
3. After configuration, you also need to connect to the corresponding mysql database using JDBC. In order for javaweb to connect normally, you need to configure the relevant content in src/main/resources/Jdbc.properties;
4. In src/main/resources/config.properties, you also need to configure the IP, port, and URL of the three machines mentioned above and the required test website.

### Environment Deployment on Machine B

The deployment of Machine B is relatively simple, only providing the function of the Guacamole Server. It can also be deployed directly on Machine A locally. It is recommended to deploy both server (8080) and gucad (4822).  
The specific method can refer to the Guacamole official website. Docker deployment is recommended, which is very simple.  
Reference link: https://guacamole.apache.org/doc/gug/guacamole-docker.html  

### Environment Deployment on Machine C

1. Since Machine C will be remotely connected, a graphical interface needs to be installed;
2. Install vnc server;
3. Install Python3;
4. Because Machine C needs to accept messages from Machine A and send files, Machine C also needs to have a simple server. You can paste /DataCollection/daemon.py into a file on Machine C and execute it using Python (port 8000);
5. Install Nodejs and Playwright.

## Reference Links:
https://guacamole.apache.org/doc/gug/introduction.html  
https://testingbot.com/support/playwright/recorder.html#introduction


## 中文
## 解释

一台JavaWeb服务器用于部署整个网站系统，下文中带成为机器A；  
一台远程服务器作为Guacamole Server，下文中代称为机器B；  
一台远程连接的机器作为被志愿者访问的远程桌面，下文中代称为机器C；  

这个网站系统是通过javaweb+docker+tomcat+guacamole+playwright实现了以上的功能。

### 流程原理
1. 当志愿者进入网站并登录后，可以选择开始测试；
2. 点击开始按钮后，首先java系统会在本地sql存储一条记录，填入唯一id和用户信息等；
3. 然后java系统会把这些信息包含在一个post请求中发送给远程机器C的服务器；
4. 机器C收到这个请求（create vnc）后会立刻预设xsession文件（playwright codegen命令），以便vnc连接后可以直接执行这个指令，在客户端弹出浏览器指定网页并记录；
5. 然后机器C的服务会执行”vncserver“命令，在C本地生成一个vnc server，并将生成的server的序号x（即监听端口5090+x）响应给机器A；
6. 收到响应后Java系统会在本地的docker中实例化submergence12138/guacamole_client5.0:latest，生成一个容器；
7. 容器会自动开启tomcat并监听8080端口（映射到机器A本地的指定端口，从8082开始递增）并且连接到远程机器C的指定端口（也就是指定的vnc server）；
8. 这个容器中的tomcat包含了已经配置好的javaweb的war包，可以通过机器B的guacamole server（4822端口）远程连接到机器C的指定vnc server；
9. 以上步骤成功后机器A会把以上所有信息更新到mysql中一开始创建的记录里。
10. 至此，测试环境的初始化已经完成，机器A会延迟5秒在前端打开一个新页面用于连接远程机器（http://{A_hostname}:{port}/tunnell-1/）;
11. 此时志愿者可以在这个网页进行访问操作，playwright会把用户操作记录在、/opt/actions/目录下；
12. 志愿者完成后可以直接关闭网页，在刚才的网页上点击finish按钮以结束测试；
13. 在点击finish按钮后，机器A会获取当前记录的id然后查询数据库中当前id的docker容器和连接的vnc server的信息用于结束服务和回调记录文件；
14. 查询后机器A会关闭对应的容器以释放端口和结束服务；
15. 然后机器A的服务把id和vnc server序号发送给机器C的服务；
16. 机器C接收到请求后（stop vnc）会关闭对应vnc server的服务并把/opt/actions/目录下对应的操作记录文件读取成String格式响应给机器A；
17. 机器A收到后，将会修改数据库中本条记录的status，并把响应的记录文件内容存入。


### 机器A的环境部署
本机环境MacOS 14.2.1（此外在Ubuntu22和CentOS7.6+系统也兼容，但注意要打开8080及之后的端口）  
在机器A部署整个javaweb（使用tomcat8.5及以上版本即可），我目前默认监听8081端口，上下文路径为/data/，即志愿者可以通过http://{A_hostname}:8081/data/访问网站系统的入口。  

1. 在docker中您需要到docker hub拉取指定的镜像：submergence12138/guacamole_client5.0:latest；
2. 安装mysql-server，默认需要创建对应的数据库和表，您也可以自行配置，所需的内容在src/main/java/com/DataCollection/Utils/db.sql；
3. 配置完成后您还需要使用JDBC连接对应的mysql数据库，为了javaweb可以正常连接，你需要在src/main/resources/Jdbc.properties中配置相关内容；
4. 在src/main/resources/config.properties中你还需要配置上述提到的三个机器的ip和端口以及需要的测试网站的URL。

### 机器B的环境部署
机器B的部署较简单，仅仅提供Guacamole Server的功能，也可以直接在机器A本地部署这个服务，建议将server（8080）和gucad（4822）都部署。  
具体方式可以参考Guacamole官网，推荐使用docker部署，十分简洁。  
参考链接：https://guacamole.apache.org/doc/gug/guacamole-docker.html  

### 机器C的环境部署
1. 由于机器C会被远程连接，所以需要安装图形化界面；
2. 安装vnc server；
3. 安装Python3；
4. 因为机器C需要接受来自机器A的消息并发送文件，所以机器C上也需要拥有一个简单的服务器，你可以把/DataCollection/daemon.py粘贴在机器C的某个文件中并使用Python执行它（8000端口）；
5. 安装Nodejs和Playwright。


## 参考链接：
https://guacamole.apache.org/doc/gug/introduction.html  
https://testingbot.com/support/playwright/recorder.html#introduction









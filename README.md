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

![流程图-202401312304](https://github.com/Submergence/DataCollection/assets/33412372/952d6e81-3ab0-4c3a-a0da-27921fc7f7cc)

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








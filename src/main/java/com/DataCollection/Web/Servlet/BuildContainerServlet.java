package com.DataCollection.Web.Servlet;

import com.DataCollection.Pojo.Record;
import com.DataCollection.Service.Impl.BuildContainersImpl;
import com.DataCollection.Utils.HostConfigReader;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

public class BuildContainerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BuildContainerServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // load configs
            HostConfigReader hostConfigReader = new HostConfigReader();
            int hostPort = hostConfigReader.findFirstAvailablePort();
            Integer userId = (Integer)request.getSession().getAttribute("user_id");
            if (userId == null) return;
            // Object Container
            Record record = new Record(hostConfigReader.getDockerImageName(),
                    hostConfigReader.getDockerContainerName()+ "_" + System.currentTimeMillis(),
                    hostPort, hostConfigReader.getDockerContainerPort(),System.currentTimeMillis(),
                    hostConfigReader.getTestUrl(), userId);
            // operations
            BuildContainersImpl buildContainers = new BuildContainersImpl();

            //insert
            int recordId = buildContainers.initRecords(record);
            record.setId(recordId);
            request.getSession().setAttribute("record_id", recordId);
            logger.info("Got record id: " + recordId);
            //request to remote vm
            int vncServer = buildContainers.startVncServer(String.valueOf(recordId),record.getTestUrl());
            if(vncServer == 0) logger.severe("connected to remote vm wrong");
            int vncServerPort = buildContainers.computeVncPort(vncServer);
            record.setVnc_port(vncServerPort);
            logger.info("Got vnc server port from remote vm: " + vncServerPort);
            //start container
            String containerId = buildContainers.instantiateDockerContainer(record, vncServerPort);
            logger.info("Built docker container: " + containerId);
            Thread.sleep(5000);
            if(containerId != null){
                record.setContainerId(containerId);
                buildContainers.updateContainerInfo(record);
                buildContainers.updateStatus(String.valueOf(record.getId()), 1);
                logger.info("Record building finished: " + record);
            }

//        Utils utils = new Utils();
//        String targetUri = "http://" + utils.getPublicIpAddress() + ":" + hostPort + "/tunnell-1";
            String targetUri = "http://" + "localhost" + ":" + hostPort + "/tunnell-1";
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(targetUri);
        }catch(Exception e){
            e.printStackTrace();

//            request.getSession().setAttribute("error_msg", "Error occurred while building the container: " + e.getMessage());
            String errorMessage = "Error occurred while building the container: " + e.getMessage();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"err_log\": \"" + errorMessage + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

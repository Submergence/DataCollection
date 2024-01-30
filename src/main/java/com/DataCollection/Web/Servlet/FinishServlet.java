package com.DataCollection.Web.Servlet;

import com.DataCollection.Dao.Impl.GetDataUtil;
import com.DataCollection.Service.Impl.BuildContainersImpl;
import com.DataCollection.Service.Impl.RemoteServiceImpl;
import com.DataCollection.Service.RemoteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class FinishServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BuildContainerServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BuildContainersImpl buildContainers = new BuildContainersImpl();
        RemoteService remoteService = new RemoteServiceImpl();
        int recordId = (Integer)request.getSession().getAttribute("record_id");
        String containerId = null;

        try {
            request.getSession().setAttribute("log_msg", "closing docker web server...");

            String[] ids = buildContainers.queryContainersIdByRecordId(recordId, 1);
            if (ids == null || Objects.equals(ids[0], "")) {
                throw new Exception("Something wrong, please try again.");
            }
            containerId = ids[0];

            int exitCode = buildContainers.shutdownContainerByProcess(containerId);
            System.out.println("exitCode: " + exitCode);
            if (exitCode != 0) {
                throw new Exception("Failed to stop container, exit code: " + exitCode);
            }
            buildContainers.updateStatus(String.valueOf(recordId), 2);

            request.getSession().setAttribute("log_msg", "closing remote vnc server...");
            logger.info("Sending record id: " + recordId);
            String record = remoteService.stopVncRequest(buildContainers.queryVncIdByRecordId(recordId), String.valueOf(recordId));
            int influenceRowUpdateRecord = buildContainers.updateRecordById(recordId, record);
            if (influenceRowUpdateRecord < 1) {
                throw new Exception("Something wrong when saving the record.");
            }
            request.getSession().setAttribute("fin_msg", "Thanks a lot!!");
        } catch (Exception e) {
            e.printStackTrace();

            // Attempt to close any opened resources
            if (containerId != null) {
                // Try to close resources related to the container
                try {
                    buildContainers.shutdownContainerByProcess(containerId);
                } catch (Exception resourceException) {
                    // Log resource closure failure
                    resourceException.printStackTrace();
                }
            }

            // Set error message and send error response
            String errorMessage = "Error occurred: " + e.getMessage();
            request.getSession().setAttribute("error_msg", errorMessage);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"err_log\": \"" + errorMessage + "\"}");
        }
    }
}

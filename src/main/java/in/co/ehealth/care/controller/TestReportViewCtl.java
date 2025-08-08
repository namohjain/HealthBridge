package in.co.ehealth.care.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.ehealth.care.bean.TestReportBean;
import in.co.ehealth.care.model.TestReportModel;

@WebServlet("/ctl/viewReport")
public class TestReportViewCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        TestReportModel model = new TestReportModel();

        try {
            TestReportBean bean = model.findByPK(id);

            if (bean != null && bean.getReportFile() != null) {
                response.setContentType(bean.getFileType());
                response.setHeader("Content-Disposition", "inline; filename=\"" + bean.getFileName() + "\"");
                response.getOutputStream().write(bean.getReportFile());
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("Report not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error loading report.");
        }
    }
}
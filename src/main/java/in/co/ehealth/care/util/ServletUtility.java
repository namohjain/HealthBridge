
package in.co.ehealth.care.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.controller.BaseCtl;
import in.co.ehealth.care.controller.EHCView;

public class ServletUtility {

    public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(page);
    }

    public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("exception", e);
        ServletUtility.forward(EHCView.ERROR_CTL, request, response);
        e.printStackTrace();
    }

    public static String getErrorMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return val == null ? "" : val;
    }

    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return val == null ? "" : val;
    }

    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        return val == null ? "" : val;
    }

    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        return val == null ? "" : val;
    }

    public static void setBean(BaseBean bean, HttpServletRequest request) {
        request.setAttribute("bean", bean);
    }

    public static BaseBean getBean(HttpServletRequest request) {
        return (BaseBean) request.getAttribute("bean");
    }

    public static String getParameter(String property, HttpServletRequest request) {
        String val = (String) request.getParameter(property);
        return val == null ? "" : val;
    }

    public static void setSize(int size, HttpServletRequest request) {
        request.setAttribute("size", size);
    }

    public static int getSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("size");
    }

    @SuppressWarnings("rawtypes")
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    @SuppressWarnings("rawtypes")
    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    public static int getPageNo(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageNo");
    }

    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    public static int getPageSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageSize");
    }

    public static void setOpration(String msg, HttpServletRequest request) {
        request.setAttribute("Opration", msg);
    }

    public static String getOpration(HttpServletRequest request) {
        String val = (String) request.getAttribute("Opration");
        return val == null ? "" : val;
    }

    // ✅ Updated subFile method: stores file in /webapp/file/
    public static String subFile(HttpServletRequest request, HttpServletResponse response, String name)
            throws ServletException, IOException {

        String savePath = request.getServletContext().getRealPath("/file");

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Part part = request.getPart("file");
        String fileName = extractFileName(part);
        String finalFileName = name + DataUtility.removeSpace(fileName);

        part.write(savePath + File.separator + finalFileName);

        return finalFileName;
    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

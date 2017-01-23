package yyl.example.exercise.jasper;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

public class JspRuntimeLibrary {

	public static Throwable getThrowable(ServletRequest request) {
		Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (error == null) {
			error = (Throwable) request.getAttribute("javax.servlet.jsp.jspException");
			if (error != null) {
				request.setAttribute("javax.servlet.error.exception", error);
			}
		}
		return error;
	}

	public static String getContextRelativePath(ServletRequest request, String relativePath) {
		if (relativePath.startsWith("/"))
			return relativePath;
		if (!(request instanceof HttpServletRequest))
			return relativePath;
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
		if (uri != null) {
			String pathInfo = (String) request.getAttribute("javax.servlet.include.path_info");
			if ((pathInfo == null) && (uri.lastIndexOf('/') >= 0))
				uri = uri.substring(0, uri.lastIndexOf('/'));
		} else {
			uri = hrequest.getServletPath();
			if (uri.lastIndexOf('/') >= 0)
				uri = uri.substring(0, uri.lastIndexOf('/'));
		}
		return new StringBuilder().append(uri).append('/').append(relativePath).toString();
	}

	/**
	 * <pre>
	 * 	<jsp:include page="page.jsp" />
	 * 	JspRuntimeLibrary.include(request, response, "page.jsp", out, false);
	 * </pre>
	 */
	public static void include(ServletRequest request, ServletResponse response, String relativePath, JspWriter out, boolean flush)
			throws IOException, ServletException {
		if ((flush) && (!(out instanceof BodyContent))) {
			out.flush();
		}
		String resourcePath = getContextRelativePath(request, relativePath);
		RequestDispatcher rd = request.getRequestDispatcher(resourcePath);
		rd.include(request, new ServletResponseWrapperInclude(response, out));
	}
}

package yyl.example.exercise.jasper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspWriter;

public class ServletResponseWrapperInclude extends HttpServletResponseWrapper {
	private PrintWriter printWriter;
	private JspWriter jspWriter;

	public ServletResponseWrapperInclude(ServletResponse response, JspWriter jspWriter) {
		super((HttpServletResponse) response);
		this.printWriter = new PrintWriter(jspWriter);
		this.jspWriter = jspWriter;
	}

	public PrintWriter getWriter() throws IOException {
		return this.printWriter;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		throw new IllegalStateException();
	}

	public void resetBuffer() {
		try {
			this.jspWriter.clearBuffer();
		} catch (IOException ioe) {
		}
	}
}
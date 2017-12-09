package com.hp.ws.logging;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:55
 */
public class ResponseLoggingWrapper extends HttpServletResponseWrapper {

	private ServletOutputStream outputStream;
	private PrintWriter writer;
	private ServletOutputStreamCopier copier;
	private int httpStatus = SC_USE_PROXY;
	private String httpErrorMsg;

	public ResponseLoggingWrapper(HttpServletResponse response) throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called on this response.");
		}

		if (outputStream == null) {
			outputStream = getResponse().getOutputStream();
			copier = new ServletOutputStreamCopier(outputStream);
		}

		return copier;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (outputStream != null) {
			throw new IllegalStateException("getOutputStream() has already been called on this response.");
		}

		if (writer == null) {
			copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
			writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
		}

		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (writer != null) {
			writer.flush();
		} else if (outputStream != null) {
			outputStream.flush();
			copier.flush();
		}
	}

	public byte[] getCopy() {
		if (copier != null) {
			return copier.getCopy();
		} else {
			return new byte[0];
		}
	}

	public String getCopyAsString() {
		if (getCopy() != null) {
			return new String(getCopy());
		}
		return null;
	}

	@Override
	public void setStatus(int sc) {
		httpStatus = sc;
		super.setStatus(sc);
	}

	@Override
	public void setStatus(int sc, String sm) {
		httpStatus = sc;
		httpErrorMsg = sm;
		super.setStatus(sc, sm);
	}

	@Override
	public void sendError(int sc) throws IOException {
		httpStatus = sc;
		super.sendError(sc);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		httpStatus = sc;
		httpErrorMsg = msg;
		super.sendError(sc, msg);
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getHttpErrorMsg() {
		return httpErrorMsg;
	}

}
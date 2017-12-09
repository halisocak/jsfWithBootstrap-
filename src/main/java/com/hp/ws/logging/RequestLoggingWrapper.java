package com.hp.ws.logging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:49
 */
public class RequestLoggingWrapper extends HttpServletRequestWrapper {

	ByteArrayInputStream bais;

	ByteArrayOutputStream baos;

	BufferedServletInputStream bsis;

	byte[] copy;

	public RequestLoggingWrapper(HttpServletRequest req) throws IOException {
		super(req);
		InputStream is = req.getInputStream();
		baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int letti;
		while ((letti = is.read(buf)) > 0) {
			baos.write(buf, 0, letti);
		}
		copy = baos.toByteArray();
	}

	@Override
	public ServletInputStream getInputStream() {
		try {
			bais = new ByteArrayInputStream(copy);
			bsis = new BufferedServletInputStream(bais);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bsis;
	}

	public byte[] getCopy() {
		return copy;
	}

	public String getCopyAsString() {
		if (getCopy() != null) {
			return new String(getCopy());
		}
		return null;
	}

	private class BufferedServletInputStream extends ServletInputStream {

		ByteArrayInputStream bais;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.bais = bais;
		}

		@Override
		public int available() {
			return bais.available();
		}

		@Override
		public int read() {
			return bais.read();
		}

		@Override
		public int read(byte[] buf, int off, int len) {
			return bais.read(buf, off, len);
		}

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			// TODO Auto-generated method stub

		}

	}

}
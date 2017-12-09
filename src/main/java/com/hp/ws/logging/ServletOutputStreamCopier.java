package com.hp.ws.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:59
 */
public class ServletOutputStreamCopier extends ServletOutputStream {

	private final OutputStream outputStream;
	private final ByteArrayOutputStream copy;

	public ServletOutputStreamCopier(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.copy = new ByteArrayOutputStream(1024);
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
		copy.write(b);
	}

	public byte[] getCopy() {
		return copy.toByteArray();
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		// TODO Auto-generated method stub

	}

}
package com.test.TestSSL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Hello world!
 *
 * https://stackoverflow.com/questions/27573192/how-do-i-set-ssl-protocol-version-in-java-and-how-do-i-know-which-one-javax-ne
 * 
 * https://stackoverflow.com/questions/9501237/read-stream-twice
 * 
 * https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		try {
			CloseableHttpResponse execute = HttpClientBuilder.create().build()
					.execute(new HttpGet("https://hubic.com"));

			System.out.println(execute.getProtocolVersion());

			HttpEntity entity = execute.getEntity();

			InputStream inputStream = entity.getContent();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			org.apache.commons.io.IOUtils.copy(inputStream, baos);
			byte[] bytes = baos.toByteArray();

			readV1(new ByteArrayInputStream(bytes));

			System.out.println("Will read again");
			
			
			readV2(new ByteArrayInputStream(bytes));

			inputStream.close();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Bye World!");

	}

	private static void readV1(InputStream inputStream) {
		System.out.println("read V1");

		try {
		int ch;
		StringBuilder sb = new StringBuilder();
			while ((ch = inputStream.read()) != -1)
				sb.append((char) ch);

		System.out.println(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void readV2(InputStream inputStream) {
		try {

			final int bufferSize = 1024;
			final char[] buffer = new char[bufferSize];
			final StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(inputStream, "UTF-8");
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0)
					break;
				out.append(buffer, 0, rsz);
			}

//			inputStream.reset();
			System.out.println("read V2");
			System.out.println(out.toString());

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}

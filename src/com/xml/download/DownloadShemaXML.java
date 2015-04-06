package com.xml.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadShemaXML
 */
public class DownloadShemaXML extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadShemaXML() {
		super();
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fileName = request.getPathInfo();
		if (fileName.endsWith(".xsd")) {

			String path = System.getProperty("java.io.tmpdir");
			File pdfFile = new File(path + fileName);
			if (pdfFile.exists() == true) {
				OutputStream responseOutputStream = null;
				FileInputStream fileInputStream = null;
				try {
					response.setContentType("application/xsd");
					response.addHeader("Content-Disposition",
							"attachment; filename=");
					response.setContentLength((int) pdfFile.length());
					fileInputStream = new FileInputStream(pdfFile);
					responseOutputStream = response.getOutputStream();
					int bytes;
					while ((bytes = fileInputStream.read()) != -1) {
						responseOutputStream.write(bytes);
					}
				} catch (FileNotFoundException e) {
					response.setContentType("text/html");
					response.getWriter().print(e.getStackTrace());

				} catch (IOException e) {
					response.setContentType("text/html");
					response.getWriter().print(e.getStackTrace());
				} finally {
					try {
						fileInputStream.close();
						responseOutputStream.close();
					} catch (Exception e) {
						response.setContentType("text/html");
						response.getWriter().print(e.getStackTrace());
					}

				}
			}// ifExist
			else {
				response.setContentType("text/html");
				response.getWriter().print(" File Did not exist");
			}

		}// if
		else {
			response.setContentType("text/html");
			response.getWriter().print("Its not e File name.XSD");
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	
	}

}

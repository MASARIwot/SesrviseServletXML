package com.xml.creator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xml.businesslogic.interfaces.ICreateXML;

/**
 * Servlet implementation class XmlCreator
 */
public class XmlCreator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public XmlCreator() {
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processRequest(HttpServletRequest request,	HttpServletResponse response) throws IOException,ServletException {
		ICreateXML.CreateXML createXMl = new ICreateXML.CreateXML();
		String fileShema = null;
		String path = System.getProperty("java.io.tmpdir");
		long before = System.currentTimeMillis();
		String urlForParsing = (request.getParameter("InputURL"));

		try {
			fileShema = createXMl.create(urlForParsing,(request.getServerName() + ":"
									+ request.getServerPort() + "/SesrviseServletXML/Download/"),path);
		} catch (UnsupportedEncodingException e1) {
			response.setContentType("text/html");
			response.getWriter().print(e1.getStackTrace());
			
		} catch (IOException e1) {
			response.setContentType("text/html");
			response.getWriter().print(e1.getStackTrace());
			
		}

		long after = System.currentTimeMillis();
		String resTame = ("Time - " + (after - before) / 1000);
		request.setAttribute("statusOfWork", "Time will update : " + resTame);
		request.setAttribute("sitemapFile",	fileShema.replace(path,(request.getScheme() + "://" + request.getServerName()
								+ ":" + request.getServerPort() + "/SesrviseServletXML/Download/")));

		RequestDispatcher dispatcher = request.getRequestDispatcher("pages/urlGet.jsp");
		if (dispatcher != null) {
			try {
				dispatcher.forward(request, response);
			} catch (IOException e) {
				response.setContentType("text/html");
				response.getWriter().print("something is going wrong");
			}
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}

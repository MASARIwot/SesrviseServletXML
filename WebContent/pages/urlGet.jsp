<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>XMLcreator</title>
</head>
<body>
 <jsp:useBean id="calendar" class="java.util.GregorianCalendar"/>
 <p><%= calendar.getInstance().getTime() %></p>
<center>
	<h2 align="center"></h2>

	<form action="XmlCreator"  method="GET">
		<div>
			<b>Write you URl</b>
		</div>
		<div>
			<input type="url" required="true" rows="10" cols="65"
				name="InputURL" <%=request.getParameter("InputURL")%>></input>
		</div>
		<div>
			<input type="submit" value="Create">
		</div>
	
	<div>
		Status OF Work :
		<%=request.getAttribute("statusOfWork")%></div>
	<div>
		The Site Map.Follow by this link:
		<%="<a rel = \"nofollow\" href = \""
					+ request.getAttribute("sitemapFile")
					+ "\" Download> Open file </href>"%></div>

</form>
	</center>



</body>
</html>

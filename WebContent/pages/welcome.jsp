<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<META http-equiv=Content-Type content="text/html; charset=windows-1251">
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
				name="InputURL"></input>
		</div>
		<div>
			<input type="submit" value="Create">
		</div>
	
	<div>
		

</form>
	</center>



</body>
</html>

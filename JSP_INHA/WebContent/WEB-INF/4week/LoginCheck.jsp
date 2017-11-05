<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	
	if( id.equals("inha") && password.equals("inha")){
		response.sendRedirect("Success.jsp");
	}else{
		response.sendRedirect("Fail.jsp");
	}
%>

<!-- 
	<a href = "a.jsp?id=inha&password=inha&add=inha">인하</a>
	a태그를 이용해서 페이지를 이동하는 경우, 주소창 뒤에 파라미터를 붙여서 정보를 전송할 수 있다. 
 -->
</body>
</html>
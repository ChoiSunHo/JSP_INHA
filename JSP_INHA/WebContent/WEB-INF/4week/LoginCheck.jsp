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
	<a href = "a.jsp?id=inha&password=inha&add=inha">����</a>
	a�±׸� �̿��ؼ� �������� �̵��ϴ� ���, �ּ�â �ڿ� �Ķ���͸� �ٿ��� ������ ������ �� �ִ�. 
 -->
</body>
</html>
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
	String str = "Hello, Weclome to JSP world! Lets Go!";
	out.println("���ڿ� ���� : " + str.length() + "<p>");
	out.println("JSP ���� ��ġ : " + str.indexOf("JSP")+ "<p>");
	out.println("�ҹ��� ��ȯ : " + str.toLowerCase() + "<p>");
	out.println("�빮�� ��ȯ : " + str.toUpperCase() + "<p>");
	

%>
</body>
</html>
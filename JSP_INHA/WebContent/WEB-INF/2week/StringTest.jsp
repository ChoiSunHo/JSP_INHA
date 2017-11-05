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
	out.println("문자열 길이 : " + str.length() + "<p>");
	out.println("JSP 문자 위치 : " + str.indexOf("JSP")+ "<p>");
	out.println("소문자 변환 : " + str.toLowerCase() + "<p>");
	out.println("대문자 변환 : " + str.toUpperCase() + "<p>");
	

%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

<h1>ȸ������ ����ȸ</h1>

<table border="1">
	<tr>
		<td>���̵�</td><td>�̸�</td><td>��ȭ��ȣ</td><td>�ּ�</td>
	</tr>
	<tr>
		<td><%=request.getParameter("id")%></td>
		<td><%=request.getParameter("name")%></td>
		<td><%=request.getParameter("phone")%></td>
		<td><%=request.getParameter("addr")%></td>
	</tr>
</table>
</body>
</html>
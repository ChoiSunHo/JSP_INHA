<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
����ó�� <p>
<%
	String sub[] = new String[4];

	sub[0] = request.getParameter("el");
	sub[1] = request.getParameter("tcp");
	sub[2] = request.getParameter("se");
	sub[3] = request.getParameter("ja");
	
	for (int i = 0 ; i < sub.length ; i++){
		if (sub[i]==null){
			sub[i]="";
		}
	}
	
%>

<form action="SungJuk.jsp" method="post">
	���ڳ�ȸ�� <input type="text" name ="el" value = <%=sub[0]%>><p>
	TCP/IP <input type="text" name = "tcp" value = <%=sub[1]%>><p>
	�ý��ۺ��� <input type="text" name = "se" value = <%=sub[2]%>><p>
	JAVA���α׷��� <input type="text" name = "ja" value = <%=sub[3]%>><p>
	<input type="submit" value = "���">
</form>

<%
	float avg, tot = 0;
	
	for (int i = 0 ; i < sub.length ; i++){
		if (!(sub[i]==null) && !sub[i].equals("")){
			tot = tot + Float.parseFloat(sub[i]);
		}
	}
	avg = tot / 4;
	
	out.print("��� : " + avg);
%>




</body>
</html>
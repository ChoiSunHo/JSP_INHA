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
	request.setCharacterEncoding("EUC-KR");
	String id = request.getParameter("_id");
	String pwd = request.getParameter("_pwd");
	String _name = request.getParameter("_name");
	String _hpnum = request.getParameter("_hpnum");
	String _religion = request.getParameter("_religion");
	String[] inter = request.getParameterValues("inter");
	
	out.println("ȸ�� ��� �Ϸ� <p>");

	out.println(" ���� : " + _name);
	out.println("<p>");
	out.println(" ���̵� : " + id);
	out.println("<p>");
	out.println(" ��ȣ : " + pwd);
	out.println("<p>");
	out.println(" ��ȭ��ȣ : " + _hpnum);
	out.println("<p>");
	out.println(" ���� : " + _religion);
	out.println("<p>");
	out.println(" ���ɺо� : ");
	
	for (int i = 0; i < inter.length ; i++){
		out.println(inter[i]);
	}
	
	out.println("<p>");
%>

<!-------



-->






</body>
</html>
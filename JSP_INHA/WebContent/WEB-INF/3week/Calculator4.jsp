<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h1>계산기</h1>

<form action="Calculator4.jsp" method="post">
	<input type="hidden" name = "cal" value = "+">
	<input type="text" name ="firstnum"> +
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="Calculator4.jsp" method="post">
	<input type="hidden" name = "cal" value = "-">
	<input type="text" name ="firstnum"> -
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="Calculator4.jsp" method="post">
	<input type="hidden" name = "cal" value = "*">
	<input type="text" name ="firstnum"> *
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="Calculator4.jsp" method="post">
	<input type="hidden" name = "cal" value = "/">
	<input type="text" name ="firstnum"> /
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p>

<%
	String cal = request.getParameter("cal");
	String firstnum = request.getParameter("firstnum");
	String secondnum = request.getParameter("secondnum");
	
	if ( cal != null && firstnum != null && secondnum != null){
		switch(cal){
		case("+"):
			out.print(firstnum + " + ");
			out.print(secondnum + " = ");
			out.print(Integer.parseInt(firstnum) + Integer.parseInt(secondnum));
			break;
			
		case("-"):
			out.print(firstnum + " - ");
			out.print(secondnum + " = ");
			out.print(Integer.parseInt(firstnum) - Integer.parseInt(secondnum));
			break;
			
		case("*"):
			out.print(firstnum + " * ");
			out.print(secondnum + " = ");
			out.print(Integer.parseInt(firstnum) * Integer.parseInt(secondnum));
			break;
			
		case("/"):
			out.print(firstnum + " / ");
			out.print(secondnum + " = ");
			out.print(Integer.parseInt(firstnum) / Integer.parseInt(secondnum));
			break;
		}
	}

%>

</body>
</html>
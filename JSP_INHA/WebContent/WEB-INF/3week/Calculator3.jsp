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

<form action="CalculatorResult3.jsp" method="post">
	<input type="hidden" name = "cal" value = "+">
	<input type="text" name ="firstnum"> +
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="CalculatorResult3.jsp" method="post">
	<input type="hidden" name = "cal" value = "-">
	<input type="text" name ="firstnum"> -
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="CalculatorResult3.jsp" method="post">
	<input type="hidden" name = "cal" value = "*">
	<input type="text" name ="firstnum"> *
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

<p><p>

<form action="CalculatorResult3.jsp" method="post">
	<input type="hidden" name = "cal" value = "/">
	<input type="text" name ="firstnum"> /
	<input type="text" name ="secondnum">
	<input type="submit" value = "전송">
</form>

</body>
</html>
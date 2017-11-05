<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>Select 테스트</h1>
	<form action="Test001.jsp" method="get">
		<select name = "_portal">
			<option value="네이버">네이버</option>
			<option value="다음">다음</option>
			<option value="구글">구글</option>
		</select>
		<p>
		<input type="submit" value="전송">
	</form>
</body>
</html>
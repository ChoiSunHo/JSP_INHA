<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
회원 등록

<form action="MemberResult.jsp" method="post">
		성명 : <input type="text" name="_name"><p>
		아이디 : <input type="text" name="_id"><p>
		암호 : <input type="password" name="_pwd"><p>
		전화번호 : <input type="text" name="_hpnum"><p>
		
		종교 : <input type="radio" name="_religion" value="기독교">기독교
		<input type="radio" name="_religion" value="천주교">천주교
		<input type="radio" name="_religion" value="불교">불교<p>
		
		관심분야 : <input type="checkbox" name="inter" value="게임" />게임
		<input type="checkbox" name="inter" value="쇼핑" />쇼핑
		<input type="checkbox" name="inter" value="교육" />교육<p>

		<input type="submit" value="등록">
		<input type="reset" value="취소">		
	</form>
</body>
</html>
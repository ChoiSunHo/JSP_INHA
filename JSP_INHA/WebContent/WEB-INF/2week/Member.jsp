<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
ȸ�� ���

<form action="MemberResult.jsp" method="post">
		���� : <input type="text" name="_name"><p>
		���̵� : <input type="text" name="_id"><p>
		��ȣ : <input type="password" name="_pwd"><p>
		��ȭ��ȣ : <input type="text" name="_hpnum"><p>
		
		���� : <input type="radio" name="_religion" value="�⵶��">�⵶��
		<input type="radio" name="_religion" value="õ�ֱ�">õ�ֱ�
		<input type="radio" name="_religion" value="�ұ�">�ұ�<p>
		
		���ɺо� : <input type="checkbox" name="inter" value="����" />����
		<input type="checkbox" name="inter" value="����" />����
		<input type="checkbox" name="inter" value="����" />����<p>

		<input type="submit" value="���">
		<input type="reset" value="���">		
	</form>
</body>
</html>
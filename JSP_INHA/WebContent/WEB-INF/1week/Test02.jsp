<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<script language = "Javascript">
 function viewDate(){
 var d = new Date();
 document.FormName.Date.value = (d.getYear()+1900)+"년"+(d.getMonth()+1)+"월"+d.getDate()+"일";
}
</script>
<body>

<% 
 Date d = new Date();
 out.println((d.getYear()+1900)+"년"+(d.getMonth()+1)+"월"+d.getDate()+"일");
%>

 <Form Name = "FormName">
	 Local Date : <Input Type = "Text" Name = "Date">
 <Input Type = "Button" Value = "Call Local date" onClick="viewDate()">
 </Form>
</body>
</html> 
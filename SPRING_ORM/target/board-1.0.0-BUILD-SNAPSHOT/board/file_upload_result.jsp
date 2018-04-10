<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
파일명 : ${param.ufile}<br>
<img src="/uploads/${param.ufile}" width="200" height="200">
<a href="/abc?ufile=${param.ufile}">[다운로드]</a><br>
<hr>
</body>
</html>
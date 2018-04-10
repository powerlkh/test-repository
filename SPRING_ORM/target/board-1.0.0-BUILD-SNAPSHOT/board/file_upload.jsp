<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

File Upload Exam.<hr>

<form name="f" method="post"
action="/abc"
enctype="multipart/form-data" >
	아이디:<input type="text" name="mname" size="10" /><br>
	작성자:<input type="text" name="mname" size="10" /><br>
	파일:<input type="file" name="ufile" size="40" /><br>
    <input type="submit" value="upload" />
</form>
<br>
<hr>

<form name="f" method="post" action="/FileUploadServlet2" enctype="multipart/form-data" >
	작성자:<input type="text" name="name" size="10" /><br>
	파일1:<input type="file" name="ufile1" size="40" /><br>
	파일2:<input type="file" name="ufile2" size="40" /><br>
    <input type="submit" value="upload" />
</form>

</body>
</html>
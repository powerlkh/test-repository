<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/my.css">

<script>
function goPage(prm1, prm2) {

	myForm.ACTIONSTR.value = prm1;
	myForm.method = prm2;
	/*if(prm == "LIST") {
		myForm.method = "get";
	else
		myForm.method = "post";*/
	myForm.submit();
}
</script>

<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
<script>
/*
$(function() {
	//$(".myform").on("submit", fuction() {
	$("#listBtn").submit(function() {
		$("#ACTIONSTR").val("LIST");
		$(".myForm").attr("method","get");
	});
	$("#uptBtn").submit(function() {
		$("#ACTIONSTR").val("UPDATE");
		$(".myForm").attr("method","post");
	});
	$("#delBtn").submit(function() {
		$("#ACTIONSTR").val("DELETE");
		$(".myForm").attr("method","post");
	});
});
*/
</script>

</head>
<body>
<h2>글쓰기</h2><hr>

<form name="myForm" class="myForm" action="/insert.do" enctype="multipart/form-data" >
<input type="hidden" id="ACTIONSTR" name="ACTIONSTR" value="INSERT">
<table  class="demo-table">
	<thead>
		<tr>
			<th bgcolor="#cccccc">BTYPE</th>
			<th bgcolor="#cccccc">BTITLE</th>
			<th bgcolor="#cccccc">BCON</th>
			<th bgcolor="#cccccc">BFILE(NEW)</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="btype" value="btype"></td>
			<td><input type="text" name="btitle" value=""></td>
			<td><input type="text" name="bcon" value=""></td>
			<td><input type="file" name="ufile"></td>
		</tr>
	</tbody>
</table>

<input type="button" id="uptBtn" value="입력하기" onClick="goPage('INSERT','post')">
</form>

</body>
</html>

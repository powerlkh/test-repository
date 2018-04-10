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

	myForm.action = prm1;
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
<h2>수정하기</h2><hr>

<form name="myForm" class="myForm"  enctype="multipart/form-data" >
<input type="hidden" name="bseq" value="${LVL_VO.bseq}">
<input type="hidden" id="ACTIONSTR" name="ACTIONSTR">
<input type="hidden" id="bfile_path" name="bfile_path" value="${LVL_VO.bfile_path}">
<input type="hidden" id="bfile_name" name="bfile_name" value="${LVL_VO.bfile_name}">
<table  class="demo-table">
	<thead>
		<tr>
			<th bgcolor="#cccccc">BSEQ</th>
			<th bgcolor="#cccccc">BTYPE</th>
			<th bgcolor="#cccccc">BTITLE</th>
			<th bgcolor="#cccccc">BCON</th>
			<th bgcolor="#cccccc">MSEQ</th>
			<th bgcolor="#cccccc">BFILE(OLD)</th>
			<th bgcolor="#cccccc">BFILE(NEW)</th>
			<th bgcolor="#cccccc">REGDATE</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>${LVL_VO.bseq}</td>
			<td>${LVL_VO.btype}</td>
			<td><input type="text" name="btitle" value="${LVL_VO.btitle}"></td>
			<td><input type="text" name="bcon" value="${LVL_VO.bcon}"></td>
			<td>${LVL_VO.mseq}</td>
			<td>${LVL_VO.bfile_path}/${LVL_VO.bfile_name}  ${LVL_VO.bfile_size}KB</td>
			<td><input type="file" name="ufile"></td>
			<td>${LVL_VO.regdate}</td>
		</tr>
	</tbody>
</table>

<input type="button" id="listBtn" value="목록보기" onClick="goPage('/list.do','get')">
<input type="button" id="uptBtn" value="수정하기" onClick="goPage('/update.do','post')">
<input type="button" id="delBtn" value="삭제하기" onClick="goPage('/delete.do','post')">
</form>

</body>
</html>

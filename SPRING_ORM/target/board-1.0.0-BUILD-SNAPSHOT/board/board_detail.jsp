<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/my.css">

<script>
function goPage() {
	location.href = "/edit.do?bseq=${LVL_VO.bseq}";
}
</script>
</head>
<body>
<h2>상세보기</h2><hr>
<table  class="demo-table">
	<thead>
		<tr>
			<th bgcolor="#cccccc">BSEQ</th>
			<th bgcolor="#cccccc">BTYPE</th>
			<th bgcolor="#cccccc">BTITLE</th>
			<th bgcolor="#cccccc">BCON</th>
			<th bgcolor="#cccccc">MSEQ</th>
			<th bgcolor="#cccccc">BFILE_PATH</th>
			<th bgcolor="#cccccc">BFILE_NAME</th>
			<th bgcolor="#cccccc">BFILE_SIZE</th>
			<th bgcolor="#cccccc">REGDATE</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>${LVL_VO.bseq}</td>
			<td>${LVL_VO.btype}</td>
			<td>${LVL_VO.btitle}</td>
			<td>${LVL_VO.bcon}</td>
			<td>${LVL_VO.mseq}</td>
			<td>${LVL_VO.bfile_path}</td>
			<td>${LVL_VO.bfile_name}</td>
			<td>${LVL_VO.bfile_size} (KB)</td>
			<td>${LVL_VO.regdate}</td>
		</tr>
	</tbody>
</table>

<input type="button" value="목록보기" onClick="javascript:history.go(-1)">

<%//if("${LVL_SESS_MSEQ}" == "${LVL_VO.mseq}") { %>
<input type="button" value="수정하러하기" onClick="goPage()">
<%//} %>

<form method="post" action="/delete.do">
<input type="hidden" name="bseq" value="${LVL_VO.bseq}">
<input type="hidden" name="mseq" value="${LVL_VO.mseq}">
<input type="hidden" name="bfile_path" value="${LVL_VO.bfile_path}">
<input type="hidden" name="bfile_name" value="${LVL_VO.bfile_name}">
<%//if("${LVL_SESS_MSEQ}" == "${LVL_VO.mseq}") { %>
<input type="submit" value="삭제하기">
<%//} %>
</form>
</body>
</html>

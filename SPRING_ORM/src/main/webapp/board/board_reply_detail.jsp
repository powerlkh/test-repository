<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/my.css">
</head>
<body>
<h2>보드상세+댓글목록</h2><hr>
<table  class="demo-table" border="1" cellpadding="0" cellspacing="0"   width=400>

		<tr>
			<th bgcolor="#cccccc">BSEQ</th>
			<td>${LVL_VO.bseq}</td>
		</tr>
		<tr>
			<th bgcolor="#cccccc">BTITLE</th>
			<td>${LVL_VO.btitle}</td>
		</tr>
</table>
<br>
<table  class="demo-table" border="1" cellpadding="0" cellspacing="0"   width=400>
	<c:forEach items="${LVL_VO.rlist}" var="r">
		<tr>
			<td>
				 ${r.bseq} : ${r.rseq} : ${r.reply}  <a hef="">[삭제]</a>
			</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>

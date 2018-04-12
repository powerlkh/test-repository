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
<h2>보드목록+댓글목록</h2><hr>
<table  class="demo-table" border="1" cellpadding="0" cellspacing="0" width=400>
	<thead>
		<tr>
			<th bgcolor="#cccccc">BSEQ</th>
			<th bgcolor="#cccccc">BTITLE</th>
			<th bgcolor="#cccccc">REPLY bseq:rseq:reply </th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${LVL_LIST}" var="vo">
		<tr>
			<td><a href="/brdetail.do?bseq=${vo.bseq}">${vo.bseq}</a></td>
			<td>${vo.btitle}</td>
			<td>
				<c:forEach items="${vo.rlist}" var="r">
					 ${r.bseq} : ${r.rseq} : ${r.reply} <br>
				</c:forEach>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/my.css">
</head>
<body>
<h2>목록</h2><hr>
총 게시물 수 : ${LVL_COUNT} 건 <br>
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
	<c:forEach items="${LVL_LIST}" var="vo">
		<tr>
			<td>${vo.bseq}</td>
			<td>${vo.btype}</td>
			<td><a href="/detail.do?bseq=${vo.bseq}">${vo.btitle}</a></td>
			<td>${vo.bcon}</td>
			<td>${vo.mseq}</td>
			<td>${vo.bfile_path}</td>
			<td>${vo.bfile_name}</td>
			<td>${vo.bfile_size}</td>
			<td>${vo.regdate}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<br>
${LVL_PAGING}

<br>
<a href="/board/board_input.jsp">[글쓰기]</a>
</body>
</html>

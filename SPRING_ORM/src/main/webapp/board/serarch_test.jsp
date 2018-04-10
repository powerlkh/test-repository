<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
	$(function() {

		$("#xmlBtn").click(function() {
			$.ajax({
				url:"/SearchServlet",
				//dataType:"json",
				//data:{"MYKEY": JSON.stringify(jsonObjectData) },
				type:"get",
				success:function(result) {
					console.log(result);
					$("#searchRes").html(result);
				}
			});
		});


		function convertArrayToJson(formArray) { //serialize data function
			var returnArray = {};
			for (var i = 0; i < formArray.length; i++) {
				returnArray[formArray[i]['name']] = formArray[i]['value'];
			}
			return returnArray;
		}

		//$("#searchBtn").click(function() {
		$("#searchStr").keyup(function() {
			//alert("D");

			//var data = $(".myForm").serialize();
			//console.log(data);	//searchGubun=btitle&searchStr=sss

			var jsonObjectData = {"searchGubun":$("#searchGubun").val()
					, "searchStr":$("#searchStr").val()};
			console.log(jsonObjectData);	//{searchGubun: "btitle", searchStr: "d"}

			$.ajax({
				url:"/SearchServlet",
				dataType:"json",
				data:{"MYKEY": JSON.stringify(jsonObjectData) },
				type:"post",
				success:function(result) {
					console.log(result);
					//[{…}, {…}, {…}, {…}, {…}, {…}]
					var htmlStr = "";
					$.each(result, function(i,v) {
						htmlStr += "<tr><td>"+v.bseq+"</td><td>"+v.btitle+"</td><td>"+v.regdate+"</td></tr>"
					});
					$("#searchRes").html(htmlStr);
				}
			});

	});
});
</script>
<body>
	<form class="myForm" method="post" action="/SearchServlet">
		<select id="searchGubun" name="searchGubun">
			<option value="btitle">제목</option>
			<option value="bcon">내용</option>
		</select> <input type="text" name="searchStr" id="searchStr"> <input
			type="button" id="searchBtn" value="검색">
	</form>

	<form method="get" action="/ParserServlet">
	<input type="button" id="xmlBtn" value="조선닷컴 기사 가져오기">
	</form>
<a href="/search.do">dddd</a>
<!--  테이블 디자인 -->

<table  class="demo-table">
	<thead>
		<tr>
			<th bgcolor="#cccccc">BSEQ</th>
			<th bgcolor="#cccccc">BTITLE</th>
			<th bgcolor="#cccccc">REGDATE</th>
		</tr>
	</thead>
	<tbody id="searchRes">
	</tbody>
</table>


</body>
</html>
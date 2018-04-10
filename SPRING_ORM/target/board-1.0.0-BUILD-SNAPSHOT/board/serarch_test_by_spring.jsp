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
		function convertArrayToJson(formArray) { //serialize data function
			var returnArray = {};
			for (var i = 0; i < formArray.length; i++) {
				returnArray[formArray[i]['name']] = formArray[i]['value'];
			}
			return returnArray;
		}

		//$("#searchBtn").click(function() {
		$("#searchStr").keyup(function() {
			var jsonObjectData = {"searchGubun":$("#searchGubun").val()
					, "searchStr":$("#searchStr").val()};
			console.log(jsonObjectData);	//{searchGubun: "btitle", searchStr: "d"}

			$.ajax({
				url:"/search.do",
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


		$("#searchStr2").keyup(function() {
			var jsonObjectData = {"searchGubun":$("#searchGubun").val() , "searchStr":$("#searchStr2").val()};
			//var jsonObjectData = $('#myForm').serialize();
			console.log(jsonObjectData);	//searchGubun=btitle&searchStr=dd

			$.ajax({
				type:"post",
				url:"/search2.do",
				//data:jsonObjectData,
				headers: {
			        'Accept': 'application/json',
			        'Content-Type': 'application/json'
			    },
				dateType: "json",
				//data:JSON.stringify(jsonObjectData),
				data:// $.param(
              	 JSON.stringify(jsonObjectData),
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
	<form class="myForm" method="post" action="/search.do">
		<select id="searchGubun" name="searchGubun">
			<option value="btitle">제목</option>
			<option value="bcon">내용</option>
		</select>
		<input type="text" name="searchStr" id="searchStr">
		<input type="text" name="searchStr2" id="searchStr2">
		<input type="button" id="searchBtn" value="검색">
	</form>

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
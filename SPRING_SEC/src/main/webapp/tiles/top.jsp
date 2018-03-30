<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.js" type="text/javascript"></script>
</head>
<body>
/sample/tiles/top.jsp<br>
<a href="/member/login_page.jsp">로그인폼(/member/login_page.jsp)</a><br>
<a href="tiles_member_login_page">로그인폼(tiles_member_login_page)</a><br>
<a href="/loginPageByTiles">로그인폼(/loginPageByTiles)</a><br>
<a href="/loginPageByMav">로그인폼(/loginPageByMav)</a><br>
<a href="/mlist">회원목록(/mlist)</a><br>
<a href="side_member_denied_page">side(side)</a><br>
<a href="side2_member_denied_page">side2(side)</a><br>


<a href="javascript:pageOpen('popup_login_page')">회원목록팝업(popup_member_login_page)</a><br>





<button type="button" id="list_board">ajax</button><br>


<!-- <button onclick="myFunction()">Try it</button> -->

<script>
$(document).ready(function(){

	//보드 리스트
	$("#list_board").click(function(){
    	  $.ajax({
              type: "POST",
              url: "${path}/ajaxlist.do",
              headers: {
                  "Content-Type" : "application/json"
              },
              dataType: "json",
              data:// $.param(
              	 JSON.stringify(
              	{
              		//searchGubun:"mtitle",
              		//searchStr:"dd",
              	}
              ),
              success: function(list){
            	    console.log(list);

					//[{…}, {…}, {…}, {…}, {…}, {…}]
					var htmlStr = "";

					$.each(list, function(i,vo) {
						htmlStr += "<tr><td>"+vo.bseq+"</td><td>"+vo.btitle+"</td><td>"+vo.regdate
						+"</td></tr>"
					});
					$("#resultDiv").html(htmlStr);

              }
          });
    });
});
</script>

<script>
function pageOpen(url) {
    window.open(url, "_blank"
    		, "toolbar=yes,scrollbars=yes,resizable=yes,top=200,left=300,width=900,height=500");
}
</script>


</body>
</html>
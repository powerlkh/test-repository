<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.js"
	type="text/javascript"></script>
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
              		sseq:"1",
              		eseq:"20"
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


	//게시판 상세보기
	$("#detail_board").click(function(){
    	  $.ajax({
              type: "POST",
              url: "${path}/ajaxdetail.do",
              headers: {
                  "Content-Type" : "application/json"
              },
              dataType: "json",
              data:// $.param(
              	 JSON.stringify(
              	{
              		bseq:"141"
              	}
              ),
              success: function(result){
            	  	console.log(result);
            	  	$('#resultDiv').html(result.btitle);
              }
          });
    });


	//BD : 게시판 등록
   	$("#reg_board").click(function(){
       	  $.ajax({
                 type: "POST",
                 url: "${path}/ajaxinsert.do",
                 dataType: "json",
                 data:// $.param(
                 	{
                 		btype:"btype"
                 		,btitle:"이건제목999"
                 		,bcon:"이건내용999"
                 		,mseq:"1"
                 	},
                 success: function(result){
               	    console.log(result);
                 }
             });
       });

});
</script>
</head>
<body>

	<button type="button" id="list_board">
	게시판 리스트 : (페이징,startSeq:"0", rowPerPage:"2")</button><br>

	<button type="button" id="detail_board">게시판 상세보기</button>
	<a href ="${path}/ajaxdetail.do/141/1">게시판 상세보기</a><br>

<a href ="/ajaxdetail.do?bseq=141">게시판 상세보기</a><br>

	<button type="button" id="reg_board">게시판 등록</button>
<div id="resultDiv">
res : ${LVL_VO}
</div>
</body>
</html>
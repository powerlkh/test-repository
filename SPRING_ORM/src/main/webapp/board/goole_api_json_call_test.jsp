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
		$("#detailBtn").click(function() {
			$.ajax({
				url:"/get_goole_api.do",
				type:"post",
				success:function(result) {
					//console.log(result);  //xx건
					var jsonObj = JSON.parse(result);
					console.log(jsonObj);
					/*
					console.log(jsonObj.result.international_phone_number);
					console.log(jsonObj.result.opening_hours.weekday_text[0]);
					console.log(jsonObj.result.photos[0].photo_reference);
					console.log(jsonObj.result.reviews[0].author_name);

					var photosArray = new Array();
					photosArray = jsonObj.result.photos;
					console.log(photosArray);
					for(i=0; i<photosArray.length; i++) {
						console.log(photosArray[i].photo_reference);
					}

					var reviewsArray = new Array();
					reviewsArray = jsonObj.result.reviews;
					console.log(reviewsArray);
					for(i=0; i<reviewsArray.length; i++) {
						console.log(reviewsArray[i].author_name);
						console.log(reviewsArray[i].author_url);
					}
 					*/

					//$("#searchRes").html(jsonRes.international_phone_number);
				}
			});
		});



});
</script>
<body>


	<form method="get">
	<input type="button" id="detailBtn" value="구글 상세보기 가져오기">
	</form>



</body>
</html>
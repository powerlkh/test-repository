<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Image Preview with jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.gdocsviewer.min.js"></script>
<!--
https://chrome.google.com/webstore/search/new%20tab%20redirect?utm_source=chrome-ntp-icon
 -->

<script>
$(document).ready(function() {
	$('a.embed').gdocsViewer({width: 480, height: 350});
	//$('#embed').gdocsViewer();

	$('a.preview').click(function(){
		var url =  $(this).attr("id");
		window.open(url, 'my div', 'height=400,width=500');
		//mywindow.print();
	});
});
</script>
</head>
<body>
	<h2>Image gallery (with caption)</h2>
	<a href=# class="preview" id="/uploads/aaaa.png">미리보기 png</a>
	<a href=# class="preview" id="/uploads/sample-3pp.pdf">미리보기 pdf</a>
	<a href=# class="preview" id="/uploads/sample-1pp.docx">미리보기 doc</a>
<br>
<a href="http://www.snee.com/xml/xslt/sample.doc" class="embed" style="text-align">ddd</a><br>
<a href="http://plugindoc.mozdev.org/testpages/test.pdf" class="embed" id="test">PDF at mozdev.org</a>


</body>
</html>

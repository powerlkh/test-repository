<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Image Preview with jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.gdocsviewer.min.js"></script>

<script>

/* function imagePreview(){
	xOffset = 10;
	yOffset = 30;

	$("a.imgPreview").hover(function(e){
		$("body").append("<p id='imgPreview'><img src='"+ this.href +"' alt='Image preview' width='200px' height='200px' /></p>");
		$("#imgPreview").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px").fadeIn("fast");
	},
	function(){
		$("#imgPreview").remove();
	});
	$("a.imgPreview").mousemove(function(e){
		$("#imgPreview").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px");
	});
}; */


function preview(){
	xOffset = 10;
	yOffset = 30;

	$("a.preview").hover(function(e){
		var f =  $(this).attr('id');
		var ext =f.split('.').pop().toLowerCase();

		if(ext == "pdf") {
			$("body").append("<object id='preview' data='"+ this.href +"' type='application/pdf' width='400px' height='400px'></object>");
		} else if (ext == "png" || ext == "jpg" || ext == "gif") {
			$("body").append("<p id='preview'><img src='"+ this.href +"' alt='Image preview' width='400px' height='400px' /></p>");
		}  else if (ext == "doc" || ext == "docx" || ext == "ppt" || ext == "pptx") {
			$.ajax({
				url:"/converter.do?origFile="+f,
				type:"get",
				success:function(result) {
					console.log(result);
					$("body").append("<object id='preview' data='/uploads/"+result+"' type='application/pdf' width='400px' height='400px'></object>");
				},
				error:function(result) {
					$("body").append("<p id='preview'>미리보기가 지원되지 않습니다.</p>");
				}
			});
		} else {
			$("body").append("<p id='preview'>미리보기가 지원되지 않습니다.</p>");
		}

		$("#pdfPreview").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px").fadeIn("fast");
	},
	function(){
		$("#preview").remove();
	});

};

$(document).ready(function() {
	preview();
	//$('a.embed').gdocsViewer({width: 480, height: 350});
	//$('#embedURL').gdocsViewer();
});
</script>

</head>
<body>
	<h2>Image gallery (with caption)</h2>
	<a href="/uploads/aaaa.png" id="aaaa.png" class="preview" >미리보기 img</a><br>
	<a href="/uploads/sample-3pp.pdf" id="sample-3pp.pdf" class="preview" >미리보기 pdf</a><br>
	<a href="/uploads/sample-4pp.pptx" id="sample-4pp.pptx" class="preview" >미리보기 pptx</a><br>
	<a href="/uploads/sample-1pp.docx" id="sample-1pp.docx" class="preview" >미리보기 docx1</a><br>
	<a href="/uploads/sample-11pp.docx" id="sample-11pp.docx" class="preview" >미리보기 docx111</a><br>
	<a href="/uploads/samle-2pp.hwp" id="samle-2pp.hwp" class="preview" >미리보기 hpw</a><br>


<br>
<!-- <iframe src='http://docs.google.com/viewer?embedded=true&url=http://www.lkh.com/uploads/sample-1pp.docx' width='200' height='200' style='border: none;'></iframe>
 -->

</body>
</html>

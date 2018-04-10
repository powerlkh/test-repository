<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title>costagram</title>
   <meta charset="UTF-8">
   <meta name=viewport content="width=device-width, initial-scale=1">

   <link rel=icon href=favicon.ico sizes="16x16" type="image/png">

   <!-- Preloader css must be first -->

   <!-- JS -->
   <script src="/insta/assets/vendor/animatedheader/js/modernizr.custom.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <!-- CSS -->
   <link href="/insta/assets/vendor/bootstrap/css/bootstrap.min.css" property='stylesheet' rel="stylesheet" type="text/css" media="screen"/>
   <link href="/insta/assets/vendor/pe-icon-7-stroke/css/pe-icon-7-stroke.css" property='stylesheet' rel="stylesheet" type="text/css" media="screen"/>
   <link href="/insta/assets/vendor/fontawesome/css/font-awesome.min.css" property='stylesheet' rel="stylesheet" type="text/css" media="screen"/>
   <link href="/insta/assets/vendor/owl-carousel/assets/owl.carousel.css" property='stylesheet' rel="stylesheet" type="text/css" media="screen"/>

   <link href="/insta/assets/custom/css/style.css" property='stylesheet' rel="stylesheet" type="text/css" media="screen"/>

</head>
<body class="boxed">

<!--Pre-Loader-->
<div id="preloader"><img src="/insta/assets/custom/images/preloader.gif" alt="01"></div>
<!--/Pre-Loader-->

<div id="wrapper" class="">

   <!-- Content -->
   <div id="page-content-wrapper" class="content-wrap">

      <!-- Header -->
      <header class="cbp-af-header toggled">
         <div class="cbp-af-inner">
            <div class="navbar navbar-default" role="navigation">
               <div class="container">
                  <!-- Brand and toggle get grouped for better mobile display -->
                  <div class="navbar-header"  >
                     <h1 class="text-center"><a href="index.html">costagram</a></h1>
                  </div>
               </div>
               <div class="container">
                  <div class="bordered">
                     <!-- Collect the nav links, forms, and other content for toggling -->
                        <nav class="collapse navbar-collapse navbar-ex1-collapse e-centered">
                        <!--<i class="nav-decor-left flaticon-christmas-rose"></i>-->
                        <ul class="nav navbar-nav">
                                 <li><a href="/homeList">HOME</a></li>
                           <li><a href="/searchList">SEARCH</a></li>
                           <li><a href="/insertContent">PHOTO</a></li>
                           <li><a href="/newfeedList">NEWS FEED</a></li>
                           <li><a href="/ProfileServlet">PROFILE</a></li>
                        </ul>
                     </nav>
                  </div>
               </div>
            </div>
         </div>
      </header>
      <!-- / Header -->

      <!-- Content Sections -->
      <div id="content">

         <!-- Post Feed -->
         <section id="articles" class="">
            <div class="container">
               <div class="row has-sidebar-right">
               <form action="/searchName" method="post"> 
               <div class="col-md-5" ><input name="searchText" placeholder="검색 " id="searchText" ></div>
    			   <div> <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></div>
    			   </form>		
                  <!--Content Column-->
                  <div class="col-md-8 pull-left">
                     <div class="row">
                        <article id="01" class="post-block col-md-12 post hentry">
                     <c:forEach items="${followerList }" var="f" varStatus="sts">
                           <div class="post-detail">
                              <div class="metas">
                                 Posted on: <a class="meta-date">${f.regdate }</a>/&nbsp;
                                 Posted by: <a href="/searchName?searchText=${f.mseq }" class="meta-author">${f.memberName }</a>/&nbsp;
                                 <a class="meta-comment">0 Comment</a>
                              </div>
                           </div>
                           <div class="img-wrap">
                              <a href="/searchDetail?cseq=${f.cseq}"><img class="img-responsive" src="/insta/instaImage/${f.fileName }" alt="이미지 로딩이 안됐습니다."></a>
                           </div>
                           <div class="post-excerpt">
                              <p>${f.con }</p>
                           </div>
                             <div>
                              <a class="btn btn-default btn-rose-str"  id="${sts.index}">댓글보기</a>
                           </div>
                           <div id="replyViewDiv${sts.index}" style="display: none;">
                           <c:forEach items="${f.rlist}" var="r">
		                   <span style="font-weight:bold">${r.memberName }</span> : <span>${r.replyCon}</span>
                           <span style="cursor: pointer; color: red;" onclick="location='/replyDelete?rseq=${r.rseq}&cseq=${f.cseq }'" title="댓글 삭제">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;X</span>
                           <br>
                           </c:forEach>
                            <form action="/replyInsert" method="post">
                           <input type="text" placeholder="댓글 달기...." style="width:400px; height: 30px;" id="replyCon" name="replyCon">
                           <input type="hidden" value="${f.cseq }" id="cseq" name="cseq">
                           <input type="hidden" value="homeReply" name="kang" id="kang">
                           </form>
                           </div>
                        </c:forEach>
                        </article>
                     </div>
                  </div>
                  <!--/Content Column-->

                  <!--Sidebar-->
                  <div class="col-md-4 sticky-column" data-sticky_column >
                     <div class="sidebar">

                        <!--About Widget-->
                        <aside id="about-widget" class="widget widget_about">
                           <div class="text-centerwidget-wrap">
                              <br>
                              <br>
                           </div>
                        </aside>
                        <!--/ About Widget-->
                        
                        <!--Resent Posts Widget-->
                        <aside id="resent-widget" class="widget widget_recent_post">
                           <div class="title widget-title lined">스토리</div>
                           <div class="e-divider-3"></div>
                              <a class="hello" href="#">나를 팔로우하는 사람들의 스토리가 여기에 표시됩니다.</a>
                        </aside>
                        <!--/Resent Posts Widget-->
                        
                         <!--About Widget-->
                        <aside id="about-widget" class="widget widget_about">
                           <c:forEach items="${requesterList }" var="r">
                           <div class="avatar img-circle" >
                            <a href="/searchDetail?cseq=${r.cseq}">  <img src="/insta/instaImage/${r.fileName }" width="100%" alt="이미지 로딩이 안됐습니다." ></a>
                              <div class="e-divider-3">
                              <a class="hello" href="/searchName?searchText=${r.mseq }">${r.memberName }</a>
                              </div>
                           </div>
                               </c:forEach>
                        </aside>
                        <!--/ About Widget-->

                     </div>
                  </div>
                  <!--/Sidebar-->
               </div>
            </div>
         </section>
         <!-- /Post Feed -->
      </div>
      <!-- /Content Sections -->
       <!-- Back to Top -->
       <div id="back-top"><a href="#top"></a></div>
       <!-- /Back to Top -->
   </div>
   <!-- /Content -->
</div>

<div id="image-cache" class="hidden"></div>
<script>
	$('.btn.btn-default.btn-rose-str').click(function() {
		var idx = $(this).attr("id");
		$("#"+"replyViewDiv"+idx).show();
		console.log(idx);
	});
</script>

<script>
function showComment() {
	$("#comment").show();
	$("#show").hide();
	$("#hide").show();
}
</script>
<script>
function hideComment() {
	$("#comment").hide();
	$("#show").show();
	$("#hide").hide();
}
</script>

<script >
$('.hashAhref').click(function() { 
	var tagValue = $(this).attr("id");
	//alert(tagValue);
	$('#aHashtag').val(tagValue);
	$('#hashtagForm').submit();
	});
</script>

<script type="text/javascript">

$(function() {
	var list = "${listName}";
	console.log("${listName}")
	var list2 = list.slice(0,-1);
	var list3 = list2.substring(1);
	var availableCity = list3.split(",");
	var trimAvailableCity = [];
	$.each(availableCity, function(i, v){
		trimAvailableCity.push(v.trim());
		//console.log(trimAvailableCity);
	}); 
	console.log(trimAvailableCity)
    $("#searchText").autocomplete({
        source: trimAvailableCity,
        select: function(event, ui) {
            
        },
        focus: function(event, ui) {
            return false;
            //event.preventDefault();
        }
    });
});
</script>

<!-- JS -->

<script src="/insta/assets/vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/insta/assets/vendor/animatedheader/js/classie.js"></script>
<script src="/insta/assets/vendor/animatedheader/js/cbpAnimatedHeader.js"></script>
<script src="/insta/assets/vendor/sticky-scroll/js/jquery.stickit.min.js" type="text/javascript"></script>
<script src="/insta/assets/vendor/owl-carousel/owl.carousel.min.js" type="text/javascript"></script>

<!-- Custom scripts -->
<script src="/insta/assets/custom/js/script.js" type="text/javascript"></script>

</body>
</html>

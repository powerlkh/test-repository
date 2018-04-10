<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<title>KakaoLink v2 Demo(Default / Feed) - Kakao JavaScript SDK</title>
<script src="http://developers.kakao.com/sdk/js/kakao.min.js"></script>

</head>
<body>
 <h1>이건 테스트 입니다.
 상품상세보기 페이지</h1><hr>

<%
for(int i=0; i<4; i++) {
%>
<img src="/uploads/aaaa.png">
<br>
핫식스 1BOX<br>
price : 25000원<br>

<input type="button" value="구매">
<a id="kakao-link-btn" href="javascript:;">
<img width=30 height=30 src="http://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"/>
</a><br>
<%
}
%>


<script type='text/javascript'>
  //<![CDATA[
    // // 사용할 앱의 JavaScript 키를 설정해 주세요.
    Kakao.init('608a36a47178b8ee2b3c5803a947ab72');
    // // 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
    Kakao.Link.createDefaultButton({
      container: '#kakao-link-btn',
      objectType: 'feed',
      content: {
        title: '이건이경화테스트',
        description: '이건 카톡 OPEN API 첫 연동입니다. 믿고 클릭...',
        imageUrl: 'http://192.168.0.72/uploads/aaaa.png',
        link: {
          mobileWebUrl: 'http://192.168.0.72/list.do',
          webUrl: 'http://192.168.0.72/list.do'
        }
      },
      social: {
        likeCount: 200,
        commentCount: 0,
        sharedCount: 500
      },
      buttons: [
        {
          title: '웹으로 보기',
          link: {
            mobileWebUrl: 'http://192.168.0.72/list.do',
            webUrl: 'http://192.168.0.72/list.do'
          }
        },
        {
          title: '앱으로 보기',
          link: {
            mobileWebUrl: 'http://192.168.0.72/list.do',
            webUrl: 'http://192.168.0.72/list.do'
          }
        }
      ]
    });
  //]]>
</script>

</body>
</html>
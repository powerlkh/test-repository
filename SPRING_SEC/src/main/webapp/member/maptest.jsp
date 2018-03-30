<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Simple markers</title>
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
    <script>

      function initMap() {
        var myLatLng = {lat: -25.363, lng: 131.044};
        var myLatLng2 = {lat: -25.363, lng: 231.044};

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: myLatLng
        });

        var marker = new google.maps.Marker({
          position: myLatLng,
          map: map,
          title: 'Hello World!',
          animation: google.maps.Animation.DROP,
          icon:'https://cdn.iconverticons.com/files/png/b351def51b2feaa6_32x32.png'
        });
        var marker2 = new google.maps.Marker({
            position: myLatLng2,
            map: map,
            title: 'Hello World!',
            animation: google.maps.Animation.BOUNCE,
            icon:'https://cdn.iconverticons.com/files/png/b351def51b2feaa6_48x48.png'
          });

        var infowindow = new google.maps.InfoWindow({
            content: '클릭했습니다.'
          });

        marker.addListener('click', function() {
        	 infowindow.open(map, marker);
          });

        marker2.addListener('mouseover', function() {
            alert('마우스오버')
          });

      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDd5qVqhzCXiTl4_8pGuLEdXEk6pWeMlgY&callback=initMap">
    </script>
  </body>
</html>
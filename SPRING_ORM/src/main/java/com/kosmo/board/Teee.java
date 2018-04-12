package com.kosmo.board;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Teee {

	public static void main(String[] args) {
		String location = "";
		try {
			//--------------------------
			//   URL 설정하고 접속하기
			//--------------------------
			String addr = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJod7tSseifDUR9hXHLFNGMIs&key=AIzaSyAX9qYtgvZNmWDKq3vYec6VQHVQld-yTlE";
			URL url = new URL(addr); //Google. api url
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
		//	http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Encoding", "identity");
			http.setRequestMethod("GET");
		//	http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		//	http.setRequestProperty("Content-Length", http.getContentLength()+"");
			//response.addHeader("Content-Length",  response.setContentLength());


		//	//HTTP 바디에 key=value&key=value 값을 쓰기
		//	StringBuffer buffer = new StringBuffer();
		//	buffer.append("id").append("=").append(mid).append("&");	//파라미터
		//	buffer.append("pword").append("=").append(mpw);				//파라미터
		//
		//	//OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
		//    OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
		//    PrintWriter pw = new PrintWriter(osw);
		//    pw.write(buffer.toString());
		//    pw.flush();


		    //구글에서 온 답변 받기
		    //location = http.getHeaderField("location");
		    location = http.getHeaderField("content-length");


		    System.out.println(location + "---------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

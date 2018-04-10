package com.kosmo.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestController {

	private final String CLIENT_ID = "132153140522-75671d9c3tt48889ogms76k0jkhkurcd.apps.googleusercontent.com";
	private final String REDIRECT_URI = "http://www.lkh.com/oauth2callback";
	private final String SECURITY_KEY= "KWHagV046P-BCMysMBiDhwKz";

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/oauth2", method = RequestMethod.GET)
	public String code() {

		String url = "https://accounts.google.com/o/oauth2/v2/auth";

		String scope = "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar";
		String access_type = "offline";
		String include_granted_scopes = "true";
		String state = "state_parameter_passthrough_value";
		String response_type = "code";


		url += "?"
				+ "scope=" + scope + "&"
				+ "access_type=" + access_type + "&"
				+ "include_granted_scopes=" + include_granted_scopes + "&"
				+ "state=" + state + "&"
				+ "redirect_uri=" + REDIRECT_URI + "&"
				+ "response_type=" + response_type + "&"
				+ "client_id=" + CLIENT_ID;

		System.out.println(url);

		return "redirect:"+url;
	}




	@RequestMapping(value = "/oauth2callback", method = RequestMethod.GET)
	public String getCode(
			@RequestParam("state") String state,
			@RequestParam("code") String code,
			@RequestParam("scope") String scope,
			HttpServletRequest request
			) {

		System.out.println(
				"state = " + state + "\n" +
				"code = " + code  + "\n" +
				"scope = " + scope
				);

		state = "state=" + state;
		scope = "scope=" + scope;
		code = "code=" + code;
		String client_id = CLIENT_ID;
		String client_secret = "client_secret="+SECURITY_KEY;
		String redirect_uri = "redirect_uri=" + REDIRECT_URI;
		String grant_type = "grant_type=authorization_code";

		String result = "";

		try {
			
			URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			StringBuffer buffer = new StringBuffer();
//			buffer.append(state).append("&");	//파라미터
//			buffer.append(scope).append("&");	//파라미터
			buffer.append(code).append("&");	//파라미터
			buffer.append(client_id).append("&");	//파라미터
			buffer.append(client_secret).append("&");	//파라미터
			buffer.append(redirect_uri).append("&");	//파라미터
			buffer.append(grant_type).append("&");	//파라미터

			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            PrintWriter pw = new PrintWriter(osw);
            pw.write(buffer.toString());
            pw.flush();


            //구글에서 온 답변 받기
            int responseCode = conn.getResponseCode();
    		System.out.println("응답받기 ::::  " + responseCode);

    		if (responseCode == HttpURLConnection.HTTP_OK) { //success

				InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				StringBuilder builder = new StringBuilder();
				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					builder.append(inputLine + "\n");
				}
				br.close();
				result = builder.toString();
				System.out.println(result);
				Gson gson = new Gson();
//				gson.fromJson(result, classOfT)
    		}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		mav.addObject("json", result);
//		mav.setViewName("/getToken.do");

		return "redirect:/getToken";

	}

	
	
		
		
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public String getToken() {

		System.out.println("getToken");

		String result = null;
		try {

			URL url = new URL("https://www.googleapis.com/calendar/v3/users/me/calendarList?");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			String access_token = "4/AADupQVSzSE0sY6jbBI9Gf1PMrwwjbNgKTz67N5JX-bDI8dZ_6w70Z3M0gvavjvImH4fixxa9V3rAnZbzIRbCP0";

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("GET");
//			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

//			StringBuffer buffer = new StringBuffer();
//			buffer.append(state).append("&");	//파라미터
//			buffer.append(scope).append("&");	//파라미터
//			buffer.append(code).append("&");	//파라미터
//			buffer.append(client_id).append("&");	//파라미터
//			buffer.append(client_secret).append("&");	//파라미터
//			buffer.append(redirect_uri).append("&");	//파라미터
//			buffer.append(grant_type).append("&");	//파라미터

//			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//          PrintWriter pw = new PrintWriter(osw);
//          pw.write(buffer.toString());
//          pw.flush();


            //구글에서 온 답변 받기
            int responseCode = conn.getResponseCode();
    		System.out.println("응답받기 ::::  " + responseCode);

    		if (responseCode == HttpURLConnection.HTTP_OK) { //success

				InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				StringBuilder builder = new StringBuilder();
				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					builder.append(inputLine + "\n");
				}
				br.close();
				result = builder.toString();
				System.out.println(result);
    		}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "alone/calendar/alone_calendar";
	}


}

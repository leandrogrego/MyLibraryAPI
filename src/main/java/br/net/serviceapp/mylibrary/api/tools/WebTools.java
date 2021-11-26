package br.net.serviceapp.mylibrary.api.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebTools {
    
	public static Object get(String link, String token) {
		
		try {
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			if(token != null && token != "") {
				con.setRequestProperty("Authorization", "Bearer "+token);
			}
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream())
				);
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();		
				System.out.println(response);
				Object at= new ObjectMapper().readValue(response.toString(), Object.class);
				return at;
			} else {
				System.out.println("GET request fail: "+responseCode);
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

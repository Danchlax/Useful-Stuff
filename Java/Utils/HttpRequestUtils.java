package monitora.qa.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import junit.framework.Test;
import monitora.qa.core.TestProperties;
import org.json.simple.JSONObject;

public class HttpRequestUtils {
	
	private static HttpURLConnection getHttpConnection(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");

		
		return connection;
	}
	
	private static String sendPayloadAndGetResponse(HttpURLConnection connection, String payload) throws IOException {
		OutputStream writer = connection.getOutputStream();
		writer.write(payload.getBytes(StandardCharsets.UTF_8));
		writer.flush();
		writer.close();

		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuffer response = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		System.out.println(response);

		return connection.getResponseCode() + " - " + connection.getResponseMessage();
	}

}

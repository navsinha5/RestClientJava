package restClient_example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.Response;

public class RestClient {

	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {

	  try {

		URL url = new URL("http://lntwater.us-west-2.elasticbeanstalk.com/auth/login");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"userName\":\"lntuser\",\"password\":\"Test@123\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		/*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/
		
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			System.out.println("succes======================================================");
		}

		/*BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));*/
		
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		//Response resp = mapper.readValue(in, Response.class);
		JsonNode node = mapper.readTree(in);
		System.out.println(node);
		System.out.println(node.get("Success"));
		

		/*String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			ObjectMapper mapper = new ObjectMapper();
			Response resp = mapper.readValue(output, Response.class);
			System.out.println(resp);
		}*/
		
		//System.out.println(resp);

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

	}

}

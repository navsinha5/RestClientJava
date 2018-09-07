package restClient_example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient2 {
	
	public static void main(String[] args) {
		String apiURL = "http://34.205.138.72:8080/com.opera.compute-0.0.1-SNAPSHOT/edge/createLogResultFile";
		String requestFormat = "{\"fileType\":\"%s\", \"fileName\":\"%s\", \"bucketName\":\"%s\", \"path\":\"%s\"}";
		String requestBody = String.format(requestFormat, "tagFile", "TagsReadLog_Edge_2018-03-29.csv", "operawaterlogbucket", "operaWaterEdgeLogFolder/operaWaterTagRecordLogFolder");
		
		try {
			URL url = new URL(apiURL);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			OutputStream output = conn.getOutputStream();
			output.write(requestBody.getBytes());
			output.flush();
			
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("error");
				return;
			}
			
			InputStream in = conn.getInputStream();
			JsonNode node = new ObjectMapper().readTree(in);
			
			if(node.get("status").asText()
					.equals("Success")) {
				System.out.println(node);;
				return;
			}else {
				System.out.println("error while calling the rest api " + node);
				return;
			}
			
		}catch(Exception e) {
			System.out.println("error while calling the rest api " + e);
		}
	}
}

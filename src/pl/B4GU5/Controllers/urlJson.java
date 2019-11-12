package pl.B4GU5.Controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

public class urlJson {
	private static HttpURLConnection con;
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
		  
		  public static JSONObject readJsonFromUrlPost(String url, String urlParameters) throws IOException, JSONException {

		        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		        try {

		            URL myurl = new URL(url);
		            con = (HttpURLConnection) myurl.openConnection();

		            con.setDoOutput(true);
		            con.setRequestMethod("POST");
		            con.setRequestProperty("User-Agent", "Java client");
		            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
		                wr.write(postData);
		            }

		            StringBuilder content;

		            try (BufferedReader in = new BufferedReader(
		                    new InputStreamReader(con.getInputStream()))) {

		                String line;
		                content = new StringBuilder();

		                while ((line = in.readLine()) != null) {
		                    content.append(line);
		                    content.append(System.lineSeparator());
		                }
		            }

				      JSONObject json = new JSONObject(content.toString());
				      return json;

		        } finally {
		            
		            con.disconnect();
		        }
			  }

}

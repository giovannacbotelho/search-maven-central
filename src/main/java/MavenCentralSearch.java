import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MavenCentralSearch {

	public static String getVersionLibraryOnDate(String groupId, String artifactId, Date date) {
		Library lib = new Library(groupId, artifactId);
		return lib.getVersionLibraryOnDate(date);
	}

	public static String montaRequest(Library lib){
		return "/solrsearch/select?q=g:" + lib.groupId +
				"+AND+a:" + lib.artifactId +
				"&rows=200&wt=json&core=gav";

	}

	public static JSONArray getJsonArrayResponseWithRest(Library lib){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpHost target = new HttpHost("search.maven.org", 80, "http");
			HttpGet getRequest = new HttpGet(montaRequest(lib));
			getRequest.addHeader("accept", "application/json");
			HttpResponse httpResponse;
			httpResponse = httpclient.execute(target, getRequest);
			HttpEntity entity = httpResponse.getEntity();
			String json = "";
			if (entity != null) {
				json = EntityUtils.toString(entity);
				System.out.println(json);
			}

			JSONObject jsonObject = new JSONObject(json);
			JSONObject myResponse = jsonObject.getJSONObject("response");
			JSONArray itens = (JSONArray) myResponse.get("docs");

			return itens;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String getDateOfLibraryVersion(String groupId, String artifactId, String versionNumber) {
		Library lib = new Library(groupId, artifactId);
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lib.getDateOfLibraryVersionNumber(versionNumber));
		} catch (Exception e) {
			return "Date Not Found";
		}
	}

	public static String getVersionLibraryOnDate(String groupId, String artifactId, String date) throws ParseException {
		Library lib = new Library(groupId, artifactId);
		try{
			return lib.getVersionLibraryOnDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date));
		}catch(Exception e){
			if(date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4}) ([0-9]{2}):([0-9]{2})")){
				return lib.getVersionLibraryOnDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date + ":00"));
			}
			else{
				return "";
			}
		}
		
	
	}
}

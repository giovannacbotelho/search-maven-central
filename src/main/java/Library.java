import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Library {
	String groupId;
	String artifactId;
	List<Version> versions;

	public Library(String groupId, String artifactId) {
		this.groupId = groupId;
		this.artifactId = artifactId;
	}

	public String getVersionLibraryOnDate(Date date) {
		if(versions == null){
			versions = this.getAllVersions();
		}
		if(versions.isEmpty()){
			return "No version found";
		}
		Version[] versionsArray = new Version[versions.size()];
		versionsArray = versions.toArray(versionsArray); 

		Arrays.sort(versionsArray, new Comparator<Version>() {
			public int compare(Version b1, Version b2) {
				return b1.getDate().compareTo(b2.getDate());
			}
		});

		Version newerVersion = null;
		for(int i = 0; i < versionsArray.length ; i++){
			if(versionsArray[i].getDate().before(date)){
				newerVersion = versionsArray[i];
			}			
		}
		
		if (newerVersion == null)
			return "No version found";
		
		return newerVersion.getVersionNumber();
	}

	public List<Version> getAllVersions(){
		if(versions == null){
			versions = new ArrayList<Version>();
			JSONArray itensJSON = MavenCentralSearch.getJsonArrayResponseWithRest(this);

			for (int i = 0; i < itensJSON.length(); i++) {
				JSONObject libJSON = itensJSON.getJSONObject(i); 	
				String v = libJSON.getString("v");
				Date d = new Date(libJSON.getLong("timestamp"));
				versions.add(new Version(d, v));
			}
		}
		return versions;
	}

	public Date getDateOfLibraryVersionNumber(String versionNumber) {
		if(versions == null){
			versions = this.getAllVersions();
		}
		Version[] versionsArray = new Version[versions.size()];
		versionsArray = versions.toArray(versionsArray); 

		for(int i = 0; i < versionsArray.length ; i++){
			if(versionsArray[i].getVersionNumber().equals(versionNumber)){
				return versionsArray[i].getDate();
			}			
		}
		throw new RuntimeException("There isn't g:"+ groupId + " a:" + artifactId + "in version "+versionNumber + " available in maven central.");
	}
}

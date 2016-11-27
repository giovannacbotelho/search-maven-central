import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ManageCSV {

	private static String[] fieldTitles = {};

	public static void addRelevantData(File file) throws FileNotFoundException, IOException, ParseException {
		List<Map<String, String>> list = readCSV(file);
		for (Map<String, String> line: (ArrayList<Map<String, String>>)list){
			AddedData ad = processLine(line);
			line.put("CURRENT_VERSION_RELEASE", ad.dateOfLibraryVersion);
			line.put("NEWER_VERSION", ad.versionOnDate);
		}
		generateNewCSV(list, file.getName());

	}

	static List<Map<String, String>> readCSV(File file) throws FileNotFoundException, IOException {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		BufferedReader br = new BufferedReader(new FileReader(file.getPath()));

		String line = br.readLine();
		fieldTitles = line.split(",");

		while ((line = br.readLine()) != null) {
			if (!line.trim().isEmpty()) {
				Map<String, String> map = new HashMap<String, String>();
				String[] fields = line.split(",");
				for (int i = 0; i < fields.length; i++) {
					map.put(fieldTitles[i], fields[i]);					
				}
				list.add(map);
			}
		}
		br.close();
		return list;
	}

	public static AddedData processLine(Map<String, String> mapCsvLine) throws ParseException{
		String groupId = mapCsvLine.get("GROUP_ID");
		String artifactId = mapCsvLine.get("ARTIFACT_ID");
		String versionNumber = mapCsvLine.get("VERSION");
		String date = mapCsvLine.get("DATE");

		String versionOnDate = MavenCentralSearch.getVersionLibraryOnDate(groupId, artifactId, date);
		String dateOfLibraryVersion = MavenCentralSearch.getDateOfLibraryVersion(groupId, artifactId, versionNumber);

		AddedData ad = new AddedData(versionOnDate, dateOfLibraryVersion);
		return ad;
	}


	private static void generateNewCSV(List<Map<String, String>> list, String name) throws IOException {
		String outputFile = name.replace(".csv", "Processed.csv");
		CSVPrinter csvFilePrinter = null;
		List<String> stringList = new ArrayList<String>(Arrays.asList(fieldTitles));
		stringList.add("CURRENT_VERSION_RELEASE");
		stringList.add("NEWER_VERSION");


		CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader(stringList.toArray(new String[stringList.size()]));
		FileWriter fileWriter = new FileWriter(outputFile);
		csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

		List<Map<String, String>> parsedList = list;
		List<String> listToPrint = new ArrayList<String>();

		for (int i = 0; i <  parsedList.size() ; i++){
			listToPrint.clear();
			for (int j=0; j < stringList.size() ; j++){
				listToPrint.add((parsedList.get(i)).get(stringList.get(j)));				
			}
			csvFilePrinter.printRecord(listToPrint);
		}

		fileWriter.flush();
		fileWriter.close();
		csvFilePrinter.close();

	}
}

class AddedData{
	String versionOnDate;
	String dateOfLibraryVersion ;

	public AddedData(String versionOnDate, String dateOfLibraryVersion) {
		this.versionOnDate = versionOnDate;
		this.dateOfLibraryVersion = dateOfLibraryVersion;
	}

}

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestReadFile {

	@Test
	public void testReadFileAndAddColumns() throws FileNotFoundException, IOException, ParseException{
		ManageCSV.addRelevantData(new File(getClass().getResource("sampleCSV.csv").getPath()));
		File file = new File("sampleCSVProcessed.csv");
		assertTrue(file.exists());
		List<Map<String, String>> list = ManageCSV.readCSV(file);
		//Typical case
		assertEquals("4.1.1.4",(list.get(0)).get("NEWER_VERSION"));
		assertEquals("24/08/2012 21:15:04",((list.get(0)).get("CURRENT_VERSION_RELEASE")));
		// No Data in Maven Central
		assertEquals("No version found",(list.get(1)).get("NEWER_VERSION"));
		assertEquals("Date Not Found",((list.get(1)).get("CURRENT_VERSION_RELEASE")));
	}

}

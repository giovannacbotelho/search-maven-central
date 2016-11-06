import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

public class TestGetLibraryVersion {

	@Test
	public void testGetVersionOfLibraryOnDate(){
		String version = MavenCentralSearch.getVersionLibraryOnDate("org.apache.logging.log4j", "log4j",new Date());
		assertEquals("2.7", version);
		
		//25-May-2016  2.6
		LocalDate myDate =LocalDate.parse("2016-05-26");
		version = MavenCentralSearch.getVersionLibraryOnDate("org.apache.logging.log4j", "log4j",myDate.toDate());
		assertEquals("2.6", version);
	}

	@Test
	public void testaMontaRequest(){
		Library lib = new Library("org.apache.logging.log4j", "log4j");
		assertEquals("/solrsearch/select?q=g:org.apache.logging.log4j+AND+a:log4j&rows=200&wt=json&core=gav"
				,MavenCentralSearch.montaRequest(lib));
	}

	@Test
	public void testGetAllVersionsOfLibrary(){
		Library lib = new Library("org.apache.logging.log4j", "log4j");
		List<Version> versions = lib.getAllVersions();

		assertEquals("[Sun Oct 02 15:14:02 BRT 2016 2.7, Tue Jul 05 23:36:20 BRT 2016 2.6.2, Sun Jun"
				+ " 05 21:32:24 BRT 2016 2.6.1, Wed May 25 11:53:55 BRT 2016 2.6, Sun Dec 06 22:18:44 BRST 2015 "
				+ "2.5, Thu Oct 08 21:49:52 BRT 2015 2.4.1, Sun Sep 20 13:58:29 BRT 2015 2.4, Sun May 10 18:12:38 "
				+ "BRT 2015 2.3, Sun Feb 22 19:16:57 BRT 2015 2.2, Sun Oct 19 23:38:33 BRST 2014 2.1, Sun Aug 17 "
				+ "02:23:39 BRT 2014 2.0.2, Tue Jul 29 21:53:10 BRT 2014 2.0.1, Sat Jul 12 20:25:54 BRT 2014 2.0,"
				+ " Sat Jun 21 20:30:36 BRT 2014 2.0-rc2, Sun Feb 09 17:03:51 BRST 2014 2.0-rc1, Sat Sep 14 "
				+ "11:16:37 BRT 2013 2.0-beta9, Wed Jul 10 18:14:33 BRT 2013 2.0-beta8, Tue Jun 04 13:12:17 BRT "
				+ "2013 2.0-beta7, Mon May 06 22:58:18 BRT 2013 2.0-beta6, Sat Apr 20 18:20:50 BRT 2013 2.0-beta5,"
				+ " Mon Jan 28 05:45:06 BRST 2013 2.0-beta4, Sun Nov 11 14:22:05 BRST 2012 2.0-beta3, "
				+ "Sun Oct 07 16:09:04 BRT 2012 2.0-beta2, Tue Sep 18 10:58:01 BRT 2012 2.0-beta1, "
				+ "Fri Aug 24 17:40:04 BRT 2012 2.0-alpha2, Sun Jul 29 16:11:41 BRT 2012 2.0-alpha1]", 
				versions.toString());
	}
}

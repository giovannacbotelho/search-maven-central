import java.util.Date;

public class Version {
	Date date;
	String versionNumber;
	public Version(Date d, String number) {
		this.date = d;
		this.versionNumber = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String number) {
		this.versionNumber = number;
	}

	public String toString(){
		return date+ " " + versionNumber;
	}
}

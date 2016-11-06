import java.util.Date;

public class Version {
	Date date;
	String number;
	public Version(Date d, String number) {
		this.date = d;
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String toString(){
		return date+ " " + number;
	}
}

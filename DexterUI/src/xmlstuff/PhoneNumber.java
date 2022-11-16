package xmlstuff;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumber {
	private String type;
	private String number;
	private List<PhoneNumber> phoneNumbers;
	public String getType() {

		return type;

	}

	public void setType(String type) {

		this.type = type;

	}

	public String getNumber() {

		return number;

	}

	public void setNumber(String number) {

		this.number = number;

	}



	public List<PhoneNumber> getPhoneNumbers() {

		return phoneNumbers;

	}

	public List setPhoneNumbers(List<PhoneNumber> phoneNumber) {

		this.phoneNumbers = phoneNumber;
		return phoneNumber;
	}

}

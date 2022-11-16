package xmlstuff;

import java.util.ArrayList;
import java.util.List;

public class MyFriend {

	private String firstName;

	private String lastName;

	private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

	public String getFirstName() {

		return firstName;

	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;

	}

	public String getLastName() {

		return lastName;

	}

	public void setLastName(String lastName) {

		this.lastName = lastName;

	}
	public void getPhoneNumbers() {

		this.phoneNumbers = phoneNumbers;

	}

}

package com.datastax.enablement.core.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.UUID;

public class UserAddressOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Static method to create a user from a Iterator<String> where each string
	 * element is value for the user property in the order of: Unique, FirstName
	 * LastName AddressName Address1 Address2 City State Zip Country
	 * 
	 * @param iterator
	 *            - Iterator<String>
	 * @return
	 */
	public static UserAddressOrg returnAddress(Iterator<String> iterator) {
		UserAddressOrg usrAdd = new UserAddressOrg();
		usrAdd.setUnique(UUID.fromString(iterator.next()));
		usrAdd.setFirstName(iterator.next());
		usrAdd.setLastName(iterator.next());
		usrAdd.setAddressName(iterator.next());
		usrAdd.setAddress1(iterator.next());
		usrAdd.setAddress2(iterator.next());
		usrAdd.setCity(iterator.next());
		usrAdd.setState(iterator.next());
		usrAdd.setZip(iterator.next());
		usrAdd.setCountry(iterator.next());
		return usrAdd;
	}

	private UUID unique;
	private String firstName;
	private String lastName;
	private String addressName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;

	private String country;
	private String address3;
	private String birthday;
	private String midName;
	private String occupation;
	private String ordinal;

	private String title;

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getAddress3() {
		return address3;
	}

	public String getAddressName() {
		return addressName;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMidName() {
		return midName;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public String getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}

	public UUID getUnique() {
		return unique;
	}

	public String getZip() {
		return zip;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public void setAddressName(String address_name) {
		this.addressName = address_name;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUnique(UUID unique) {
		this.unique = unique;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}

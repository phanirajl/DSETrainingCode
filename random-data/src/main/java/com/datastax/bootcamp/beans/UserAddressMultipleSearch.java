package com.datastax.bootcamp.beans;

import java.util.Date;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.generate.test.util.RandDataGenerator;
import com.github.javafaker.Faker;

@Table(name = "user_address_multiple_search")
public class UserAddressMultipleSearch extends UserAddressMultiple {
	private static final long serialVersionUID = 1L;

	@Column(name = "hamlet")
	private String hamletQuote;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "race")
	private String race;

	@Column(name = "marital")
	private String maritalStatus;

	@Column(name = "sex")
	private String sex;

	@Column(name = "geo")
	private String geoLatLong;

	public static UserAddressMultipleSearch generateRandomUser() {
		UserAddressMultipleSearch user = new UserAddressMultipleSearch();
		Faker faker = new Faker();

		user.setUnique(RandDataGenerator.getRandomTimeUUID());
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setMidlName(faker.name().lastName());
		user.setOccupation(RandDataGenerator.getRandomWordFromList(RandDataGenerator.OCCUPATIONS));
		user.setTitle(faker.name().title());
		user.setOrdinal(faker.name().suffix());
		Date bday = RandDataGenerator.getRangedDate();
		LocalDate.fromMillisSinceEpoch(bday.toInstant().toEpochMilli());
		user.setBirthday(LocalDate.fromMillisSinceEpoch(bday.toInstant().toEpochMilli()));
		user.setAddressName(RandDataGenerator.getRandomWordFromList(RandDataGenerator.ADDRESSNAMES));
		user.setAddress1(faker.address().streetAddress());
		user.setAddress2(faker.address().secondaryAddress());
		user.setAddress3(null);
		user.setCity(faker.address().city());
		user.setState(faker.address().stateAbbr());
		user.setCountry(faker.address().countryCode());
		user.setZip(faker.address().zipCode());

		user.setHamletQuote(faker.shakespeare().hamletQuote());
		Double lgtd = new Double(faker.address().longitude());
		user.setLongitude(lgtd);
		Double lttd = new Double(faker.address().latitude());
		user.setLatitude(lttd);
		user.setRace(faker.demographic().race());
		user.setMaritalStatus(faker.demographic().maritalStatus());
		user.setSex(faker.demographic().sex());
		user.setGeoLatLong(lttd + "," + lgtd);

		return user;
	}

	public String getGeoLatLong() {
		return geoLatLong;
	}

	public void setGeoLatLong(String geoLatLong) {
		this.geoLatLong = geoLatLong;
	}

	public String getHamletQuote() {
		return hamletQuote;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getRace() {
		return race;
	}

	public String getSex() {
		return sex;
	}

	public void setHamletQuote(String hamletQuote) {
		this.hamletQuote = hamletQuote;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}

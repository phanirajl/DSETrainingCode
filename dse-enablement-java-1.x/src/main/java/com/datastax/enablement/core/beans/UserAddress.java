
package com.datastax.enablement.core.beans;

import java.util.UUID;

import com.datastax.driver.core.LocalDate;

/**
 * @author matwater
 *
 */
public class UserAddress {
    private UUID uid;
    private String address_name;
    private String address1;
    private String address2;
    private String address3;
    private LocalDate birthday;
    private String city;
    private String country;
    private String firstName;
    private String lastName;
    private String midName;
    private String ordinal;
    private String state;
    private String zip;

    /**
     * Default Constructor
     */
    public UserAddress() {
        super();
    }

    /**
     * @param uid
     * @param address_name
     * @param address1
     * @param address2
     * @param address3
     * @param birthday
     * @param city
     * @param country
     * @param first_name
     * @param last_name
     * @param mid_name
     * @param ordinal
     * @param state
     * @param zip
     */
    public UserAddress(UUID uid, String address_name, String address1, String address2, String address3,
            LocalDate birthday, String city, String country, String firstName, String lastName, String midName,
            String ordinal, String state, String zip) {
        super();
        this.uid = uid;
        this.address_name = address_name;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.birthday = birthday;
        this.city = city;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.ordinal = ordinal;
        this.state = state;
        this.zip = zip;
    }

    public String getAddress_name() {
        return address_name;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public LocalDate getBirthday() {
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

    public String getOrdinal() {
        return ordinal;
    }

    public String getState() {
        return state;
    }

    public UUID getUid() {
        return uid;
    }

    public String getZip() {
        return zip;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
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

    public void setBirthday(LocalDate birthday) {
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

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "UserAddress [uid=" + uid + ", address_name=" + address_name + ", address1=" + address1 + ", address2="
                + address2 + ", address3=" + address3 + ", birthday=" + birthday + ", city=" + city + ", country="
                + country + ", firstName=" + firstName + ", lastName=" + lastName + ", midName=" + midName
                + ", ordinal=" + ordinal + ", state=" + state + ", zip=" + zip + "]";
    }
}

package com.datastax.bootcamp.beans;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.generate.test.util.RandDataGenerator;

@Table(name = "user_address_multiple")
public class UserAddressMultiple implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "unique")
    private UUID unique;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mid_name")
    private String midlName;

    @Column(name = "ordinal")
    private String ordinal;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "title")
    private String title;

    @ClusteringColumn(0)
    @Column(name = "address_name")
    private String addressName;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;

    public UserAddressMultiple() {
        super();
    }

    public static UserAddressMultiple constructFromUserAndAddress(User user, Address address) {
        UserAddressMultiple multi = new UserAddressMultiple();
        multi.setUnique(user.getUnique());
        multi.setFirstName(user.getFirstName());
        multi.setLastName(user.getLastName());
        multi.setMidlName(user.getMidlName());
        multi.setOccupation(user.getOccupation());
        multi.setTitle(user.getTitle());
        multi.setOrdinal(user.getOrdinal());
        multi.setBirthday(user.getBirthday());
        multi.setAddressName(RandDataGenerator.getRandomWordFromList(RandDataGenerator.ADDRESSNAMES));
        multi.setAddress1(address.getAddress1());
        multi.setAddress2(address.getAddress2());
        multi.setAddress3(address.getAddress3());
        multi.setCity(address.getCity());
        multi.setState(address.getState());
        multi.setCountry(address.getCountry());
        multi.setZip(address.getZip());
        return multi;
    }

    public UUID getUnique() {
        return unique;
    }

    public void setUnique(UUID unique) {
        this.unique = unique;
    }

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

    public String getMidlName() {
        return midlName;
    }

    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

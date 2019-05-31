package com.datastax.enablement.core.mapper.beans;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;

/**
 * @author matwater
 *
 *         Creating this pojo as a mapped classes using the dse mapper api. So
 *         we will start with the table annotation to map this object as a whole
 *         to a table in dse
 */

// the only required element is the name as the keyspace can be associated with 
// the session.  Being able to set the consistency level per pojo is nice that 
// way it is object based and how you handle the object instead of setting in 
// each statement where you would use an object like we did with the statements 
// (or accepted default)
@Table(name = "user_address_multiple",
        keyspace = "enablement",
        readConsistency = "LOCAL_ONE",
        writeConsistency = "LOCAL_QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
public class UserAddressBean implements Serializable {
    private static final long serialVersionUID = 7704367984403367811L;

    @PartitionKey(value = 0)
    @Column(name = "uid")
    private UUID uid;

    @ClusteringColumn(value = 0)
    @Column(name = "address_name")
    private String addressName;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mid_name")
    private String midName;

    @Column(name = "ordinal")
    private String ordinal;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Transient
    private String fullName;

    /**
     * 
     */
    public UserAddressBean() {
        super();
    }

    /**
     * @param uid
     * @param addressName
     * @param address1
     * @param address2
     * @param address3
     * @param birthday
     * @param city
     * @param country
     * @param firstName
     * @param lastName
     * @param midName
     * @param ordinal
     * @param state
     * @param zip
     */
    public UserAddressBean(UUID uid, String addressName, String address1, String address2, String address3,
            LocalDate birthday, String city, String country, String firstName, String lastName, String midName,
            String ordinal, String state, String zip) {
        super();
        this.uid = uid;
        this.addressName = addressName;
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

    public String getFullName() {
        return getFirstName() + " " + getMidName() + " " + getLastName();
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

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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
        return "UserAddressBean [uid=" + uid + ", addressName=" + addressName + ", address1=" + address1 + ", address2="
                + address2 + ", address3=" + address3 + ", birthday=" + birthday + ", city=" + city + ", country="
                + country + ", firstName=" + firstName + ", lastName=" + lastName + ", midName=" + midName
                + ", ordinal=" + ordinal + ", state=" + state + ", zip=" + zip + ", fullName=" + fullName
                + ", getAddressName()=" + getAddressName() + ", getAddress1()=" + getAddress1() + ", getAddress2()="
                + getAddress2() + ", getAddress3()=" + getAddress3() + ", getBirthday()=" + getBirthday()
                + ", getCity()=" + getCity() + ", getCountry()=" + getCountry() + ", getFirstName()=" + getFirstName()
                + ", getFullName()=" + getFullName() + ", getLastName()=" + getLastName() + ", getMidName()="
                + getMidName() + ", getOrdinal()=" + getOrdinal() + ", getState()=" + getState() + ", getUid()="
                + getUid() + ", getZip()=" + getZip() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

}

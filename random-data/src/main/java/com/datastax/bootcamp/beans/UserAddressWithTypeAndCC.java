package com.datastax.bootcamp.beans;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "user_address_with_type_and_cc")
public class UserAddressWithTypeAndCC implements Serializable {

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

    @Column(name = "address")
    private AdressUserDefinedType address;

    public UserAddressWithTypeAndCC() {
        super();
    }

    public static UserAddressWithTypeAndCC constructFromUserAndAddress(UserAddressMultiple user) {
        UserAddressWithTypeAndCC userWithType = new UserAddressWithTypeAndCC();
        AdressUserDefinedType addressType = new AdressUserDefinedType();
        userWithType.setUnique(user.getUnique());
        userWithType.setFirstName(user.getFirstName());
        userWithType.setLastName(user.getLastName());
        userWithType.setMidlName(user.getMidlName());
        userWithType.setOccupation(user.getOccupation());
        userWithType.setTitle(user.getTitle());
        userWithType.setOrdinal(user.getOrdinal());
        userWithType.setBirthday(user.getBirthday());
        userWithType.setAddressName(user.getAddressName());
        
        // put the type together
        addressType.setAddress1(user.getAddress1());
        addressType.setAddress2(user.getAddress2());
        addressType.setAddress3(user.getAddress3());
        addressType.setCity(user.getCity());
        addressType.setState(user.getState());
        addressType.setCountry(user.getCountry());
        addressType.setZip(user.getZip());
        
        userWithType.setAddress(addressType);
        return userWithType;
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

    public AdressUserDefinedType getAddress() {
        return address;
    }

    public void setAddress(AdressUserDefinedType address) {
        this.address = address;
    }

}

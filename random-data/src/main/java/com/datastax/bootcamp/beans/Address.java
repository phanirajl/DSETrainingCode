package com.datastax.bootcamp.beans;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.generate.test.util.RandDataGenerator;

@Table(name = "address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "unique")
    private UUID unique;

    @Column(name = "rdb_adr_seq_num")
    private Integer relAddrSeq;

    @Column(name = "rdb_usr_seq_num")
    private Integer relUserSeq;

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

    public Address() {
        super();
    }

    public static Address randomAddressForUser(Integer userSeqNum) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        DataFactory df = new DataFactory();
        Address address = new Address();
        address.setUnique(RandDataGenerator.getRandomTimeUUID());
        address.setRelUserSeq(userSeqNum);
        address.setRelAddrSeq(rand.nextInt(0, 100000));
        address.setAddress1(df.getAddress());
        address.setAddress2(df.getAddressLine2(50));
        address.setAddress3(df.getAddressLine2(10));
        address.setCity(df.getCity());
        address.setState(RandDataGenerator.getRandomWordFromList(RandDataGenerator.USSTATES));
        address.setCountry("USA");
        address.setZip(new Integer(rand.nextInt(10000, 99999)).toString());
        return address;
    }

    public UUID getUnique() {
        return unique;
    }

    public void setUnique(UUID unique) {
        this.unique = unique;
    }

    public Integer getRelAddrSeq() {
        return relAddrSeq;
    }

    public void setRelAddrSeq(Integer relAddrSeq) {
        this.relAddrSeq = relAddrSeq;
    }

    public Integer getRelUserSeq() {
        return relUserSeq;
    }

    public void setRelUserSeq(Integer relUserSeq) {
        this.relUserSeq = relUserSeq;
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

package com.datastax.bootcamp.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.generate.test.util.RandDataGenerator;

@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "unique")
    private UUID unique;
    
    @Column(name = "rdb_seq_num")
    private Integer relSeqNum;
    
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

    
    public User() {
        super();
    }

    public static User randomUser() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        DataFactory df = new DataFactory();
        User user = new User();
        user.setUnique(RandDataGenerator.getRandomTimeUUID());
        user.setRelSeqNum(rand.nextInt(0, 100000));
        user.setFirstName(df.getFirstName());
        user.setLastName(df.getLastName());
        user.setMidlName(df.getFirstName());
        user.setOrdinal(RandDataGenerator.getRandomWordFromList(RandDataGenerator.ORDINAL));
        Date bday = RandDataGenerator.getRangedDate();
        LocalDate.fromMillisSinceEpoch(bday.toInstant().toEpochMilli());
        user.setBirthday(LocalDate.fromMillisSinceEpoch(bday.toInstant().toEpochMilli()));
        user.setOccupation(RandDataGenerator.getRandomWordFromList(RandDataGenerator.OCCUPATIONS));
        user.setTitle(RandDataGenerator.getRandomWordFromList(RandDataGenerator.TITLES));
        return user;
    }
    
    public UUID getUnique() {
        return unique;
    }

    public void setUnique(UUID unique) {
        this.unique = unique;
    }

    public Integer getRelSeqNum() {
        return relSeqNum;
    }

    public void setRelSeqNum(Integer relSeqNum) {
        this.relSeqNum = relSeqNum;
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

}

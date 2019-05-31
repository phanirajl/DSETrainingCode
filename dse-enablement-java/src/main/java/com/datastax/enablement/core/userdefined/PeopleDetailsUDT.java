
package com.datastax.enablement.core.userdefined;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

/**
 * @author matwater
 *
 */

// can have some of the optional parameters that @Table has, but not all as
// not everything applies such as consistency level
@UDT(keyspace = "enablement",
        name = "people_details_udt")
public class PeopleDetailsUDT implements Serializable {
    private static final long serialVersionUID = 8128839808006179021L;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "age")
    private Integer age;

    @Field(name = "alive")
    private Boolean alive;

    /**
     *
     */
    public PeopleDetailsUDT() {
        super();
    }

    /**
     * @param firstName
     * @param lastName
     * @param age
     * @param alive
     */
    public PeopleDetailsUDT(String firstName, String lastName, Integer age, Boolean alive) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.alive = alive;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getAlive() {
        return alive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PeopleDetailsUDT [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", alive="
                + alive + "]";
    }

}

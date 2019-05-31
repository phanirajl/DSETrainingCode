package com.datastax.enablement.core.userdefined;

import java.io.Serializable;
import java.util.Map;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * @author matwater
 *
 */
// just a few of the parameters, will use the default settings for the rest
@Table(name = "people",
        keyspace = "enablement")
public class People implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "id")
    private Integer id;

    @Column(name = "user")
    private PeopleDetailsUDT user;

    // specifying the UDT is Frozen just like the table def
    @Column(name = "children")
    @FrozenValue
    private Map<String, PeopleDetailsUDT> children;

    /**
     *
     */
    public People() {
        super();
    }

    /**
     * @param id
     * @param user
     * @param children
     */
    public People(Integer id, PeopleDetailsUDT user, Map<String, PeopleDetailsUDT> children) {
        super();
        this.id = id;
        this.user = user;
        this.children = children;
    }

    public Map<String, PeopleDetailsUDT> getChildren() {
        return children;
    }

    public Integer getId() {
        return id;
    }

    public PeopleDetailsUDT getUser() {
        return user;
    }

    public void setChildren(Map<String, PeopleDetailsUDT> children) {
        this.children = children;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(PeopleDetailsUDT user) {
        this.user = user;
    }

}

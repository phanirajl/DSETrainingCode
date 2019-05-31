package com.datastax.generate.test.tables;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "test")
public class TestMapper implements Serializable {
    private static final long   serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "id")
    private UUID                id;

    @PartitionKey(1)
    @Column(name = "pk2")
    private String              pk2;

    @ClusteringColumn(0)
    @Column(name = "cc1")
    private String              cc1;

    @ClusteringColumn(1)
    @Column(name = "cc2")
    private String              cc2;

    @Column(name = "rand_int")
    private Integer             randInt;

    @Column(name = "rand_list")
    private List<String>        randList;

    @Column(name = "rand_map")
    private Map<String, String> randMap;

    @Column(name = "rand_set")
    private Set<String>         randSet;

    @Column(name = "rand_text")
    private String              randText;

    @Column(name = "rand_time")
    private Date                randTime;

    @Column(name = "solr_query")
    private String              solrQuery;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPk2() {
        return pk2;
    }

    public void setPk2(String pk2) {
        this.pk2 = pk2;
    }

    public String getCc1() {
        return cc1;
    }

    public void setCc1(String cc1) {
        this.cc1 = cc1;
    }

    public String getCc2() {
        return cc2;
    }

    public void setCc2(String cc2) {
        this.cc2 = cc2;
    }

    public Integer getRandInt() {
        return randInt;
    }

    public void setRandInt(Integer randInt) {
        this.randInt = randInt;
    }

    public List<String> getRandList() {
        return randList;
    }

    public void setRandList(List<String> randList) {
        this.randList = randList;
    }

    public Map<String, String> getRandMap() {
        return randMap;
    }

    public void setRandMap(Map<String, String> randMap) {
        this.randMap = randMap;
    }

    public Set<String> getRandSet() {
        return randSet;
    }

    public void setRandSet(Set<String> randSet) {
        this.randSet = randSet;
    }

    public String getRandText() {
        return randText;
    }

    public void setRandText(String randText) {
        this.randText = randText;
    }

    public Date getRandTime() {
        return randTime;
    }

    public void setRandTime(Date randTime) {
        this.randTime = randTime;
    }

    public String getSolrQuery() {
        return solrQuery;
    }

    public void setSolrQuery(String solrQuery) {
        this.solrQuery = solrQuery;
    }

    @Override
    public String toString() {
        return "INSERT INTO primary_key_example (id, pk2, cc1, cc2, randInt, randList, randMap, randSet, randText, randTime ) "
                + "VALUES ("+ id  +", '" + pk2  + "', '"+ cc1  + "', '" + cc2 + "', " + randInt
                + ", " + randList + ", " + randMap + ", " + randSet + ", '" + randText
                + "', '" + randTime + "');";
    }

    
}

package com.datastax.generate.test.tables;

import java.io.Serializable;
import java.util.Date;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "test_no_collection")
public class TestNoCollectionMapper implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "id")
    private UUID              id;

    @PartitionKey(1)
    @Column(name = "pk2")
    private String            pk2;

    @ClusteringColumn(0)
    @Column(name = "cc1")
    private String            cc1;

    @ClusteringColumn(1)
    @Column(name = "cc2")
    private String            cc2;

    @Column(name = "rand_text")
    private String            randText;

    @Column(name = "rand_time")
    private Date              randTime;

    @Column(name = "solr_query")
    private String            solrQuery;

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

}

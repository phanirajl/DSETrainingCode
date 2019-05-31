package com.datastax.enablement.core.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * @author matwater
 *
 */
@Table(name = "collections",
        keyspace = "enablement")
public class CollectionDemoData implements Serializable {

    private static final long serialVersionUID = -1558113141474899473L;

    @PartitionKey()
    @Column(name = "id")
    private int id;

    @Column(name = "set_data")
    private Set<String> setData = new TreeSet<String>();

    @Column(name = "list_data")
    private List<String> listData = new ArrayList<String>();

    @Column(name = "map_data")
    private Map<String, String> mapData = new HashMap<String, String>();

    /**
     *
     */
    public CollectionDemoData() {
        super();
    }

    /**
     * @param id
     * @param setData
     */
    public CollectionDemoData(int id, Set<String> setData) {
        super();
        this.id = id;
        this.setData = setData;
    }

    /**
     * @param id
     * @param listData
     */
    public CollectionDemoData(int id, List<String> listData) {
        super();
        this.id = id;
        this.listData = listData;
    }

    /**
     * @param id
     * @param mapData
     */
    public CollectionDemoData(int id, Map<String, String> mapData) {
        super();
        this.id = id;
        this.mapData = mapData;
    }

    /**
     * @param id
     * @param setData
     * @param listData
     * @param mapData
     */
    public CollectionDemoData(int id, Set<String> setData, List<String> listData, Map<String, String> mapData) {
        super();
        this.id = id;
        this.setData = setData;
        this.listData = listData;
        this.mapData = mapData;
    }

    public int getId() {
        return id;
    }

    public List<String> getListData() {
        return listData;
    }

    public Map<String, String> getMapData() {
        return mapData;
    }

    public Set<String> getSetData() {
        return setData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListData(List<String> listData) {
        this.listData = listData;
    }

    public void setMapData(Map<String, String> mapData) {
        this.mapData = mapData;
    }

    public void setSetData(Set<String> setData) {
        this.setData = setData;
    }

    @Override
    public String toString() {
        return "CollectionDemoData [id=" + id + ", setData=" + setData + ", listData=" + listData + ", mapData="
                + mapData + "]";
    }

}

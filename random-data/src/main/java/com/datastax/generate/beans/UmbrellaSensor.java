package com.datastax.generate.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_data")
public class UmbrellaSensor implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey(0)
    @Column(name = "serial_number")
    private UUID serialNum;

    @PartitionKey(1)
    @Column(name = "year")
    private Integer year;

    @PartitionKey(2)
    @Column(name = "week")
    private Integer week;

    @PartitionKey(3)
    @Column(name = "day")
    private Integer day;

    @ClusteringColumn(0)
    @Column(name = "sensor_snapshot")
    private Date sensorMetricTime;

    // wide partition data
    @Column(name = "movement")
    private Integer movement;

    // wide partition data
    @Column(name = "temperature")
    private Double temperature;

    // wide partition data
    @Column(name = "humidity")
    private Double humidity;

    // static data
    @Column(name = "vendor")
    private String vendor;

    @Column(name = "manuf_date")
    private Date manufactureDate;

    @Column(name = "deploy_date")
    private Date deploymentDate;

    @Column(name = "main_date")
    private Date lastMainDate;

    @Column(name = "retired")
    private Boolean retired;

    @Column(name = "retire_date")
    private Date retiredDate;

    @Column(name = "sensor_type")
    private String sensorType;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "wing")
    private String wing;

    @Column(name = "hive_num")
    private Integer hiveNumber;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "main_history")
    private List<String> maintainHist;

    public Integer getDay() {
        return day;
    }

    public Date getDeploymentDate() {
        return deploymentDate;
    }

    public Integer getFloor() {
        return floor;
    }

    public Integer getHiveNumber() {
        return hiveNumber;
    }

    public Double getHumidity() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(humidity));
    }

    public Date getLastMainDate() {
        return lastMainDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        DecimalFormat df = new DecimalFormat("#.####");
        return Double.valueOf(df.format(longitude));
    }

    public List<String> getMaintainHist() {
        return maintainHist;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public Integer getMovement() {
        return movement;
    }

    public Boolean getRetired() {
        return retired;
    }

    public Date getRetiredDate() {
        return retiredDate;
    }

    public Date getSensorMetricTime() {
        return sensorMetricTime;
    }

    public String getSensorType() {
        return sensorType;
    }

    public UUID getSerialNum() {
        return serialNum;
    }

    public Double getTemperature() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(temperature));
    }

    public String getVendor() {
        return vendor;
    }

    public Integer getWeek() {
        return week;
    }

    public String getWing() {
        return wing;
    }

    public Integer getYear() {
        return year;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setDeploymentDate(Date deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setHiveNumber(Integer hiveNumber) {
        this.hiveNumber = hiveNumber;
    }

    public void setHumidity(Double humidity) {
        if (humidity > 100) {
            this.humidity = 100.00;
        } else if (humidity < 0.00) {
            this.humidity = 0.00;
        } else {
            this.humidity = humidity;
        }
    }

    public void setLastMainDate(Date lastMainDate) {
        this.lastMainDate = lastMainDate;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setMaintainHist(List<String> maintainHist) {
        this.maintainHist = maintainHist;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public void setMovement(Integer movement) {
        this.movement = movement;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public void setRetiredDate(Date retiredDate) {
        this.retiredDate = retiredDate;
    }

    public void setSensorMetricTime(Date sensorMetricTime) {
        this.sensorMetricTime = sensorMetricTime;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setSerialNum(UUID serialNum) {
        this.serialNum = serialNum;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

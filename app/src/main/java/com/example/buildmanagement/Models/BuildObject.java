package com.example.buildmanagement.Models;

import java.math.BigDecimal;
import java.util.Calendar;

public class BuildObject {
    int id;
    Calendar startDate;
    Calendar endDate;
    String status;
    String objectName;
    String objectCategory;
    BigDecimal price;
    String phone;
    int indexRegion;
    String region;
    String city;
    String street;
    String home;
    Integer flat;
    Calendar finishDate;

    int clientId;
    int foremanId;

    public BuildObject(Calendar startDate, Calendar endDate, String status, String objectName,
                       String objectCategory, BigDecimal price, String phone, int indexRegion,
                       String region, String city, String street, String home, Integer flat,
                       Calendar finishDate, int clientId, int foremanId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.objectName = objectName;
        this.objectCategory = objectCategory;
        this.price = price;
        this.phone = phone;
        this.indexRegion = indexRegion;
        this.region = region;
        this.city = city;
        this.street = street;
        this.home = home;
        this.flat = flat;
        this.finishDate = finishDate;
        this.clientId = clientId;
        this.foremanId = foremanId;
    }

    public BuildObject(int id, Calendar startDate, Calendar endDate, String status, String objectName,
                       String objectCategory, BigDecimal price, String phone, Integer indexRegion, String region, String city,
                           String street, String home, Integer flat, Calendar finishDate, int clientId, int foremanId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.objectName = objectName;
        this.objectCategory = objectCategory;
        this.price = price;
        this.phone = phone;
        this.indexRegion = indexRegion;
        this.region = region;
        this.city = city;
        this.street = street;
        this.home = home;
        this.flat = flat;
        this.finishDate = finishDate;
        this.foremanId = foremanId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIndexRegion() {
        return indexRegion;
    }

    public void setIndexRegion(int indexRegion) {
        this.indexRegion = indexRegion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getForemanId() {
        return foremanId;
    }

    public void setForemanId(int foremanId) {
        this.foremanId = foremanId;
    }
}

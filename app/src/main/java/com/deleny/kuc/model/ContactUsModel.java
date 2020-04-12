package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactUs")
public class ContactUsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ColumnInfo(name = "record_id")
    @SerializedName("RecoredID")
    @Expose
    private Integer recoredID;

    @ColumnInfo(name = "record_version")
    @SerializedName("RecordVersion")
    @Expose
    private Integer recordVersion;

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    private String address;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    @Expose
    private String lat;

    @ColumnInfo(name = "long")
    @SerializedName("Long")
    @Expose
    private String _long;

    @ColumnInfo(name = "mobile")
    @SerializedName("Mobile")
    @Expose
    private String mobile;

    @ColumnInfo(name = "phone")
    @SerializedName("Phone")
    @Expose
    private String phone;

    @ColumnInfo(name = "website")
    @SerializedName("Website")
    @Expose
    private String website;

    public Integer getRecoredID() {
        return recoredID;
    }

    public void setRecoredID(Integer recoredID) {
        this.recoredID = recoredID;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}

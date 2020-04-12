package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class ServicesModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "service_id")
    @SerializedName("ServiceID")
    @Expose
    private Integer serviceID;

    @ColumnInfo(name = "service_title")
    @SerializedName("Title")
    @Expose
    private String title;

    @ColumnInfo(name = "service_description")
    @SerializedName("Description")
    @Expose
    private String description;

    @ColumnInfo(name = "service_image")
    @SerializedName("Image")
    @Expose
    private String image;

    @ColumnInfo(name = "service_image64")
    @SerializedName("Image64")
    @Expose
    private String image64;

    @ColumnInfo(name = "service_recordVersion")
    @SerializedName("RecordVersion")
    @Expose
    private Integer recordVersion;

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}

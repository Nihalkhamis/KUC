package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contracts")
public class ContractsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "contracts_id")
    @SerializedName("RecordID")
    @Expose
    private Integer recordID;

    @ColumnInfo(name = "services_title")
    @SerializedName("Title")
    @Expose
    private String title;

    @ColumnInfo(name = "contracts_image")
    @SerializedName("Image64")
    @Expose
    private String image64;

    @ColumnInfo(name = "contracts_recordVersion")
    @SerializedName("RecordVersion")
    @Expose
    private Integer recordVersion;

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

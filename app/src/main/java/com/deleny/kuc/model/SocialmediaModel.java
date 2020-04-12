package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "socialMedia")
public class SocialmediaModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "target_link")
    @SerializedName("TargetLink")
    @Expose
    private String targetLink;

    @ColumnInfo(name = "icon")
    @SerializedName("Icon")
    @Expose
    private String icon;

    @ColumnInfo(name = "record_version")
    @SerializedName("RecordVersion")
    @Expose
    private Integer recordVersion;



    public String getTargetLink() {
        return targetLink;
    }

    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}

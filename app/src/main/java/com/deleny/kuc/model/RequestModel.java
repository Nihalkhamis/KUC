package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("RequestID")
    @Expose
    private Integer requestID;
    @SerializedName("RequestDate")
    @Expose
    private String requestDate;
    @SerializedName("DrName")
    @Expose
    private String drName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

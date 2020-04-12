package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingRequestModel {
    @SerializedName("pendingRequest")
    @Expose
    private String pendingRequest;

    public String getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(String pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

}

package com.deleny.kuc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "blogs")
public class NewsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "blog_id")
    @SerializedName("BlogID")
    @Expose
    private int blogID;

    @ColumnInfo(name = "categoryName")
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;

    @ColumnInfo(name = "blog_title")
    @SerializedName("BlogTitle")
    @Expose
    private String blogTitle;

    @ColumnInfo(name = "blog_body")
    @SerializedName("BlogBody")
    @Expose
    private String blogBody;

    @ColumnInfo(name = "blog_description")
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;

    @ColumnInfo(name = "blog_image")
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    @ColumnInfo(name = "blog_date")
    @SerializedName("PublishDate")
    @Expose
    private String publishDate;

//    @ColumnInfo(name = "blog_viewers")
//    @SerializedName("ViewCount")
//    @Expose
//    private int viewCount;

    @ColumnInfo(name = "blog_image64")
    @SerializedName("Image64")
    @Expose
    private String image64;

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogBody() {
        return blogBody;
    }

    public void setBlogBody(String blogBody) {
        this.blogBody = blogBody;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

//    public int getViewCount() {
//        return viewCount;
//    }
//
//    public void setViewCount(int viewCount) {
//        this.viewCount = viewCount;
//    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }
}

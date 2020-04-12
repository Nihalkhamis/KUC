package com.deleny.kuc.service;

import com.deleny.kuc.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface blogDao {
    @Insert
    public void addBlog(NewsModel newsModel);

    @Query("select * from blogs")
    public List<NewsModel> getBlogs();

    @Query("DELETE FROM blogs")
    void deleteAllBlogs();


}

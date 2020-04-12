package com.deleny.kuc.service;

import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.ServicesModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface aboutUsDao {
    @Insert
    public void addAboutUs(AboutUsModel aboutUsModel);

    @Query("select * from aboutUs")
    public List<AboutUsModel> getAboutUs();

    @Query("DELETE FROM aboutUs")
    void deleteAllAboutUs();
}

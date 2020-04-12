package com.deleny.kuc.service;


import com.deleny.kuc.model.NewsModel;
import com.deleny.kuc.model.ServicesModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface serviceDao {
    @Insert
    public void addService(ServicesModel servicesModel);

    @Query("select * from services")
    public List<ServicesModel> getServices();

    @Query("DELETE FROM services")
    void deleteAllServices();
}

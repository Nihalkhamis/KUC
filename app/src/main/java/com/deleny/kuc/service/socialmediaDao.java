package com.deleny.kuc.service;

import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.SocialmediaModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface socialmediaDao {
    @Insert
    public void addSocialmedia(SocialmediaModel socialmediaModel);

    @Query("select * from socialMedia")
    public List<SocialmediaModel> getSocialMedia();

    @Query("DELETE FROM socialMedia")
    void deleteAllSocialMedia();
}

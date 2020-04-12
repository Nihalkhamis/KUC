package com.deleny.kuc.service;

import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.ContactUsModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface contactUsDao {
    @Insert
    public void addContactUs(ContactUsModel contactUsModel);

    @Query("select * from contactUs")
    public List<ContactUsModel> getContactUs();

    @Query("DELETE FROM contactUs")
    void deleteAllContactUs();
}

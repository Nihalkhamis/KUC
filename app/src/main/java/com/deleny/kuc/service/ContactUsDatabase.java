package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.ContactUsModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactUsModel.class},version = 1, exportSchema = false)
public abstract class ContactUsDatabase extends RoomDatabase {
    private static ContactUsDatabase instance;

    public abstract contactUsDao contactUsDao();

    public static ContactUsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactUsDatabase.class, "contactUs_database").allowMainThreadQueries()
                    // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

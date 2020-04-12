package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.ServicesModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AboutUsModel.class},version = 1, exportSchema = false)
public abstract class AboutUsDatabase extends RoomDatabase {
    private static AboutUsDatabase instance;

    public abstract aboutUsDao aboutUsDao();

    public static AboutUsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AboutUsDatabase.class, "aboutUs_database").allowMainThreadQueries()
                    // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}


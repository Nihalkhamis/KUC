package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.NewsModel;
import com.deleny.kuc.model.ServicesModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {ServicesModel.class},version = 1, exportSchema = false)
public abstract class ServiceDatabase extends RoomDatabase {

    private static ServiceDatabase instance;

    public abstract serviceDao serviceDao();

    public static ServiceDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ServiceDatabase.class, "services_database").allowMainThreadQueries()
                    // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

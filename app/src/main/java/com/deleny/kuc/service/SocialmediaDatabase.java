package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.SocialmediaModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SocialmediaModel.class},version = 1, exportSchema = false)
public abstract class SocialmediaDatabase extends RoomDatabase {
    private static SocialmediaDatabase instance;

    public abstract socialmediaDao socialmediaDao();

    public static SocialmediaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SocialmediaDatabase.class, "socialMedia_database").allowMainThreadQueries()
                    // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

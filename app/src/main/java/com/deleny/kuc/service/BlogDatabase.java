package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.NewsModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsModel.class},version = 1, exportSchema = false)
public abstract class BlogDatabase extends RoomDatabase {

    private static BlogDatabase instance;

    public abstract blogDao blogDao();

    public static BlogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BlogDatabase.class, "blogs_database").allowMainThreadQueries()
                   // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

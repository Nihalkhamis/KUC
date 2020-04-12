package com.deleny.kuc.service;

import android.content.Context;

import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.ServicesModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContractsModel.class},version = 1, exportSchema = false)
public abstract class ContractDatabase extends RoomDatabase {
    private static ContractDatabase instance;

    public abstract contractsDao contractsDao();

    public static ContractDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContractDatabase.class, "contracts_database").allowMainThreadQueries()
                    // .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

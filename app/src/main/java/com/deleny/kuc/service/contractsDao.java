package com.deleny.kuc.service;

import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.ServicesModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface contractsDao {
    @Insert
    public void addContract(ContractsModel contractsModel);

    @Query("select * from contracts")
    public List<ContractsModel> getContracts();

    @Query("DELETE FROM contracts")
    void deleteAllContracts();
}

package com.deleny.kuc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.deleny.kuc.adapter.ContractsAdapter;
import com.deleny.kuc.adapter.ServicesAdapter;
import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.VersionModel;
import com.deleny.kuc.service.ContractDatabase;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractsActivity extends AppCompatActivity {

    @BindView(R.id.aboutUsTV)
    TextView aboutUsTV;
    @BindView(R.id.ContractsRV)
    RecyclerView ContractsRV;

    ContractsAdapter contractsAdapter;
    List<ContractsModel> contractsModels;
    LinearLayoutManager linearLayoutManager;

    ProgressDialog dialog;

    ContractDatabase database;

    boolean connected = false;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        ButterKnife.bind(this);

        if (!Hawk.isBuilt()){
            Hawk.init(this).build();
        }

        aboutUsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        contractsModels = new ArrayList<>();

        database = ContractDatabase.getInstance(this);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        ContractsRV.setLayoutManager(linearLayoutManager);

        contractsAdapter = new ContractsAdapter(this, contractsModels, new ContractsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

            }
        });

        ContractsRV.setAdapter(contractsAdapter);

        if (Hawk.get("LastContractVersion") == null) {
            Hawk.put("LastContractVersion", 0);
        }


        if (Hawk.get("LastContractVersion").equals(0)){
            Log.d("TTT", "onCreate: "+"Last Contract Version equal to 0");
            //call api for saving data in room database and check the lastServiceVersion and update it here with the new value and show value
            getContracts();
        }
        else {
            Log.d("TTT", "onCreate: " + "Last Contract Version not equal to 0");

            //check for internet connection
            //if user offline then retrieve data from room DB
            //if user online will call api that retrieve lastServiceVersion and compare it with the one in Hawk
            //if it's the same number then there is no update and will retrieve data from room DB
            //if it's not the same number then there is an update then i'll delete data in DB and call service api and save it in DB and show data


            //check for the internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);

                getLastVersion(5);


            } else {
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);
                getContractsFromDB();

            }

        }


    }

    private void getContractsFromDB() {
        connected = false;
        //contractsModels.clear();
        contractsModels.addAll(database.contractsDao().getContracts());

        contractsAdapter.notifyDataSetChanged();
    }

    private void getLastVersion(int i) {
        dialog.show();
        connectionService.getLastVersion(i).enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                dialog.dismiss();
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()){
                    Log.d("TTT", "onResponse: "+response.body().getVersion());

                    if (Hawk.get("LastContractVersion").equals(response.body().getVersion())){
                        getContractsFromDB();
                    }
                    else {
                        getContracts();
                    }


                }
                else {
                    getContractsFromDB();
                }
            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }

    private void getContracts() {
        dialog.show();
        connectionService.getContracts().enqueue(new Callback<ArrayList<ContractsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ContractsModel>> call, Response<ArrayList<ContractsModel>> response) {
                dialog.dismiss();
                Log.d("TTT", "onResponse: "+call.request().url());
                Log.d("TTT", "onResponse: "+response.code());
                if (response.isSuccessful()){
                    Log.d("TTT", "onResponse: "+response.body().size());

                    contractsModels.addAll(response.body());

                    //save data in roomDB
                    database.contractsDao().deleteAllContracts();

                    for (int i = 0; i < contractsModels.size(); i++) {
                        database.contractsDao().addContract(contractsModels.get(i));
                    }

                    Hawk.put("LastContractVersion",contractsModels.get(contractsModels.size()-1).getRecordVersion());
                    Log.d("TTT", "onResponse: " + Hawk.get("LastContractVersion"));

                    contractsAdapter.notifyDataSetChanged();

                }
                else {
                    getContractsFromDB();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContractsModel>> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
}
}

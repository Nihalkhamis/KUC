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

import com.deleny.kuc.adapter.CategoryAdapter;
import com.deleny.kuc.adapter.NewsAdapter;
import com.deleny.kuc.adapter.ServicesAdapter;
import com.deleny.kuc.model.CategoryModel;
import com.deleny.kuc.model.ServicesModel;
import com.deleny.kuc.model.VersionModel;
import com.deleny.kuc.service.BlogDatabase;
import com.deleny.kuc.service.ServiceDatabase;
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

public class ServicesActivity extends AppCompatActivity {

    ServicesAdapter servicesAdapter;
    List<ServicesModel> servicesModels;
    GridLayoutManager gridLayoutManager;


    @BindView(R.id.servicesTV)
    TextView servicesTV;
    @BindView(R.id.servicesRV)
    RecyclerView servicesRV;

    ServiceDatabase database;

    ProgressDialog dialog;

    boolean connected = false;


    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);

        if (!Hawk.isBuilt()){
            Hawk.init(this).build();
        }
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        servicesModels = new ArrayList<>();


        database = ServiceDatabase.getInstance(this);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(this, 3);
        }
        else{
            gridLayoutManager = new GridLayoutManager(this, 2);
        }


        servicesRV.setLayoutManager(gridLayoutManager);

        servicesAdapter = new ServicesAdapter(this, servicesModels,  new ServicesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

                Intent intent = new Intent(ServicesActivity.this,OneServiceActivity.class);
                intent.putExtra("ServicePhoto", servicesModels.get(position).getImage());
                intent.putExtra("ServiceTitle", servicesModels.get(position).getTitle());
                intent.putExtra("ServicesDescription", servicesModels.get(position).getDescription());
                startActivity(intent);

            }
        });

        servicesRV.setAdapter(servicesAdapter);




        if (Hawk.get("LastServiceVersion") == null) {
            Hawk.put("LastServiceVersion", 0);
        }

        if (Hawk.get("LastServiceVersion").equals(0)){
            Log.d("TTT", "onCreate: "+"Last Service Version equal to 0");
            //call api for saving data in room database and check the lastServiceVersion and update it here with the new value and show value
            getServices();
        }
        else {
            Log.d("TTT", "onCreate: "+"Last Service Version not equal to 0");

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

                 getLastVersion(3);



            } else {
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);
                getServicesFromDB();

            }


        }


        servicesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

                    if (Hawk.get("LastServiceVersion").equals(response.body().getVersion())){
                        getServicesFromDB();
                    }
                    else {
                        getServices();
                    }


                }
                else {
                    getServicesFromDB();
                }

            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getServicesFromDB() {
        connected = false;
       // servicesModels.clear();
        servicesModels.addAll( database.serviceDao().getServices());
        Log.d("TTTTT", "getServicesFromDB: "+servicesModels.size());
        Log.d("TTTTT", "getServicesFromDB: "+servicesModels.isEmpty());
        Log.d("TTTTT", "getServicesFromDB: "+servicesModels.get(0).getTitle());
        Log.d("TTTTT", "getServicesFromDB: "+servicesModels.get(0).getTitle());

        servicesAdapter.notifyDataSetChanged();

    }

    private void getServices() {
        dialog.show();
        connectionService.getServices().enqueue(new Callback<ArrayList<ServicesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ServicesModel>> call, Response<ArrayList<ServicesModel>> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()){
                    Log.d("TTT", "onResponse: "+response.body().size());


                    servicesModels.addAll(response.body());

                    //save data in roomDB
                    database.serviceDao().deleteAllServices();

                    for (int i = 0; i < servicesModels.size(); i++) {
                        database.serviceDao().addService(servicesModels.get(i));
                    }



                   Hawk.put("LastServiceVersion",servicesModels.get(servicesModels.size()-1).getRecordVersion());
                    Log.d("TTT", "onResponse: " + Hawk.get("LastServiceVersion"));

                    servicesAdapter.notifyDataSetChanged();

                }
                else {
                    getServicesFromDB();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServicesModel>> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }
}

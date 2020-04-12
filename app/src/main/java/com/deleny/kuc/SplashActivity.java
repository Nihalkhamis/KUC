package com.deleny.kuc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.deleny.kuc.model.LoginModel;
import com.deleny.kuc.model.SocialmediaModel;
import com.deleny.kuc.model.VersionModel;
import com.deleny.kuc.service.SocialmediaDatabase;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    List<SocialmediaModel> socialmediaModels;

    SocialmediaDatabase database;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if (!Hawk.isBuilt()){
            Hawk.init(this).build();
        }
        socialmediaModels = new ArrayList<>();

        database = SocialmediaDatabase.getInstance(this);

        if (Hawk.get("LastSMVersion") == null) {
            Hawk.put("LastSMVersion", 0);
        }


        getLastVersion(1);


        if (Hawk.get("ID") != null) {
            if (Hawk.get("ID").equals("0")) {

            } else {
                checkUser(Hawk.get("ID").toString());
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);

    }

    private void checkUser(String ID) {
        connectionService.checkUser(ID).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getID());
                    Log.d("TTT", "onResponse: " + response.body().getDisplayName());
                    Log.d("TTT", "onResponse: " + response.body().getImage());
                    Hawk.put("savedName", response.body().getDisplayName());
                    Hawk.put("savedImg", response.body().getImage());

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }


    private void getLastVersion(int i) {
        connectionService.getLastVersion(i).enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getVersion());

                    if (Hawk.get("LastSMVersion").equals(response.body().getVersion())) {
                    }
                    else {
                        getSocialMedia();
                    }

                }

            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }

    private void getSocialMedia() {
        connectionService.getSocialmedia().enqueue(new Callback<ArrayList<SocialmediaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SocialmediaModel>> call, Response<ArrayList<SocialmediaModel>> response) {
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().size());
                    socialmediaModels.addAll(response.body());

                      //save data in roomDB
                    database.socialmediaDao().deleteAllSocialMedia();

                    for (int i = 0; i < socialmediaModels.size(); i++) {
                        database.socialmediaDao().addSocialmedia(socialmediaModels.get(i));
                    }

                    Hawk.put("LastSMVersion", socialmediaModels.get(socialmediaModels.size() - 1).getRecordVersion());
                    Log.d("TTT", "onResponse: " + Hawk.get("LastSMVersion"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SocialmediaModel>> call, Throwable t) {

            }
        });
    }


}

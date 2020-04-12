package com.deleny.kuc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.VersionModel;
import com.deleny.kuc.service.AboutUsDatabase;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.aboutUsTV)
    TextView aboutUsTV;
    @BindView(R.id.about)
    TextView about;

    ProgressDialog dialog;

    AboutUsDatabase database;

    List<AboutUsModel> aboutUsModels;

    boolean connected = false;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);
    @BindView(R.id.aboutUsImage)
    ImageView aboutUsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        if (!Hawk.isBuilt()) {
            Hawk.init(this).build();
        }
        database = AboutUsDatabase.getInstance(this);

        aboutUsModels = new ArrayList<>();

        aboutUsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (Hawk.get("LastAboutUsVersion") == null) {
            Hawk.put("LastAboutUsVersion", 0);
        }
        if (Hawk.get("LastAboutUsVersion").equals(0)) {
            Log.d("TTT", "onCreate: " + "Last About us Version equal to 0");
            //call api for saving data in room database and check the lastServiceVersion and update it here with the new value and show value
            getAboutUs();
        } else {
            Log.d("TTT", "onCreate: " + "Last About us Version not equal to 0");

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

                getLastVersion(4);

            } else {
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);
                getAboutUsFromDB();
            }
        }
    }

    private void getAboutUs() {
        dialog.show();

        connectionService.getAboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    about.setText(response.body().getAboutus());
                    byte[] decodedString = Base64.decode(response.body().getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Glide.with(AboutUsActivity.this).load(decodedByte).fitCenter().into(aboutUsImage);

                    //save data in roomDB
                    database.aboutUsDao().deleteAllAboutUs();

                    database.aboutUsDao().addAboutUs(response.body());
                    Hawk.put("LastAboutUsVersion", response.body().getRecordVersion());
                    Log.d("TTT", "onResponse: " + Hawk.get("LastServiceVersion"));
                } else {
                    getAboutUsFromDB();

                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());


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
                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getVersion());

                    if (Hawk.get("LastAboutUsVersion").equals(response.body().getVersion())) {
                        getAboutUsFromDB();
                    } else {
                        getAboutUs();
                    }


                } else {
                    getAboutUsFromDB();
                }

            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getAboutUsFromDB() {
        connected = false;
        // aboutUsModels.clear();
        aboutUsModels.addAll(database.aboutUsDao().getAboutUs());

        about.setText(aboutUsModels.get(0).getAboutus());

        byte[] decodedString = Base64.decode(aboutUsModels.get(0).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(AboutUsActivity.this).load(decodedByte).fitCenter().into(aboutUsImage);
    }

}

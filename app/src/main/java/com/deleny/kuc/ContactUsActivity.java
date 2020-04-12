package com.deleny.kuc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deleny.kuc.model.ContactUsModel;
import com.deleny.kuc.model.VersionModel;
import com.deleny.kuc.service.ContactUsDatabase;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends FragmentActivity implements OnMapReadyCallback {



    GoogleMap mapAPI;
    SupportMapFragment supportMapFragment;

    @BindView(R.id.aboutUsTV)
    TextView aboutUsTV;

    ProgressDialog dialog;

    ContactUsDatabase database;

    List<ContactUsModel> contactUsModels;




    boolean connected = false;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.mobileTV)
    TextView mobileTV;
    @BindView(R.id.emailTV)
    TextView emailTV;
    @BindView(R.id.websiteTV)
    TextView websiteTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.phoneLayout)
    LinearLayout phoneLayout;
    @BindView(R.id.mobileLayout)
    LinearLayout mobileLayout;
    @BindView(R.id.emailLayout)
    LinearLayout emailLayout;
    @BindView(R.id.websiteLayout)
    LinearLayout websiteLayout;
    @BindView(R.id.addressLayout)
    LinearLayout addressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        if (!Hawk.isBuilt()) {
            Hawk.init(this).build();
        }

        database = ContactUsDatabase.getInstance(this);


        contactUsModels = new ArrayList<>();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);


        aboutUsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (Hawk.get("LastContactUsVersion") == null) {
            Hawk.put("LastContactUsVersion", 0);
        }
        if (Hawk.get("LastContactUsVersion").equals(0)) {
            Log.d("TTT", "onCreate: " + "Last About us Version equal to 0");
            getContacttUs();
        } else {
            Log.d("TTT", "onCreate: " + "Last About us Version not equal to 0");
            //check for the internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);

                getLastVersion(2);

            } else {
                Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);
                getContactUsFromDB();
            }
        }

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +phoneTV.getText().toString()));
                startActivity(i);
            }
        });
        mobileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +mobileTV.getText().toString()));
                startActivity(i);
            }
        });
        websiteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteTV.getText().toString()));
                startActivity(browserIntent);
            }
        });
        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                String aEmailBCCList[] = { emailTV.getText().toString() };
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailBCCList);
                startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
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

                    if (Hawk.get("LastContactUsVersion").equals(response.body().getVersion())) {
                        getContactUsFromDB();
                    } else {
                        getContacttUs();
                    }


                } else {
                    getContactUsFromDB();
                }

            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getContactUsFromDB() {
        connected = false;
        // contactUsModels.clear();
        contactUsModels.addAll(database.contactUsDao().getContactUs());
        phoneTV.setText(contactUsModels.get(0).getPhone());
        mobileTV.setText(contactUsModels.get(0).getMobile());
        addressTV.setText(contactUsModels.get(0).getAddress());
        emailTV.setText(contactUsModels.get(0).getEmail());
        websiteTV.setText(contactUsModels.get(0).getWebsite());

        LatLng here = new LatLng(Double.parseDouble(contactUsModels.get(0).getLat()),Double.parseDouble(contactUsModels.get(0).getLong()));
        mapAPI.animateCamera(CameraUpdateFactory.newLatLngZoom(here,12.0f));
        mapAPI.addMarker(new MarkerOptions().position(here).title("KUC"));
       // mapAPI.moveCamera(CameraUpdateFactory.newLatLng(here));

    }

    private void getContacttUs() {
        dialog.show();
        connectionService.getContactUs().enqueue(new Callback<ArrayList<ContactUsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ContactUsModel>> call, Response<ArrayList<ContactUsModel>> response) {
                dialog.dismiss();
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().size());


                    phoneTV.setText(response.body().get(0).getPhone());
                    mobileTV.setText(response.body().get(0).getMobile());
                    addressTV.setText(response.body().get(0).getAddress());
                    emailTV.setText(response.body().get(0).getEmail());
                    websiteTV.setText(response.body().get(0).getWebsite());


                    LatLng here = new LatLng(Double.parseDouble(response.body().get(0).getLat()),Double.parseDouble(response.body().get(0).getLong()));
                    mapAPI.animateCamera(CameraUpdateFactory.newLatLngZoom(here,12.0f));
                    mapAPI.addMarker(new MarkerOptions().position(here).title("KUC"));

                    Log.d("TTT", "onResponse: lat "+Double.parseDouble(response.body().get(0).getLat()));
                    Log.d("TTT", "onResponse:  long "+Double.parseDouble(response.body().get(0).getLong()));

                   // Hawk.put("Phone",response.body().get(0).getPhone());

                    //save data in roomDB
                    database.contactUsDao().deleteAllContactUs();

                    database.contactUsDao().addContactUs(response.body().get(0));
                    Hawk.put("LastContactUsVersion", response.body().get(0).getRecordVersion());
                    Log.d("TTT", "onResponse: " + Hawk.get("LastContactUsVersion"));

                } else {
                    getContactUsFromDB();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContactUsModel>> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;

    }

}

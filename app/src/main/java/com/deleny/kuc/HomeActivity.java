package com.deleny.kuc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.adapter.SocialMediaAdapter;
import com.deleny.kuc.model.SocialmediaModel;
import com.deleny.kuc.service.SocialmediaDatabase;
import com.deleny.kuc.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_host_fragment)
    LinearLayout navHostFragment;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.usernameTV)
    TextView usernameTV;
    @BindView(R.id.loginTV)
    TextView loginTV;
    @BindView(R.id.aboutLayout)
    LinearLayout aboutLayout;
    @BindView(R.id.servicesLayout)
    LinearLayout servicesLayout;
    @BindView(R.id.contractsLayout)
    LinearLayout contractsLayout;
    @BindView(R.id.newsLayout)
    LinearLayout newsLayout;
    @BindView(R.id.contactUsLayout)
    LinearLayout contactUsLayout;
    @BindView(R.id.socialMediaRV)
    RecyclerView socialMediaRV;

    SocialMediaAdapter socialMediaAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.orderLayout)
    LinearLayout orderLayout;
    @BindView(R.id.aroundLayout)
    LinearLayout aroundLayout;
    @BindView(R.id.myAccountLayout)
    LinearLayout myAccountLayout;


    private AppBarConfiguration mAppBarConfiguration;

    HomeFragment homeFragment;

    FragmentManager manager;

    String username, userimage;

    SocialmediaDatabase database;

    List<SocialmediaModel> socialmediaModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (!Hawk.isBuilt()) {
            Hawk.init(this).build();
        }
        database = SocialmediaDatabase.getInstance(this);

        socialmediaModels = new ArrayList<>();

        socialmediaModels = database.socialmediaDao().getSocialMedia();

        Log.d("TTT", "onCreate: " + socialmediaModels.get(0).getRecordVersion());


        homeFragment = new HomeFragment();
        manager = getSupportFragmentManager();


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userimage = intent.getStringExtra("userimage");


        if (Hawk.get("savedName") != null && Hawk.get("savedImg") != null) {
            if (!Hawk.get("savedName").equals("") && !Hawk.get("savedImg").equals("")) {
                usernameTV.setText(Hawk.get("savedName"));
                String savedImg = Hawk.get("savedImg");
                byte[] decodedString = Base64.decode(savedImg, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Glide.with(HomeActivity.this).load(decodedByte).fitCenter().into(userImage);
                loginTV.setText("تسجيل خروج");

            }
        }


        if (username != null && userimage != null) {
            Log.d("TTT", "onCreate: " + username + "    " + userimage);
            usernameTV.setText(username);
            loginTV.setText("تسجيل خروج");
            byte[] decodedString = Base64.decode(userimage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Glide.with(HomeActivity.this).load(decodedByte).fitCenter().into(userImage);

        }

//        if (!Hawk.get("ID").equals(0)) {
//            usernameTV.setText(username);
//            loginTV.setText("تسجيل خروج");
//        }

        setHomeScreen();


        loginTV.setOnClickListener(v -> {
            // Log.d("TTTTTTTTTTT", "onCreate: ");
            if (loginTV.getText().equals("تسجيل دخول"))
                setLoginScreen();
            else {
                usernameTV.setText(R.string.nav_header_title);
                loginTV.setText("تسجيل دخول");
                userImage.setImageResource(R.drawable.default_user_img);
                Hawk.put("ID", 0);
                Hawk.put("savedName", "");
                Hawk.put("SavedImg", "");
                Log.d("TTT", "onCreate: " + Hawk.get("ID"));
            }

        });

        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, NewsActivity.class));
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
            }
        });

        servicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, ServicesActivity.class));
            }
        });

        contractsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, ContractsActivity.class));
            }
        });

        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
            }
        });
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "034207755"));
                startActivity(i);
            }
        });
        aroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, AroundAppActivity.class));
            }
        });
        myAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ggggggggg", "onClick: ");
                drawerLayout.closeDrawers();
               // startActivity(new Intent(HomeActivity.this, LabSheetActivity.class));
                //check for internet conncetion
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    Log.d("TTT", "onCreate: " + "Connected" );
                     startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                } else {
                    Log.d("TTT", "onCreate: " + "Not Connected" );
                    startActivity(new Intent(HomeActivity.this, InternetFailAActivity.class));
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        socialMediaRV.setLayoutManager(linearLayoutManager);

        socialMediaAdapter = new SocialMediaAdapter(this, socialmediaModels, new SocialMediaAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialmediaModels.get(position).getTargetLink()));
                startActivity(browserIntent);

            }
        });

        socialMediaRV.setAdapter(socialMediaAdapter);
        socialMediaAdapter.notifyDataSetChanged();

    }


    private void setLoginScreen() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void setHomeScreen() {
        manager.beginTransaction().replace(R.id.nav_host_fragment, homeFragment, "").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void menu() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}

package com.deleny.kuc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InternetFailAActivity extends AppCompatActivity {

    @BindView(R.id.error_connection)
    TextView errorConnection;
    @BindView(R.id.try_again)
    Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_fail_a);
        ButterKnife.bind(this);

        errorConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for internet conncetion
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    Log.d("TTT", "onCreate: " + "Connected" );
                    startActivity(new Intent(InternetFailAActivity.this, LoginActivity.class));
                } else {
                    Log.d("TTT", "onCreate: " + "Not Connected" );
                    Toast.makeText(InternetFailAActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

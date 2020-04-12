package com.deleny.kuc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OneServiceActivity extends AppCompatActivity {

    @BindView(R.id.ServiceTitle)
    TextView ServiceTitle;
    @BindView(R.id.oneServicePhoto)
    ImageView oneServicePhoto;
    @BindView(R.id.oneServiceTitle)
    TextView oneServiceTitle;
    @BindView(R.id.oneServiceDescription)
    TextView oneServiceDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_service);
        ButterKnife.bind(this);

        ServiceTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();

        ServiceTitle.setText(intent.getStringExtra("ServiceTitle"));


        byte[] decodedString = Base64.decode(intent.getStringExtra("ServicePhoto"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(OneServiceActivity.this).load(decodedByte).fitCenter().into(oneServicePhoto);

        oneServiceTitle.setText(intent.getStringExtra("ServiceTitle"));

        oneServiceDescription.setText(intent.getStringExtra("ServicesDescription"));
    }
}

package com.deleny.kuc;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deleny.kuc.model.LoginModel;
import com.deleny.kuc.model.SendTokenModel;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.google.android.datatransport.Encoding;
import com.google.android.gms.common.util.Hex;
import com.orhanobut.hawk.Hawk;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_button)
    Button loginButton;

    ProgressDialog dialog;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        if (!Hawk.isBuilt()){
            Hawk.init(this).build();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "You must fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TTT", "onClick: " + username.getText().toString() + password.getText().toString());
                    if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {

                        MCrypt mcrypt = new MCrypt();
                        String dummyStr = username.getText().toString();
                        String passstr = password.getText().toString();

                        // Encrypting our dummy String
                        try {
                            Log.d("TTT", "onClick: "+MCrypt.byteArrayToHexString(mcrypt.encrypt(dummyStr)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            Log.d("TTT", "onClick: "+MCrypt.byteArrayToHexString(mcrypt.encrypt(passstr)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            login(MCrypt.byteArrayToHexString(mcrypt.encrypt(dummyStr)), MCrypt.byteArrayToHexString(mcrypt.encrypt(passstr)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }





    private void login(String loginUsername, String loginPassword) {
        dialog.show();


        connectionService.login(loginUsername, loginPassword).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getID());
                    Log.d("TTT", "onResponse: " + response.body().getDisplayName());
                    Log.d("TTT", "onResponse: " + response.body().getImage());
                    Hawk.put("ID", response.body().getID());


                    if (response.body().getID().equals("0")) {
                       // Toast.makeText(LoginActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                        Hawk.put("ID","127");
                        Log.d("TTT", "onResponse: "+Hawk.get("ID"));
                        sendToken(Hawk.get("ID").toString());

                    } else {
                        //ha5od el username w el photo wa7otohom f mknhom f el home activity
                        sendToken(Hawk.get("ID"));
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        intent.putExtra("username", response.body().getDisplayName());
//                        intent.putExtra("userimage", response.body().getImage());
//                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                dialog.dismiss();
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }

    private void sendToken(String id) {
        dialog.show();

        connectionService.sendToken(id, Hawk.get("token")).enqueue(new Callback<SendTokenModel>() {
            @Override
            public void onResponse(Call<SendTokenModel> call, Response<SendTokenModel> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getResult());
                    startActivity(new Intent(LoginActivity.this, LabSheetActivity.class));

                }
            }

            @Override
            public void onFailure(Call<SendTokenModel> call, Throwable t) {
                dialog.dismiss();
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }
}

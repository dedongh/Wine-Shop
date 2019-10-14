package com.engineerskasa.winey.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.engineerskasa.winey.Model.User;
import com.engineerskasa.winey.R;
import com.engineerskasa.winey.Retrofit.IWineyAPI;
import com.engineerskasa.winey.Util.Common;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnContinue;
    private static final int REQUEST_CODE = 1000;

    // retrofit
    IWineyAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//retrofit instance
        mService = Common.getAPI();

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage();
            }
        });
    }

    private void startLoginPage() {
        if (Common.currentUser != null) {
            // login User
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        } else {
            showRegisterDialog();
        }
    }

    private void showRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Register");

        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edt_name = (MaterialEditText) register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edt_address = (MaterialEditText) register_layout.findViewById(R.id.edt_address);
        final MaterialEditText edt_birthdate = (MaterialEditText) register_layout.findViewById(R.id.edt_birthdate);
        final MaterialEditText edt_phone = (MaterialEditText) register_layout.findViewById(R.id.edt_phone);

        builder.setView(register_layout);
        final AlertDialog dialog = builder.create();
        Button btn_reg = (Button) register_layout.findViewById(R.id.btnReg);

        //make sure birthdate matches required format
        edt_birthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (TextUtils.isEmpty(edt_address.getText().toString())) {
                   /* edt_address.setError("Please Enter Address");
                    edt_address.requestFocus();*/
                    Toast.makeText(MainActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_name.getText().toString())) {
                    /*edt_name.setError("Please Enter Name");
                    edt_name.requestFocus();*/
                    Toast.makeText(MainActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_birthdate.getText().toString())) {
                    /*edt_birthdate.setError("Please Enter Birthdate");
                    edt_birthdate.requestFocus();*/
                    Toast.makeText(MainActivity.this, "Please Enter Birthdate", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_phone.getText().toString())) {
                   /* edt_phone.setError("Phone number is required");
                    edt_phone.requestFocus();*/
                    Toast.makeText(MainActivity.this, "Phone number is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please Wait");

                // register new User
                mService.registerNewUser(edt_phone.getText().toString(),
                        edt_name.getText().toString(),
                        edt_birthdate.getText().toString(),
                        edt_address.getText().toString()
                ).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        waitingDialog.dismiss();
                        User user = response.body();
                        if (TextUtils.isEmpty(user.getError_msg())) {
                            Toast.makeText(MainActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            Common.currentUser = response.body();
                            // start new Activity
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        waitingDialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }
}

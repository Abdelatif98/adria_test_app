package com.example.splatchscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Parameter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    //Variables
    Button btn_sign,btn_login,btn_about;
    LoginButton fb_login;
    ImageView logo;
    TextInputLayout user,password;
    TextView hi,desc;
    //call back
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //fb btn
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fb_login = (LoginButton)findViewById(R.id.login_button);

        //declarations
        btn_sign = findViewById(R.id.btn_sign);
        btn_login = findViewById(R.id.btn_login);
        btn_about = findViewById(R.id.about);
        logo = findViewById(R.id.logo);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        hi = findViewById(R.id.hi);
        desc = findViewById(R.id.desc);

        // about btn
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,About.class);
                startActivity(intent);
            }
        });

        //sign up btn
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair(logo,"logo_animation");
                pairs[1] = new Pair(user,"text_animation");
                pairs[2] = new Pair(user,"desc_animation");
                pairs[3] = new Pair(user,"username_animation");
                pairs[4] = new Pair(user,"password_animation");
                pairs[5] = new Pair(user,"btnCon_animation");
                pairs[6] = new Pair(user,"btnNU_animation");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccessToken();
            }
        });

        //fb login
        fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken(),Toast.LENGTH_LONG);
                //Intent intent = new Intent(login.this,Home.class);
                //startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login attempt canceled.",Toast.LENGTH_LONG);

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(),"Login attempt failed.",Toast.LENGTH_LONG);
            }
        });

    }

    public void getAccessToken(){
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        String username = user.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();

        Call<AccessToken> call = getDataService.getAccessToken("Login","password","4e4d43a7-0bb9-4588-b6ad-6a5dc5611889","openid",username,pass);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()){
                    AccessToken accessToken = response.body();
                    Intent intent = new Intent(login.this,Home.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"error" + t + username,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode , resultCode , data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
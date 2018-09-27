package com.example.saudali.shuftipro_demo.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.saudali.shuftipro.listeners.NetworkListener;
import com.example.saudali.shuftipro_demo.DbConstants;
import com.example.saudali.shuftipro_demo.Helpers;
import com.example.saudali.shuftipro_demo.R;
import com.example.saudali.shuftipro_demo.cloud.CloudConnection;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements NetworkListener, View.OnClickListener{

    private static String TAG = LoginActivity.class.getSimpleName();
    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private WebView webView;
    private TextView contactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressbar);
        webView = findViewById(R.id.webView);
        contactTextView = findViewById(R.id.contactTextView);

        passwordEditText.setTypeface(Typeface.DEFAULT);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        contactTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (webView != null){
            if (webView.canGoBack()){
                webView.goBack();
            }else {
                webView.setVisibility(View.GONE);
                webView = null;
                setContentView(R.layout.activity_login);
            }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void successResponse(String s) {
        enableView();
        String clientId = null, secretKey = null;
        try{
            JSONObject response = new JSONObject(s);
            if (response.has("status")){
                String status = response.getString("status");
                if (status.equalsIgnoreCase("found")){
                    JSONObject dataObject = response.getJSONObject("data");

                    if (dataObject != null){
                        if (dataObject.has("client_id")) {
                             clientId = dataObject.getString("client_id");
                        }
                        if (dataObject.has("secret_key")) {
                             secretKey = dataObject.getString("secret_key");
                        }
                    }
                }else {
                    String error = "Email or password is invalid. Try again.";
                    Helpers.displayAlertMessage(this,error);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        navigate(clientId,secretKey);
    }

    @Override
    public void errorResponse(String error) {
        enableView();
        Helpers.displayAlertMessage(this,error);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton){
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (email.isEmpty()){
                String message = "Email cannot be empty. Please provide your email address.";
                Helpers.displayAlertMessage(this,message);
                return;
            }
            if (password.isEmpty()){
                String message = "Password cannot be empty. Please provide password.";
                Helpers.displayAlertMessage(this,message);
                return;
            }

            boolean isSubmitted = CloudConnection.getInstance(email,password).executeLoginRequest(this,LoginActivity.this);
            progressBar.setVisibility(View.VISIBLE);
            disableView();

            if (!isSubmitted){
                enableView();
                progressBar.setVisibility(View.GONE);
                Helpers.displayAlertMessage(LoginActivity.this,"No Internet. Please connect to your internet.");
            }
        }
        if (v == contactTextView){
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(DbConstants.SHUFTIPRO_SALES);
        }
    }


    private void navigate(String clientId, String secretKey) {
        if (clientId == null || secretKey == null){
            return;
        }
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(DbConstants.CLIENT_ID,clientId);
        intent.putExtra(DbConstants.SECRET_KEY,secretKey);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void disableView(){
        loginButton.setClickable(false);
        loginButton.setActivated(false);
        loginButton.setAlpha(0.5f);
        emailEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
    }
    private void enableView(){
        progressBar.setVisibility(View.GONE);
        loginButton.setAlpha(1f);
        loginButton.setClickable(true);
        loginButton.setActivated(true);
        emailEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
    }
}

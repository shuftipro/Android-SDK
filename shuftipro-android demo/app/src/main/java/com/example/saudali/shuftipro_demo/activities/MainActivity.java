
package com.example.saudali.shuftipro_demo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saudali.shuftipro.Shuftipro;
import com.example.saudali.shuftipro.listeners.ShuftiVerifyListener;
import com.example.saudali.shuftipro.utils.Utils;
import com.example.saudali.shuftipro_demo.DbConstants;
import com.example.saudali.shuftipro_demo.Helpers;
import com.example.saudali.shuftipro_demo.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener{

    private RelativeLayout faceRelativeLayout,docRelativeLayout,addressRelativeLayout;
    private ImageView faceCheckImageView,docCheckImageView,addressCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private Button continueButton;

    private final String TAG = MainActivity.class.getSimpleName();
    private String clientId;
    private String secretKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Getting data from the Login Activity
        Intent intent = getIntent();
        if (intent != null){
            clientId = intent.getStringExtra(DbConstants.CLIENT_ID);
            secretKey = intent.getStringExtra(DbConstants.SECRET_KEY);
        }

        //Initializing views
        faceRelativeLayout = findViewById(R.id.faceRelativeLayout);
        docRelativeLayout = findViewById(R.id.docRelativeLayout);
        addressRelativeLayout = findViewById(R.id.addressRelativeLayout);
        faceCheckImageView = findViewById(R.id.faceCheckImageView);
        docCheckImageView = findViewById(R.id.docCheckImageView);
        addressCheckImageView = findViewById(R.id.addressCheckImageView);
        continueButton = findViewById(R.id.continueButton);

        //Setting click listeners for the layouts
        faceRelativeLayout.setOnClickListener(this);
        docRelativeLayout.setOnClickListener(this);
        addressRelativeLayout.setOnClickListener(this);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == faceRelativeLayout){
            if (!isFaceChecked){
                isFaceChecked = true;
                faceCheckImageView.setImageResource(R.drawable.check_radio_icon);
            }
            else{
                isFaceChecked = false;
                faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == docRelativeLayout){
            if (!isDocChecked){
                isDocChecked = true;
                docCheckImageView.setImageResource(R.drawable.check_radio_icon);
            }
            else{
                isDocChecked = false;
                docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == addressRelativeLayout){
            if (!isAddressChecked){
                isAddressChecked = true;
                addressCheckImageView.setImageResource(R.drawable.check_radio_icon);
            }
            else{
                isAddressChecked = false;
                addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == continueButton){

            //If none of verification is requested display alert message
            if (!isFaceChecked && !isDocChecked && !isAddressChecked){
                String message = "Please, select one or more methods of verification.";
                Helpers.displayAlertMessage(this,message);
            }else {
                requestSDKForVerification();
            }
        }
    }

    private void requestSDKForVerification() {

        //Creating my constructor parameters
        final String country = "GB";
        final String lng = "EN";
        final String email = "yourmail@gmail.com";
        final String callback_url = "https://www.yourdomain.com";
        final String redirect_url = "https://www.yourdomain.com";

        //Get unique reference
        final String reference = Utils.getUniqueReference(this);

        Shuftipro.getInstance(clientId,secretKey).shuftiproVerification(reference,country,lng,email,callback_url,redirect_url,
                 isFaceChecked,isDocChecked,true,true,true,
                true,"","","","","",isAddressChecked,"",
                "",true,true,true,false,
                "",MainActivity.this, MainActivity.this);
    }

    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {
        Log.e(TAG,"\n\n *-->>>>>>>>>>>>>>>> Response Callback <<<<<<<<<<<<<<<--* \n\n");
        Log.d(TAG, responseSet.toString());
        uncheckAllOptions();
    }

    private void uncheckAllOptions() {
        isFaceChecked = false;
        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isAddressChecked = false;
        addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocChecked = false;
        docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
    }
}

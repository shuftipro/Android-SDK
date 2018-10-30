
package com.shufti.shuftipro.shuftipro_demo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
import com.shufti.sdk.shuftipro.utils.Utils;
import com.shufti.shuftipro.shuftipro_demo.Helpers;
import com.shufti.shuftipro.shuftipro_demo.R;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener {

    private RelativeLayout faceRelativeLayout, docRelativeLayout, addressRelativeLayout;
    private ImageView faceCheckImageView, docCheckImageView, addressCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private Button continueButton;

    private String clientId = ""; //Set your client Id here
    private String secretKey = ""; //Set your secret key here.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

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
        if (v == faceRelativeLayout) {
            if (!isFaceChecked) {
                isFaceChecked = true;
                faceCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isFaceChecked = false;
                faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == docRelativeLayout) {
            if (!isDocChecked) {
                isDocChecked = true;
                docCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isDocChecked = false;
                docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == addressRelativeLayout) {
            if (!isAddressChecked) {
                isAddressChecked = true;
                addressCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isAddressChecked = false;
                addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == continueButton) {

            //If none of verification is requested display alert message
            if (!isFaceChecked && !isDocChecked && !isAddressChecked) {
                String message = "Please, select one or more methods of verification.";
                Helpers.displayAlertMessage(this, message);
            } else {
                requestSDKForVerification();
            }
        }
    }

    private void requestSDKForVerification() {

        //Creating constructor parameters
        final String country = "GB";
        final String lng = "EN";
        final String email = "yourmail@gmail.com";
        final String callback_url = "https://www.yourdomain.com";
        final String redirect_url = "https://www.yourdomain.com";

        //Get unique reference
        final String reference = Utils.getUniqueReference(this);

        if (clientId.isEmpty() || secretKey.isEmpty()){

            showErrorMessageDialog("PLease provide client id and secret key before proceed.");
            return;
        }
        Shuftipro.getInstance(clientId, secretKey).shuftiproVerification(reference, country, lng, email, callback_url, redirect_url,
                isFaceChecked, isDocChecked, true, true, true,
                true,true,true,true,true,true,isAddressChecked,
                true,true,true,true,true,
                HomeActivity.this,HomeActivity.this);
    }

    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {

        //In case of any error or response this method will invoke. Please check your logcat if request do not process.
        Log.e("Callback : ", responseSet.toString());
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

    public void showErrorMessageDialog(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                uncheckAllOptions();
               dialogInterface.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

}

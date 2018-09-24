
package com.example.saudali.shuftipro_demo;

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

import com.example.saudali.shuftipro.Shuftipro;
import com.example.saudali.shuftipro.listeners.ShuftiVerifyListener;
import com.example.saudali.shuftipro.utils.Utils;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener{

    private RelativeLayout faceRelativeLayout,docRelativeLayout,addressRelativeLayout;
    private ImageView faceCheckImageView,docCheckImageView,addressCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private Button continueButton;

    private final String TAG = MainActivity.class.getSimpleName();
    private String clientId = "2c624232cdd221771294dfbb310aca000a0df6ac8b66b696d90ef06fdefb64a3";
    private String secretKey = "G3RFRQMJFQ1XW22GYYEMD8IBQMLH52GS";

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
                displayAlertMessage();
            }else {
                requestSDKForVerification();
            }
        }
    }

    private void requestSDKForVerification() {


        //Creating my constructor parameters
        final String country = "UK";
        final String lng = "EN";
        final String email = "yourmail@gmail.com";
        final String callback_url = "https://www.your_domain.com";
        final String redirect_url = "https://www.your_domain.com";

        //Get unique reference
        final String reference = Utils.getUniqueReference(this);
        //Calling new dynamic constructor

        Shuftipro.getInstance(clientId,secretKey).shuftiproVerification(reference,country,lng,email,callback_url,redirect_url,
                isFaceChecked,isDocChecked,true,true,true,
                true,"","","","","",isAddressChecked,"",
                "",true,true,true,false,
                "",MainActivity.this, MainActivity.this);
    }

    private void displayAlertMessage(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Please, select one or more methods of verification.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {
        Log.e(TAG,"\n\n *************** Response Callback *************** \n\n");
        Log.d(TAG, responseSet.toString());
    }
}

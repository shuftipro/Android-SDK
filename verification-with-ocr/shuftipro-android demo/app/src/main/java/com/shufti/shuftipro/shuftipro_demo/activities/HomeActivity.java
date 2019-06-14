
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

import com.shufti.shuftipro.shuftipro_demo.Helpers;
import com.shufti.shuftipro.shuftipro_demo.R;

import com.shutipro.sdk.Shuftipro;
import com.shutipro.sdk.listeners.ShuftiVerifyListener;
import com.shutipro.sdk.models.AddressVerification;
import com.shutipro.sdk.models.DocumentVerification;
import com.shutipro.sdk.models.FaceVerification;
import com.shutipro.sdk.models.ShuftiproVerification;
import com.shutipro.sdk.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

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
                Helpers.displayAlertMessage(this, getString(R.string.select_service));
            } else {
                requestSDKForVerification();
            }
        }
    }

    private void requestSDKForVerification() {

        if (clientId.isEmpty() || secretKey.isEmpty()) {

            showErrorMessageDialog(getString(R.string.provide_credentials));
            return;
        }

        sendVerificationRequest();
    }

    private void sendVerificationRequest() {

        final String reference = Utils.getUniqueReference(this);
        final String country = "GB";
        final String lng = "EN";
        final String email = "yourmail@gmail.com";
        final String callback_url = "https://www.yourdomain.com";
        final String redirect_url = "https://www.yourdomain.com";
        final String verification_mode = "video";

        Shuftipro instance = Shuftipro.getInstance(clientId, secretKey, false);

        /*
         * FOR FACE VERIFICATION SERVICE
         * Make an instance and set the face verification to true
         */

        FaceVerification faceVerification = FaceVerification.getInstance();
        faceVerification.setFaceVerification(true);

        /*
         * FOR DOCUMENTATION VERIFICATION SERVICE
         * Make an instance and set the supported types & required fields for verification
         */

        DocumentVerification documentVerification = DocumentVerification.getInstance();
        ArrayList<String> doc_supported_types = new ArrayList<>();
        doc_supported_types.add("id_card");
        doc_supported_types.add("credit_or_debit_card");
        doc_supported_types.add("passport");
        doc_supported_types.add("driving_license");

        documentVerification.setSupportedTypes(doc_supported_types);
        documentVerification.extractName(true);
        documentVerification.extractDob(true);
        documentVerification.extractDocumentNumber(true);
        documentVerification.extractExpiryDate(true);
        documentVerification.extractIssueDate(true);

        /*
         * FOR ADDRESS VERIFICATION SERVICE
         * Make an instance, set the supported types & required fields for verification
         */

        AddressVerification addressVerification = AddressVerification.getInstance();
        ArrayList<String> supported_types = new ArrayList<>();
        supported_types.add("id_card");
        supported_types.add("passport");
        supported_types.add("bank_statement");
        supported_types.add("utility_bill");
        addressVerification.setSupportedTypes(supported_types);
        addressVerification.extractFullAddress(true);
        addressVerification.extractName(true);

        ShuftiproVerification.RequestBuilder requestBuilder = new ShuftiproVerification.RequestBuilder(reference, country, callback_url,
                this, verification_mode, new ShuftiVerifyListener() {
            @Override
            public void verificationStatus(HashMap<String, String> responseSet) {
                uncheckAllOptions();
                Log.e("Response", responseSet.toString());
            }
        });

        requestBuilder.withFaceVerification(isFaceChecked ? faceVerification : null);
        requestBuilder.withAddressVerification(isAddressChecked ? addressVerification : null);
        requestBuilder.withDocumentVerification(isDocChecked ? documentVerification : null);
        requestBuilder.withLanguage(lng);
        requestBuilder.withRedirectUrl(redirect_url);
        requestBuilder.withEmail(email);
        instance.shuftiproVerification(requestBuilder.buildShuftiModel());
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
        alertDialog.setPositiveButton(getString(R.string.ok), new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                uncheckAllOptions();
                dialogInterface.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}

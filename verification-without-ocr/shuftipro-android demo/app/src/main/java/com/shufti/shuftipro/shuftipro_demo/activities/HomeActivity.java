
package com.shufti.shuftipro.shuftipro_demo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
import com.shufti.sdk.shuftipro.utils.Utils;
import com.shufti.shuftipro.shuftipro_demo.Helpers;
import com.shufti.shuftipro.shuftipro_demo.R;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener {

    private RelativeLayout faceRelativeLayout;
    private boolean isFaceChecked = false, isDocChecked = false, isAddressChecked = false;
    private boolean isToVerifyName = false;
    private ImageView faceCheckImageView;
    private EditText firstNameEditText, lastNameEditText, dobEditText, docNumberEditText, issueDateEditText, expiryDateEditText, addressEditText;
    private Button continueButton;
    private String firstName, lastName, dob, doc_number, issue_date, expiry_date, address;

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
        faceCheckImageView = findViewById(R.id.faceCheckImageView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        dobEditText = findViewById(R.id.dobEditText);
        docNumberEditText = findViewById(R.id.docNumberEditText);
        issueDateEditText = findViewById(R.id.issueDateEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        addressEditText = findViewById(R.id.addressEditText);
        continueButton = findViewById(R.id.continueButton);

        //Setting click listeners for the layouts
        faceRelativeLayout.setOnClickListener(this);
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

        if (v == continueButton) {

            firstName = firstNameEditText.getText().toString();
            lastName = lastNameEditText.getText().toString();
            dob = dobEditText.getText().toString();
            doc_number = docNumberEditText.getText().toString();
            issue_date = issueDateEditText.getText().toString();
            expiry_date = expiryDateEditText.getText().toString();
            address = addressEditText.getText().toString();

            //If none of verification is requested display alert message
            if (!isFaceChecked && firstName.isEmpty() && lastName.isEmpty() && dob.isEmpty() && doc_number.isEmpty()
                    && issue_date.isEmpty() && expiry_date.isEmpty() && address.isEmpty()) {
                showErrorMessageDialog("Please choose your method of verification.");
                return;
            }

            //Check if user has check for document verification
            if (!firstName.isEmpty() || !lastName.isEmpty() || !dob.isEmpty() || !doc_number.isEmpty() ||
                    !issue_date.isEmpty() || !expiry_date.isEmpty()) {

                isDocChecked = true;

                if (!firstName.isEmpty()) {
                    isToVerifyName = true;
                }
            }

            //Check if user has check for document verification
            if (!address.isEmpty()) {
                isAddressChecked = true;
            }

            requestSDKForVerification();
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

        if (clientId.isEmpty() || secretKey.isEmpty()) {

            showErrorMessageDialog("Please provide client id and secret key before proceed.");
            return;
        }

        Shuftipro.getInstance(clientId, secretKey, false).shuftiproVerification(reference, country, lng, email, callback_url, redirect_url,
                isFaceChecked, isDocChecked, true, true, true,
                true, isToVerifyName, firstName, lastName, "", dob,
                doc_number, expiry_date, issue_date, isAddressChecked, true,
                true, true, address, isToVerifyName, firstName,
                lastName, "", false, false, "", HomeActivity.this,
                HomeActivity.this);
    }

    //SDK callback function invoked on response
    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {

        //In case of any error or response, this method will invoke. Please check your logcat if request do not process.
        Log.e("Callback From SDK : ", responseSet.toString());
        uncheckAllOptions();
    }

    //Deselect all of the pre selected values
    private void uncheckAllOptions() {
        isFaceChecked = false;
        isDocChecked = false;
        isAddressChecked = false;
        isToVerifyName = false;

        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);

        firstNameEditText.setText("");
        lastNameEditText.setText("");
        dobEditText.setText("");
        docNumberEditText.setText("");
        issueDateEditText.setText("");
        expiryDateEditText.setText("");
        addressEditText.setText("");
    }

    //Display an alert dialog to show the error messages
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

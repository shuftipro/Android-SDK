package com.example.pf.demowebviewapp;

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

import com.example.saudali.demowebviewapp.R;
import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
import com.shufti.sdk.shuftipro.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener {

    private RelativeLayout faceRelativeLayout, docRelativeLayout, addressRelativeLayout;
    private ImageView faceCheckImageView, docCheckImageView, addressCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private Button continueButton;
    private JSONObject jsonObject;

    private String clientId = ""; // Add your client Id here.
    private String secretKey = ""; // Add yout secret key here.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        jsonObject = new JSONObject();

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

    private void startVerification() {
        if (clientId.isEmpty() || secretKey.isEmpty()) {
            showErrorMessageDialog(getString(R.string.credentials_missing), getString(R.string.ok));
        }
        Shuftipro instance = Shuftipro.getInstance(clientId, secretKey);
        instance.shuftiproVerification(getJsonObject(), HomeActivity.this, HomeActivity.this);
    }

    private JSONObject getJsonObject() {

        String reference = Utils.getUniqueReference(this);
        try {

            jsonObject.put("reference", reference);
            jsonObject.put("country", "GB");
            jsonObject.put("language", "EN");
            jsonObject.put("email", "yourmail@gmail.com");
            jsonObject.put("callback_url", "https://www.yourdomain.com");
            jsonObject.put("redirect_url", "");
            jsonObject.put("verification_mode", "any");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isFaceChecked) {
            addFaceRequest();
        }
        if (isDocChecked) {
            addDocumentRequest();
        }
        if (isAddressChecked) {
            addAddressRequest();
        }

        return jsonObject;
    }

    private void addFaceRequest() {

        JSONObject faceObject = new JSONObject();
        try {
            faceObject.put("proof", "");
            jsonObject.put("face", faceObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addDocumentRequest() {

        JSONObject documentationObject = new JSONObject();
        ArrayList<String> doc_supported_types = new ArrayList<String>();

        doc_supported_types.add("passport");
        doc_supported_types.add("id_card");
        doc_supported_types.add("driving_license");
        doc_supported_types.add("credit_or_debit_card");

        try {
            documentationObject.put("proof", "");
            documentationObject.put("supported_types", new JSONArray(doc_supported_types));

            //Set parameter in the requested object if OCR is required.

            documentationObject.put("name", "");
            documentationObject.put("dob", "");
            documentationObject.put("document_number", "");
            documentationObject.put("expiry_date", "");
            documentationObject.put("issue_date", "");

            jsonObject.put("document", documentationObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addAddressRequest() {

        JSONObject addressObject = new JSONObject();
        ArrayList<String> address_supported_types = new ArrayList<String>();

        address_supported_types.add("id_card");
        address_supported_types.add("utility_bill");
        address_supported_types.add("bank_statement");

        try {

            addressObject.put("proof", "");
            addressObject.put("full_address", "");
            addressObject.put("name", "");

            addressObject.put("supported_types", new JSONArray(address_supported_types));
            jsonObject.put("address", addressObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                showErrorMessageDialog(getString(R.string.select_methods), getString(R.string.ok));
            } else {
                startVerification();
            }
        }
    }

    public void showErrorMessageDialog(String message, String button_text) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {
        //In case of any error or response this method will invoke. Please check your logcat if request do not process.
        Log.e("Callback From SDK: ", responseSet.toString());
        uncheckAllOptions();
    }

    private void uncheckAllOptions() {
        isFaceChecked = false;
        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocChecked = false;
        docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isAddressChecked = false;
        addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
    }
}

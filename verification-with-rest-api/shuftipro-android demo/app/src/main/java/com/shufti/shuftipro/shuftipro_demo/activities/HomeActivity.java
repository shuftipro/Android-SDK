
package com.shufti.shuftipro.shuftipro_demo.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shufti.shuftipro.Shuftipro;
import com.shufti.shuftipro.listeners.ShuftiVerifyListener;
import com.shufti.shuftipro.shuftipro_demo.Helpers;
import com.shufti.shuftipro.shuftipro_demo.R;
import com.shufti.shuftipro.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ShuftiVerifyListener {

    private RelativeLayout faceRelativeLayout, docRelativeLayout, addressRelativeLayout, consentRelativeLayout;
    private ImageView faceCheckImageView, docCheckImageView, addressCheckImageView, consentCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private boolean isConsentChecked = false;
    private Button continueButton;
    private String clientId = ""; //Add your client id here.
    private String secretKey = ""; //Add your secret key here.

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
        consentRelativeLayout = findViewById(R.id.consentRelativeLayout);
        faceCheckImageView = findViewById(R.id.faceCheckImageView);
        docCheckImageView = findViewById(R.id.docCheckImageView);
        addressCheckImageView = findViewById(R.id.addressCheckImageView);
        consentCheckImageView = findViewById(R.id.consentCheckImageView);
        continueButton = findViewById(R.id.continueButton);

        //Setting click listeners for the layouts
        faceRelativeLayout.setOnClickListener(this);
        docRelativeLayout.setOnClickListener(this);
        addressRelativeLayout.setOnClickListener(this);
        consentRelativeLayout.setOnClickListener(this);
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
        if (v == consentRelativeLayout) {
            if (!isConsentChecked) {
                isConsentChecked = true;
                consentCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isConsentChecked = false;
                consentCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }
        if (v == continueButton) {

            //If none of verification is requested display alert message

            if (!isFaceChecked && !isDocChecked && !isAddressChecked && !isConsentChecked) {
                Helpers.displayAlertMessage(this, getString(R.string.selection_verification));
            } else {
                sendShuftiproVerificationRequest();
            }
        }
    }

    private void sendShuftiproVerificationRequest() {

        //Check if client id or secret key is missing
        if (clientId.isEmpty() || secretKey.isEmpty()) {
            Helpers.displayAlertMessage(this, getString(R.string.missing_credentials));
        }
        Shuftipro instance = Shuftipro.getInstance(clientId, secretKey);
        JSONObject requestedObject = getJsonObject();
        if (requestedObject != null) {
            instance.shuftiproVerification(getJsonObject(), HomeActivity.this, HomeActivity.this);
        }
    }

    private JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {
            String reference = Utils.getUniqueReference(this);
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

        //Creating face object

        JSONObject faceObject = new JSONObject();

        try {

            faceObject.put("proof", "");

            if (isFaceChecked) {
                jsonObject.put("face", faceObject);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating document object

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

            if (isDocChecked) {
                jsonObject.put("document", documentationObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Creating Address object

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

            if (isAddressChecked) {
                jsonObject.put("address", addressObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Creating consent object

        JSONObject consentObject = new JSONObject();
        ArrayList<String> consent_supported_types = new ArrayList<String>();
        consent_supported_types.add("handwritten");
        consent_supported_types.add("printed");

        try {
            consentObject.put("proof", "");
            consentObject.put("text", "This is my consent test");

            consentObject.put("supported_types", new JSONArray(consent_supported_types));
            if (isConsentChecked) {
                jsonObject.put("consent", consentObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Log.e("Request Object:", jsonObject.toString());
        return jsonObject;
    }

    @Override
    public void verificationStatus(HashMap<String, String> hashMap) {
        Log.e("Response from SDK: \n ", hashMap.toString());
        uncheckAllOptions();
    }

    private void uncheckAllOptions() {
        isFaceChecked = false;
        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isAddressChecked = false;
        addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocChecked = false;
        docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isConsentChecked = false;
        consentCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
    }
}

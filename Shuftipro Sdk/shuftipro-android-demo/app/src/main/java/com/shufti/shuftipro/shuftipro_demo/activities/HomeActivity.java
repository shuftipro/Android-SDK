
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
import android.widget.Toast;

import com.shufti.shuftipro.shuftipro_demo.Helpers;
import com.shufti.shuftipro.shuftipro_demo.R;
import com.shutipro.sdk.Shuftipro;
import com.shutipro.sdk.listeners.ShuftiVerifyListener;
import com.shutipro.sdk.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout faceRelativeLayout, docRelativeLayout,doc2RelativeLayout, addressRelativeLayout,consentRelativeLayout,phoneRelativeLayout,backgroundsRelativeLayout;
    private ImageView faceCheckImageView, docCheckImageView,doc2CheckImageView, addressCheckImageView,consentCheckImageView,phoneCheckImageView,backgroundCheckImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false;
    private boolean isAddressChecked = false;
    private boolean isDoc2Checked = false;
    private boolean isConsentChecked = false;
    private boolean isPhoneChecked = false;
    private boolean isBgChecked = false;
    private Button continueButton;
    private String clientId = ""; //Set your client Id.
    private String secretKey = ""; //Set your secret key.
    private String accessToken = "";

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
        doc2RelativeLayout = findViewById(R.id.doc2RelativeLayout);
        phoneRelativeLayout = findViewById(R.id.phoneRelativeLayout);
        backgroundsRelativeLayout = findViewById(R.id.bgRelativeLayout);

        faceCheckImageView = findViewById(R.id.faceCheckImageView);
        docCheckImageView = findViewById(R.id.docCheckImageView);
        addressCheckImageView = findViewById(R.id.addressCheckImageView);
        consentCheckImageView = findViewById(R.id.consentCheckImageView);
        phoneCheckImageView = findViewById(R.id.phoneCheckImageView);
        backgroundCheckImageView = findViewById(R.id.bgCheckImageView);
        doc2CheckImageView = findViewById(R.id.doc2CheckImageView);

        continueButton = findViewById(R.id.continueButton);

        //Setting click listeners for the layouts
        faceRelativeLayout.setOnClickListener(this);
        docRelativeLayout.setOnClickListener(this);
        addressRelativeLayout.setOnClickListener(this);
        doc2RelativeLayout.setOnClickListener(this);
        consentRelativeLayout.setOnClickListener(this);
        phoneRelativeLayout.setOnClickListener(this);
        backgroundsRelativeLayout.setOnClickListener(this);
        continueButton.setOnClickListener(this);

        // optional
     //   accessToken = this.getAccessToken();

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

        if (v == doc2RelativeLayout) {
            if (!isDoc2Checked) {
                isDoc2Checked = true;
                doc2CheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isDoc2Checked = false;
                doc2CheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
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

        if (v == phoneRelativeLayout) {
            if (!isPhoneChecked) {
                isPhoneChecked = true;
                phoneCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isPhoneChecked = false;
                phoneCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }

        if (v == backgroundsRelativeLayout) {
            if (!isBgChecked) {
                isBgChecked = true;
                backgroundCheckImageView.setImageResource(R.drawable.check_radio_icon);
            } else {
                isBgChecked = false;
                backgroundCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
            }
        }

        if (v == continueButton) {

            //If none of verification is requested display alert message
            if (!isFaceChecked && !isDocChecked && !isAddressChecked && !isDoc2Checked && !isConsentChecked && !isPhoneChecked && !isBgChecked) {
                Helpers.displayAlertMessage(this, getString(R.string.select_service));
            } else {
                requestSDKForVerification();
            }
        }
    }

    private void requestSDKForVerification() {
        if (accessToken.isEmpty() && (clientId.isEmpty() || secretKey.isEmpty())) {
            showErrorMessageDialog(getString(R.string.provide_credentials));
            return;
        }

        sendVerificationRequest();
    }

    private void sendVerificationRequest() {


        Shuftipro shuftipro = Shuftipro.getInstance();


        JSONObject AuthKeys = new JSONObject();
        try {
            AuthKeys.put("auth_type","basic_auth");
            AuthKeys.put("client_Id", clientId);
            AuthKeys.put("secret_key",secretKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject Config=new JSONObject();
        try {
            Config.put("open_webview",false);
            Config.put("asyncRequest",false);
            Config.put("captureEnabled",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final JSONObject requestObj=getRequestObject();

        if(requestObj!=null)
        {
            shuftipro.shuftiproVerification(requestObj,AuthKeys, Config,HomeActivity.this, new ShuftiVerifyListener() {
                @Override
                public void verificationStatus(HashMap<String, String> responseSet) {

                    Log.e("Response",responseSet.toString());
                    
                    uncheckAllOptions();

                }
            });
        }
    }
    
    public JSONObject getRequestObject()
    {
        JSONObject jsonObject = new JSONObject();

        final String reference = Utils.getUniqueReference(this);

        try {
            jsonObject.put("reference", reference);
            jsonObject.put("country", "US");
            jsonObject.put("language", "EN");
            jsonObject.put("email", "");
            jsonObject.put("callback_url", "");
            jsonObject.put("redirect_url", "");
            jsonObject.put("verification_mode", "image_only");
            jsonObject.put("show_privacy_policy","1");
            jsonObject.put("show_results", "1");
            jsonObject.put("show_consent","1");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Face object

        JSONObject faceObject = new JSONObject();

        try {

            faceObject.put("proof", "");

            if(isFaceChecked)
            {
                jsonObject.put("face", faceObject);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Document object

        JSONObject documentObject = new JSONObject();
        ArrayList<String> doc_supported_types = new ArrayList<String>();

        doc_supported_types.add("passport");
        doc_supported_types.add("id_card");
        doc_supported_types.add("driving_license");
        doc_supported_types.add("credit_or_debit_card");

        try {

            documentObject.put("proof", "");
            documentObject.put("additional_proof", "");
            documentObject.put("name", "");
            documentObject.put("dob", "");
            documentObject.put("document_number", "");
            documentObject.put("expiry_date", "");
            documentObject.put("issue_date", "");
            documentObject.put("backside_proof_required", "0");
            documentObject.put("supported_types",new JSONArray(doc_supported_types));

            if(isDocChecked)
            {
                jsonObject.put("document", documentObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Creating Document Two Object

        JSONObject documentTwoObject = new JSONObject();
        ArrayList<String> doc_two_supported_types = new ArrayList<String>();

        doc_two_supported_types.add("passport");
        doc_two_supported_types.add("id_card");
        doc_two_supported_types.add("driving_license");

        try {

            documentTwoObject.put("proof", "");
            documentTwoObject.put("additional_proof", "");
            documentTwoObject.put("name", "");
            documentTwoObject.put("dob", "");
            documentTwoObject.put("document_number", "");
            documentTwoObject.put("expiry_date", "");
            documentTwoObject.put("issue_date", "");
            documentTwoObject.put("backside_proof_required", "0");
            documentTwoObject.put("supported_types",new JSONArray(doc_two_supported_types));
            documentTwoObject.put("gender", "");

            if(isDoc2Checked)
            {
                jsonObject.put("document_two", documentTwoObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Creating Address object

        JSONObject addressObject = new JSONObject();
        ArrayList<String> address_supported_types = new ArrayList<String>();

        address_supported_types.add("id_card");
        address_supported_types.add("passport");
        address_supported_types.add("driving_license");

        address_supported_types.add("bank_statement");
        address_supported_types.add("utility_bill");
        address_supported_types.add("rent_agreement");

        try {
            addressObject.put("proof", "");
            addressObject.put("full_address", "");
            addressObject.put("name", "");
            addressObject.put("supported_types",new JSONArray(address_supported_types));

            if(isAddressChecked)
            {
                jsonObject.put("address", addressObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Creating consent object

        JSONObject consentObject = new JSONObject();
        ArrayList<String> consent_supported_types = new ArrayList<String>();
        consent_supported_types.add("handwritten");
//        consent_supported_types.add("printed");

        try {
            consentObject.put("proof", "");
            consentObject.put("text", "This is my consent test");
            consentObject.put("supported_types",new JSONArray(consent_supported_types));

            if(isConsentChecked)
            {
                jsonObject.put("consent", consentObject);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating phone object

        try {
            if(isPhoneChecked)
            {
                jsonObject.put("phone", "");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating BGC object

        try {
            if(isBgChecked)
            {
                jsonObject.put("background_checks", "");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jsonObject;
    }


    private void uncheckAllOptions()
    {
        isFaceChecked = false;
        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isAddressChecked = false;
        addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocChecked = false;
        docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDoc2Checked=false;
        doc2CheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isConsentChecked=false;
        consentCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isPhoneChecked=false;
        phoneCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isBgChecked=false;
        backgroundCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
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


    private String getAccessToken() {
        String accessToken = "";

        // implement logic to get accessToken from server side


        return accessToken;
    }

}
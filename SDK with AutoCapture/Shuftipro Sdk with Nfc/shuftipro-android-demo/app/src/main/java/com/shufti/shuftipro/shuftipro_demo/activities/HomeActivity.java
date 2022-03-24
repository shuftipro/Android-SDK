package com.shufti.shuftipro.shuftipro_demo.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout faceRelativeLayout, docRelativeLayout, docTwoRelativeLayout, addressRelativeLayout, consentRelativeLayout,phoneRelativeLayout,backgroundsRelativeLayout;
    private ImageView faceCheckImageView, docCheckImageView, docTwoCheckImageView, addressCheckImageView, consentCheckImageView,phoneCheckImageView,backgroundCheckImageView;
    private ImageView docNfcImageView, docTwoNfcImageView, addressNfcImageView;
    private boolean isFaceChecked = false;
    private boolean isDocChecked = false, isDocNfc = false;
    private boolean isDocTwoChecked = false, isDocTwoNfc = false;
    private boolean isAddressChecked = false, isAddressNfc = false;
    private boolean isConsentChecked = false;
    private Button continueButton;
    TextView faceTextView, docTextView, docTwoTextView, addressTextView, consentTextView;
    ConstraintLayout docNfcLayout, docTwoNfcLayout, addressNfcLayout;
    NfcAdapter adapter;
    private boolean isPhoneChecked = false;
    private boolean isBgChecked = false;
    private String clientId = ""; //Set your client Id here
    private String secretKey = ""; //Set your secret key here.
    private String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Initializing views
        adapter = NfcAdapter.getDefaultAdapter(this);

        faceRelativeLayout = findViewById(R.id.faceRelativeLayout);
        docRelativeLayout = findViewById(R.id.docRelativeLayout);
        docTwoRelativeLayout = findViewById(R.id.docTwoRelativeLayout);
        addressRelativeLayout = findViewById(R.id.addressRelativeLayout);
        consentRelativeLayout = findViewById(R.id.consentRelativeLayout);
        phoneRelativeLayout = findViewById(R.id.phoneRelativeLayout);
        backgroundsRelativeLayout = findViewById(R.id.bgRelativeLayout);
        faceCheckImageView = findViewById(R.id.faceCheckImageView);
        docCheckImageView = findViewById(R.id.docCheckImageView);
        docTwoCheckImageView = findViewById(R.id.docTwoCheckImageView);
        addressCheckImageView = findViewById(R.id.addressCheckImageView);
        consentCheckImageView = findViewById(R.id.consentCheckImageView);
        phoneCheckImageView = findViewById(R.id.phoneCheckImageView);
        backgroundCheckImageView = findViewById(R.id.bgCheckImageView);

        continueButton = findViewById(R.id.continueButton);
        faceTextView = findViewById(R.id.faceTextView);
        docTextView = findViewById(R.id.docTextView);
        docTwoTextView = findViewById(R.id.docTwoTextView);
        addressTextView = findViewById(R.id.addressTextView);
        consentTextView = findViewById(R.id.consentTextView);

        docNfcLayout = findViewById(R.id.docNfcLayout);
        docTwoNfcLayout = findViewById(R.id.docTwoNfcLayout);
        addressNfcLayout = findViewById(R.id.addressNfcLayout);

        docNfcImageView = findViewById(R.id.docNfcImageView);
        docTwoNfcImageView = findViewById(R.id.docTwoNfcImageView);
        addressNfcImageView = findViewById(R.id.addressNfcImageView);

        //Setting click listeners for the layouts
        faceRelativeLayout.setOnClickListener(this);
        docRelativeLayout.setOnClickListener(this);
        docNfcLayout.setOnClickListener(this);
        docTwoNfcLayout.setOnClickListener(this);
        addressNfcLayout.setOnClickListener(this);
        docTwoRelativeLayout.setOnClickListener(this);
        addressRelativeLayout.setOnClickListener(this);
        consentRelativeLayout.setOnClickListener(this);
        phoneRelativeLayout.setOnClickListener(this);
        backgroundsRelativeLayout.setOnClickListener(this);
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

            faceTextView.setTextColor(getResources().getColor(isFaceChecked ? R.color.charcol_blue : R.color.charcol_blue));
        }
        if (v == docRelativeLayout) {
            if (!isDocChecked) {
                if (adapter != null) {
                    docNfcLayout.setVisibility(View.VISIBLE);
                }
                isDocChecked = true;
                docCheckImageView.setImageResource(R.drawable.check_radio_icon);
                addressNfcLayout.setVisibility(View.GONE);
                docTwoNfcLayout.setVisibility(View.GONE);
            } else {
                docNfcLayout.setVisibility(View.GONE);
                isDocChecked = false;
                docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
                isDocNfc = false;
                docNfcImageView.setImageResource(R.drawable.nfc_disable);
            }
            docTextView.setTextColor(getResources().getColor(isDocChecked ? R.color.charcol_blue : R.color.charcol_blue));
        }
        if (v == docNfcLayout) {
            if (!isDocNfc) {
                isDocNfc = true;
                docNfcImageView.setImageResource(R.drawable.nfc_enable);
                docTwoNfcLayout.setVisibility(View.GONE);
            } else {
                isDocNfc = false;
                docNfcImageView.setImageResource(R.drawable.nfc_disable);
            }
        }
        if (v == docTwoRelativeLayout) {
            if (!isDocTwoChecked) {
                if (adapter != null) {
                    docTwoNfcLayout.setVisibility(View.VISIBLE);
                }
                isDocTwoChecked = true;
                docTwoCheckImageView.setImageResource(R.drawable.check_radio_icon);
                addressNfcLayout.setVisibility(View.GONE);
                docNfcLayout.setVisibility(View.GONE);
            } else {
                docTwoNfcLayout.setVisibility(View.GONE);
                isDocTwoChecked = false;
                docTwoCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
                isDocTwoNfc = false;
                docTwoNfcImageView.setImageResource(R.drawable.nfc_disable);
            }
            docTwoTextView.setTextColor(getResources().getColor(isDocTwoChecked ? R.color.charcol_blue : R.color.charcol_blue));
        }
        if (v == docTwoNfcLayout) {
            if (!isDocTwoNfc) {
                isDocTwoNfc = true;
                docTwoNfcImageView.setImageResource(R.drawable.nfc_enable);
            } else {
                isDocTwoNfc = false;
                docTwoNfcImageView.setImageResource(R.drawable.nfc_disable);
            }
        }
        if (v == addressRelativeLayout) {
            if (!isAddressChecked) {
                if (adapter != null) {
                    addressNfcLayout.setVisibility(View.VISIBLE);
                }
                isAddressChecked = true;
                addressCheckImageView.setImageResource(R.drawable.check_radio_icon);
                docTwoNfcLayout.setVisibility(View.GONE);
                docNfcLayout.setVisibility(View.GONE);
            } else {
                addressNfcLayout.setVisibility(View.GONE);
                isAddressChecked = false;
                addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
                isAddressNfc = false;
                addressNfcImageView.setImageResource(R.drawable.nfc_disable);
            }

            addressTextView.setTextColor(getResources().getColor(isAddressChecked ? R.color.charcol_blue : R.color.charcol_blue));
        }
        if (v == addressNfcLayout) {
            if (!isAddressNfc) {
                isAddressNfc = true;
                addressNfcImageView.setImageResource(R.drawable.nfc_enable);
            } else {
                isAddressNfc = false;
                addressNfcImageView.setImageResource(R.drawable.nfc_disable);
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

            consentTextView.setTextColor(getResources().getColor(isConsentChecked ? R.color.charcol_blue : R.color.charcol_blue));
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
            if (!isFaceChecked && !isDocChecked && !isDocTwoChecked && !isAddressChecked && !isConsentChecked && !isPhoneChecked && !isBgChecked) {
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
            Config.put("autoCapture",true);
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

    public JSONObject getRequestObject() {
        JSONObject jsonObject = new JSONObject();

        final String reference = Utils.getUniqueReference(this);

        try {
            jsonObject.put("reference", reference);
            jsonObject.put("country", "");
            jsonObject.put("language", "EN");
            jsonObject.put("email", "");
            jsonObject.put("callback_url", "");
            jsonObject.put("redirect_url", "");
            jsonObject.put("verification_mode", "image_only");
            jsonObject.put("show_privacy_policy","1");
            jsonObject.put("show_results", "1");
            jsonObject.put("show_consent", "1");
            jsonObject.put("allow_online","1");
            jsonObject.put("allow_offline","1");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Face object

        JSONObject faceObject = new JSONObject();

        try {

            faceObject.put("proof", "");
            faceObject.put("allow_online","1");
            faceObject.put("allow_offline","1");

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
            documentObject.put("allow_online","1");
            documentObject.put("allow_offline","1");
            if (isDocNfc) {
                documentObject.put("nfc_verification", true);
            }
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
            documentTwoObject.put("allow_online","1");
            documentTwoObject.put("allow_offline","1");
            if (isDocTwoNfc) {
                documentTwoObject.put("nfc_verification", true);
            }
            if(isDocTwoChecked)
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
            addressObject.put("allow_online","1");
            addressObject.put("allow_offline","1");
            if (isAddressNfc) {
                addressObject.put("nfc_verification", true);
            }
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
        consent_supported_types.add("printed");

        try {
            consentObject.put("proof", "");
            consentObject.put("text", "This is my consent test");
            consentObject.put("supported_types",new JSONArray(consent_supported_types));
            consentObject.put("allow_online","1");
            consentObject.put("allow_offline","1");

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

    private void uncheckAllOptions() {
        isFaceChecked = false;
        faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isAddressChecked = false;
        addressCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocChecked = false;
        docCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocTwoChecked = false;
        docTwoCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isConsentChecked = false;
        consentCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isPhoneChecked = false;
        phoneCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isBgChecked = false;
        backgroundCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        isDocNfc = false;
        docNfcLayout.setVisibility(View.GONE);
        docNfcImageView.setImageResource(R.drawable.nfc_disable);
        isDocTwoNfc = false;
        docTwoNfcLayout.setVisibility(View.GONE);
        docTwoNfcImageView.setImageResource(R.drawable.nfc_disable);
        isAddressNfc = false;
        addressNfcLayout.setVisibility(View.GONE);
        addressNfcImageView.setImageResource(R.drawable.nfc_disable);
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
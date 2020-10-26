
package com.shufti.shuftipro.shuftipro_demo.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.shufti.shuftipro.shuftipro_demo.MyDatePickerDialog;
import com.shufti.shuftipro.shuftipro_demo.R;
import com.shutipro.sdk.Shuftipro;
import com.shutipro.sdk.listeners.ShuftiVerifyListener;
import com.shutipro.sdk.models.AddressVerification;
import com.shutipro.sdk.models.ConsentVerification;
import com.shutipro.sdk.models.DocumentVerification;
import com.shutipro.sdk.models.FaceVerification;
import com.shutipro.sdk.models.ShuftiproVerification;
import com.shutipro.sdk.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout faceRelativeLayout;
    private boolean isFaceChecked = false, isDocChecked = false, isAddressChecked = false, isConsentChecked = false;
    private boolean isToVerifyName = false;
    private Button continueButton;
    private String firstName, lastName, dob, documentNumber, issueDate, expiryDate, fullAddress, consentText;

    private String clientId = "L3nF3hZYrV07itEna5GWaWJbuABlaFfWO8FlecT0ucXrWcbHQJ1595921709"; //Set your client Id here
    private String secretKey = "$2y$10$/Vpw4S5iP2S2Mp8HonS3surtfJe0aS4x4jNmtKRq4nPRCpDdp06/2"; //Set your secret key here.
    private String accessToken = "";


    LinearLayout address_collapse_view, consent_collapse_view, document_collapse_view, dob_layour, issuedate_layout, expirydate_layout;
    ImageView address_btn, consent_btn,document_btn;
    String DateOfBirth="",IssueDate="",ExpiryDate="";
    TextView issuedate_tv, dob_tv, expirydate_tv;
    EditText firstname_et, lastname_et, documentno_et, consent_et, address_et;
    ImageView face_checkbox, document_checkbox, address_checkbox, consent_checkbox;
    boolean face_key=false, document_key=false, address_key=false, consent_key=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Initializing views
        continueButton = findViewById(R.id.continueButton);

        ///After Update


        address_collapse_view=findViewById(R.id.address_collapse_view);
        consent_collapse_view=findViewById(R.id.consent_collapse_view);
        document_collapse_view=findViewById(R.id.document_collapse_view);

        dob_layour=findViewById(R.id.dob_layout);
        issuedate_layout=findViewById(R.id.issuedate_layout);
        expirydate_layout=findViewById(R.id.expirydate_layout);


        address_btn=findViewById(R.id.address_btn);
        consent_btn=findViewById(R.id.consent_btn);
        document_btn=findViewById(R.id.document_btn);

        firstname_et=findViewById(R.id.firstname_et);
        lastname_et=findViewById(R.id.lastname_et);
        documentno_et=findViewById(R.id.documentno_et);
        consent_et=findViewById(R.id.consent_et);
        address_et=findViewById(R.id.address_et);


        issuedate_tv=findViewById(R.id.issuedate_tv);
        dob_tv=findViewById(R.id.dob_tv);
        expirydate_tv=findViewById(R.id.expirydate_tv);


        face_checkbox=findViewById(R.id.face_checkbox);
        document_checkbox=findViewById(R.id.document_checkbox);
        address_checkbox=findViewById(R.id.address_checkbox);
        consent_checkbox=findViewById(R.id.consent_checkbox);



        address_btn.setOnClickListener(this);
        consent_btn.setOnClickListener(this);
        document_btn.setOnClickListener(this);

        dob_layour.setOnClickListener(this);
        issuedate_layout.setOnClickListener(this);
        expirydate_layout.setOnClickListener(this);


        face_checkbox.setOnClickListener(this);
        document_checkbox.setOnClickListener(this);
        address_checkbox.setOnClickListener(this);
        consent_checkbox.setOnClickListener(this);







        //End Update

        //Setting click listeners for the layouts
        continueButton.setOnClickListener(this);

        // optional
        accessToken = this.getAccessToken();
    }

    @Override
    public void onClick(View v) {



        if(v==document_btn)
        {
            if (!document_key)
            {
                document_key = true;
                document_collapse_view.setVisibility(View.VISIBLE);
                document_btn.setImageResource(R.drawable.minus_checkbox);
            } else
            {
                document_key = false;
                document_collapse_view.setVisibility(View.GONE);
                document_btn.setImageResource(R.drawable.plus_checkbox);


                if(firstname_et.getText().toString().length()>0 || lastname_et.getText().toString().length()>0 || documentno_et.getText().toString().length()>0 || DateOfBirth.length()>0 || IssueDate.length()>0 || ExpiryDate.length()>0)
                {
                    document_checkbox.setImageResource(R.drawable.checked_checkbox);
                    isDocChecked=true;
                }
                else
                {
                    document_checkbox.setImageResource(R.drawable.empty_checkbox);
                    isDocChecked=false;
                }

            }
        }

        if(v==address_btn)
        {
            if (!address_key) {
                address_key = true;
                address_collapse_view.setVisibility(View.VISIBLE);
                address_btn.setImageResource(R.drawable.minus_checkbox);
            } else {
                address_key = false;
                address_collapse_view.setVisibility(View.GONE);
                address_btn.setImageResource(R.drawable.plus_checkbox);

                if(!address_et.getText().toString().isEmpty())
                {
                    address_checkbox.setImageResource(R.drawable.checked_checkbox);
                    isAddressChecked=true;
                }
                else
                {
                    address_checkbox.setImageResource(R.drawable.empty_checkbox);
                    isAddressChecked=false;
                }
            }
        }

        if(v==consent_btn)
        {
            if (!consent_key) {
                consent_key = true;
                consent_collapse_view.setVisibility(View.VISIBLE);
                consent_btn.setImageResource(R.drawable.minus_checkbox);
            } else {
                consent_key = false;
                consent_collapse_view.setVisibility(View.GONE);
                consent_btn.setImageResource(R.drawable.plus_checkbox);

                if(!consent_et.getText().toString().isEmpty())
                {
                    consent_checkbox.setImageResource(R.drawable.checked_checkbox);
                    isConsentChecked=true;
                }
                else
                {
                    consent_checkbox.setImageResource(R.drawable.empty_checkbox);
                    isConsentChecked=false;
                }
            }
        }


        if(v==face_checkbox)
        {
            if (!isFaceChecked)
            {
                isFaceChecked = true;
                face_checkbox.setImageResource(R.drawable.checked_checkbox);
            } else {
                isFaceChecked = false;
                face_checkbox.setImageResource(R.drawable.empty_checkbox);
            }
        }

        if(v==document_checkbox)
        {
            if (!isDocChecked)
            {
                if(firstname_et.getText().toString().length()>0 && lastname_et.getText().toString().length()>0 && documentno_et.getText().toString().length()>0 && DateOfBirth.length()>0 && IssueDate.length()>0 && ExpiryDate.length()>0)
                {
                    isDocChecked = true;
                    document_checkbox.setImageResource(R.drawable.checked_checkbox);
                }

            }
            else {
                isDocChecked = false;
                document_checkbox.setImageResource(R.drawable.empty_checkbox);

                firstname_et.getText().clear();
                lastname_et.getText().clear();
                documentno_et.getText().clear();
                dob_tv.setText("");
                issuedate_tv.setText("");
                expirydate_tv.setText("");

            }

        }
        if(v==address_checkbox)
        {
            if(!isAddressChecked)
            {
                if(address_et.getText().toString().length()>0)
                {
                    isAddressChecked = true;
                    address_checkbox.setImageResource(R.drawable.checked_checkbox);
                }
            }
            else {
                isAddressChecked=false;
                address_checkbox.setImageResource(R.drawable.empty_checkbox);

                address_et.getText().clear();
            }

        }


        if(v==consent_checkbox)
        {

            if(!isConsentChecked)
            {
                if(consent_et.getText().toString().length()>0)
                {
                    isConsentChecked=true;
                    consent_checkbox.setImageResource(R.drawable.checked_checkbox);
                }

            }
            else {
                isConsentChecked=false;
                consent_checkbox.setImageResource(R.drawable.empty_checkbox);

                consent_et.getText().clear();
            }

        }




        if(v==dob_layour)
        {
            DatePicker(0);
        }

        if(v==issuedate_layout)
        {
            DatePicker(1);
        }

        if(v==expirydate_layout)
        {
            DatePicker(2);
        }


        if (v == continueButton) {

            firstName = firstname_et.getText().toString();
            lastName = lastname_et.getText().toString();
            dob = DateOfBirth;
            documentNumber = documentno_et.getText().toString();
            issueDate = IssueDate;
            expiryDate = ExpiryDate;
            fullAddress = address_et.getText().toString();
            consentText = consent_et.getText().toString();

            //If none of verification is requested display alert message
            if (!isFaceChecked && firstName.isEmpty() && lastName.isEmpty() && dob.isEmpty() && documentNumber.isEmpty()
                    && issueDate.isEmpty() && expiryDate.isEmpty() && fullAddress.isEmpty() && consentText.isEmpty()) {
                showErrorMessageDialog(getString(R.string.methods_of_verifications));
                return;
            }

            //Check if user has check for document verification
            if (!firstName.isEmpty() || !lastName.isEmpty() || !dob.isEmpty() || !documentNumber.isEmpty() ||
                    !issueDate.isEmpty() || !expiryDate.isEmpty()) {

                isDocChecked = true;
                if (!firstName.isEmpty()) {
                    isToVerifyName = true;
                }
            } else {
                isDocChecked = false;
            }

            //Check if user has check for document verification
            isAddressChecked = !fullAddress.isEmpty();

            //Check if user has check for consent verification
            isConsentChecked = !consentText.isEmpty();
            requestSDKForVerification();

        }
    }



    private void DatePicker(final int type)
    {

        MyDatePickerDialog dialog = new MyDatePickerDialog(this);
        dialog.setTitle("Set Date");
        dialog.showDatePicker(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthUpdate = 0;
                String monthString="";
                String dayString="";
                monthUpdate=month+1;

                monthString = String.valueOf(monthUpdate);
                dayString = String.valueOf(dayOfMonth);


                if(monthUpdate<10)
                {
                    monthString="0"+monthUpdate;
                }

                if(dayOfMonth<10)
                {
                    dayString="0"+dayOfMonth;
                }

                if(type==0)
                {
                    DateOfBirth=year+"-"+monthString+"-"+dayString;
                    dob_tv.setText(DateOfBirth);
                }
                else if(type==1)
                {
                    IssueDate=year+"-"+monthString+"-"+dayString;
                    issuedate_tv.setText(IssueDate);
                }
                else if(type==2)
                {
                    ExpiryDate=year+"-"+monthString+"-"+dayString;
                    expirydate_tv.setText(ExpiryDate);
                }
                //Date select callback
            }
        }, Calendar.getInstance());

    }


    private void requestSDKForVerification() {
        if (accessToken.isEmpty() && (clientId.isEmpty() || secretKey.isEmpty())) {
            showErrorMessageDialog(getString(R.string.provide_credentials));
            return;
        }
        sendVerificationRequest();
    }

    private void sendVerificationRequest() {

        final String country = "";
        final String lng = "EN";
        final String email = "yourmail@gmail.com";
        final String callback_url = "https://www.yourdomain.com";
        final String redirect_url = "https://www.yourdomain.com";
        final String verification_mode = "video";

        //Get unique reference using SDK utils (You can use your own reference)
        final String reference = Utils.getUniqueReference(this);

        /*
         * FOR FACE VERIFICATION SERVICE
         * Make an instance and set the face verification to true
         */

        FaceVerification faceVerification = FaceVerification.getInstance();
        faceVerification.setFaceVerificationService(true);

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
        documentVerification.setFirstName(firstName);
        documentVerification.setMiddleName("");
        documentVerification.setLastName(lastName);
        documentVerification.setDob(dob);
        documentVerification.setDocumentNumber(documentNumber);
        documentVerification.setIssueDate(issueDate);
        documentVerification.setExpiryDate(expiryDate);
        //  documentVerification.setFetchEnhancedData(true);

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
        addressVerification.setFullAddress(fullAddress);
        addressVerification.setFirstName(firstName);
        addressVerification.setMiddleName("");
        addressVerification.setLastName(lastName);
        addressVerification.setFuzzyMatch(false);

        /*
         * FOR CONSENT VERIFICATION SERVICE
         * Make an instance, set the supported types & required fields for verification
         */

        ConsentVerification consentVerification = ConsentVerification.getInstance();
        ArrayList<String> consent_supported_types = new ArrayList<>();
        consent_supported_types.add("handwritten");
        consent_supported_types.add("printed");

        consentVerification.setSupportedTypes(consent_supported_types);
        consentVerification.setConsentText(consentText);

        //Make an instance and method call
        Shuftipro instance;

        if (clientId.isEmpty() || secretKey.isEmpty()) {
            instance = Shuftipro.getInstance(accessToken, false);
        } else {
            instance = Shuftipro.getInstance(clientId, secretKey, false);
        }


        ShuftiproVerification.RequestBuilder requestBuilder = new ShuftiproVerification.RequestBuilder(reference, country, callback_url,
                this, verification_mode, new ShuftiVerifyListener() {
            @Override
            public void verificationStatus(HashMap<String, String> responseSet) {
                Log.e("Response", responseSet.toString());
                uncheckAllOptions();
            }
        });

        requestBuilder.withFaceVerification(isFaceChecked ? faceVerification : null);
        requestBuilder.withAddressVerification(isAddressChecked ? addressVerification : null);
        requestBuilder.withDocumentVerification(isDocChecked ? documentVerification : null);
        requestBuilder.withConsentVerification(isConsentChecked ? consentVerification : null);
        requestBuilder.withEmail(email);
        requestBuilder.withLanguage(lng);
        requestBuilder.withRedirectUrl(redirect_url);
        instance.shuftiproVerification(requestBuilder.buildShuftiModel());
    }

    //Deselect all of the pre selected values
    private void uncheckAllOptions() {
        isFaceChecked = false;
        isDocChecked = false;
        isAddressChecked = false;
        isToVerifyName = false;
        isConsentChecked=false;
        //    faceCheckImageView.setImageResource(R.drawable.uncheck_radio_icon);
        resetAllFields();
    }

    //Display an alert dialog to show the error messages
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

    private void resetAllFields() {

        firstname_et.setText("");
        lastname_et.setText("");
        documentno_et.setText("");
        consent_et.setText("");
        address_et.setText("");
        issuedate_tv.setText("");
        dob_tv.setText("");
        expirydate_tv.setText("");

        document_checkbox.setImageResource(R.drawable.empty_checkbox);
        address_checkbox.setImageResource(R.drawable.empty_checkbox);
        consent_checkbox.setImageResource(R.drawable.empty_checkbox);
        face_checkbox.setImageResource(R.drawable.empty_checkbox);
    }



    /**
     *
     * @Optional | clientId + secretKey can also be used instead accessToken
     *
     * return access token
     */
    private String getAccessToken() {
        String accessToken = "fjdabf";

        // implement logic to get accessToken from server side


        return accessToken;
    }
}

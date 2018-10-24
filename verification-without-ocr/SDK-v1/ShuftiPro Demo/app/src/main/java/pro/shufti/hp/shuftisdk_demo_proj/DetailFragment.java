package pro.shufti.hp.shuftisdk_demo_proj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.percent.PercentRelativeLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shufti.pro.sdk.Shuftipro;
import com.shufti.pro.sdk.interfaces.ShuftiVerifyListener;


import java.util.Calendar;
import java.util.HashMap;

import pro.shufti.hp.shuftisdk_demo_proj.helpers.FontHelper;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by HP on 4/1/2018.
 */

public class DetailFragment extends android.support.v4.app.Fragment implements View.OnClickListener, ShuftiVerifyListener,
        DatePickerDialog.OnDateSetListener {

    private Context context;
    private EditText etCountryName;
    private EditText etCreditCard6Digit;
    private EditText etCreditLast4Digit;
    private EditText etPhoneNumber;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etDob;
    private Button btnVerify;
    private String method;
    private Shuftipro instance;
    private String cardFirst6Digit;
    private String cardLast4Digit;
    private String firstName;
    private String lastName;
    private String dob;
    private String country;
    private String phoneNumber;
    private RelativeLayout rlMain;
    private FloatingActionButton iconButton;
    private TextView tvVerifyType;
    private TextView tvCustomerDetails;
    private View dobBlocker;
    private ImageView ivCross;
    private View viewBlocker;
    private boolean isPass;
    private PercentRelativeLayout rlFn;
    private PercentRelativeLayout rlLn;
    private PercentRelativeLayout rlCountryName;
    private PercentRelativeLayout rlCardFirstSix;
    private PercentRelativeLayout rlCardLastFour;
    private RelativeLayout rlGradientLayout;
    private RelativeLayout rlTopMost;
    private int gradHeight = 0;
    private int fabHeight = 0;
    int es = 0;

    private String clientId = "YOUR_CLIENT_ID";
    private String secretKey = "YOUR_SECRET_KEY";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_fragment_layout_new, container, false);

        method = getArguments().getString(MainActivity.key_data);

        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etDob = view.findViewById(R.id.et_dob);
        etCountryName = view.findViewById(R.id.et_country_name);
        etCreditCard6Digit = view.findViewById(R.id.et_card_first_six_digit);
        etCreditLast4Digit = view.findViewById(R.id.et_card_last_four_digit);
        etPhoneNumber = view.findViewById(R.id.et_phone_no);
        btnVerify = view.findViewById(R.id.btn_verify);
        tvCustomerDetails = view.findViewById(R.id.tv_cust_details);
        tvVerifyType = view.findViewById(R.id.tv_verify_type);
        iconButton = view.findViewById(R.id.icon_button);
        dobBlocker = view.findViewById(R.id.dob_et_blocker);
        ivCross = view.findViewById(R.id.iv_cross);
        viewBlocker = view.findViewById(R.id.view_blocker);
        rlGradientLayout = view.findViewById(R.id.rl_top_gradient);
        rlTopMost = view.findViewById(R.id.rl_top_most);
        rlFn = view.findViewById(R.id.rl_fn);
        rlLn = view.findViewById(R.id.rl_ln);
        rlCountryName = view.findViewById(R.id.rl_country_name);
        rlCardFirstSix = view.findViewById(R.id.rl_card_first_six);
        rlCardLastFour = view.findViewById(R.id.rl_card_last_four);

        etFirstName.setTypeface(FontHelper.getFuturaFont(context));
        etLastName.setTypeface(FontHelper.getFuturaFont(context));
        etDob.setTypeface(FontHelper.getFuturaFont(context));
        etCountryName.setTypeface(FontHelper.getFuturaFont(context));
        etCreditCard6Digit.setTypeface(FontHelper.getFuturaFont(context));
        etCreditLast4Digit.setTypeface(FontHelper.getFuturaFont(context));
        etPhoneNumber.setTypeface(FontHelper.getFuturaFont(context));
        btnVerify.setTypeface(FontHelper.getFuturaFont(context));
        tvCustomerDetails.setTypeface(FontHelper.getFuturaFont(context));
        tvVerifyType.setTypeface(FontHelper.getFuturaFont(context));


        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                etPhoneNumber.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int ts = (int)etPhoneNumber.getTextSize();
                es = etPhoneNumber.getHeight(); //height is ready
                int padding = es - ts;
                padding = padding/3;
                Log.d("Size","Size : " + ts +  " : " + es);
                etPhoneNumber.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
                etFirstName.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
                etLastName.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
                etCountryName.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
                etCreditCard6Digit.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
                etCreditLast4Digit.setPadding(convertDpToPx(10),0,convertDpToPx(10),padding);
            }
        });


        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlGradientLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                gradHeight = rlGradientLayout.getHeight(); //height is ready
            }
        });

        iconButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlGradientLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                fabHeight = iconButton.getHeight(); //height is ready

                int marginTop = gradHeight - (fabHeight/2);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iconButton.getLayoutParams();
                layoutParams.setMargins(0,marginTop,0,0);
                iconButton.setLayoutParams(layoutParams);
                rlTopMost.setVisibility(View.GONE);
            }
        });

        viewBlocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.clearFocus();
                    hideKeyboard();
                    showDatePicker();
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        dobBlocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        rlMain = view.findViewById(R.id.rl_main);
        rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        if (method.equals("credit_card")) {
            tvVerifyType.setText("Credit/Debit Card Verification");
            iconButton.setImageResource(R.drawable.ic_credit_card);
            etFirstName.setVisibility(View.GONE);
            etLastName.setVisibility(View.GONE);
            etDob.setVisibility(View.GONE);
            dobBlocker.setVisibility(View.GONE);
            rlFn.setVisibility(View.GONE);
            rlLn.setVisibility(View.GONE);

        } else if (method.equals("driving_license")) {
            tvVerifyType.setText("Driving Licence Verification");
            iconButton.setImageResource(R.drawable.ic_card_driving);
            etCreditCard6Digit.setVisibility(View.GONE);
            etCreditLast4Digit.setVisibility(View.GONE);
            rlCardFirstSix.setVisibility(View.GONE);
            rlCardLastFour.setVisibility(View.GONE);

        } else if (method.equals("id_card")) {
            tvVerifyType.setText("ID Card Verification");
            iconButton.setImageResource(R.drawable.ic_card_id);
            etCreditCard6Digit.setVisibility(View.GONE);
            etCreditLast4Digit.setVisibility(View.GONE);
            rlCardFirstSix.setVisibility(View.GONE);
            rlCardLastFour.setVisibility(View.GONE);

        } else if (method.equals("passport")) {
            tvVerifyType.setText("Passport Verification");
            iconButton.setImageResource(R.drawable.ic_passport);
            etCreditCard6Digit.setVisibility(View.GONE);
            etCreditLast4Digit.setVisibility(View.GONE);
            rlCardFirstSix.setVisibility(View.GONE);
            rlCardLastFour.setVisibility(View.GONE);
        }

        instance = Shuftipro.getInstance(clientId, secretKey);
        btnVerify.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_verify:

                isPass = true;

                if (method.equals("credit_card")) {
                    cardFirst6Digit = etCreditCard6Digit.getText().toString();
                    cardLast4Digit = etCreditLast4Digit.getText().toString();
                    country = etCountryName.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();

                    if (country.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Country code cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (country.length() < 2) {
                        isPass = false;
                        Toast.makeText(context, "Please enter country code minimum 2 letters...", Toast.LENGTH_SHORT).show();
                    } else  if (cardFirst6Digit.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Card first 6 digits cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (cardFirst6Digit.length() < 6 || cardFirst6Digit.length() > 6) {
                        isPass = false;
                        Toast.makeText(context, "Please enter first 6 digits...", Toast.LENGTH_SHORT).show();
                    } else if (cardLast4Digit.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Card last 4 digits cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (cardLast4Digit.length() < 4 || cardLast4Digit.length() > 4) {
                        isPass = false;
                        Toast.makeText(context, "Please enter last 4 digits...", Toast.LENGTH_SHORT).show();
                    }  else if (phoneNumber.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Phone Number cannot be empty...", Toast.LENGTH_SHORT).show();
                    }

                    if (isPass) {
                        instance.creditCardVerification(country, cardFirst6Digit, cardLast4Digit,
                                phoneNumber, getActivity(), this);
                    }

                } else {

                    firstName = etFirstName.getText().toString();
                    lastName = etLastName.getText().toString();
                    dob = etDob.getText().toString();
                    country = etCountryName.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();


                    if (firstName.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "First Name cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (firstName.length() < 2) {
                        isPass = false;
                        Toast.makeText(context, "Please enter minimum first name of 2 characters...", Toast.LENGTH_SHORT).show();
                    } else if (lastName.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Last Name cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (lastName.length() < 2) {
                        isPass = false;
                        Toast.makeText(context, "Please enter minimum last name of 2 characters...", Toast.LENGTH_SHORT).show();
                    } else if (dob.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "DOB cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (country.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Country code cannot be empty...", Toast.LENGTH_SHORT).show();
                    } else if (country.length() < 2) {
                        isPass = false;
                        Toast.makeText(context, "Please enter country code minimum 2 letters...", Toast.LENGTH_SHORT).show();
                    } else if (phoneNumber.isEmpty()) {
                        isPass = false;
                        Toast.makeText(context, "Phone Number cannot be empty...", Toast.LENGTH_SHORT).show();
                    }

                    if (isPass) {
                        instance.documentVerification(method, firstName, lastName, dob, country,
                                phoneNumber, getActivity(), this);
                    }

                }


                break;
        }

    }

    @Override
    public void verificationStatus(HashMap<String, String> responseSet) {
        String status = responseSet.get("status_code");

        if (status.equalsIgnoreCase("SP1")) {
            Log.i("tagResponse", "Verified");
        } else {
            String message = responseSet.get("message");
            Log.i("tagResponse", message);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int i1, int date) {
        int month = i1 + 1;
        if (month < 10 && date < 10) {
            etDob.setText(year + "-0" + month + "-0" + date);
        } else if (month >= 10 && date < 10) {
            etDob.setText(year + "-" + month + "-0" + date);

        } else if (month < 10 && date >= 10) {
            etDob.setText(year + "-0" + month + "-" + date);
        } else {
            etDob.setText(year + "-" + month + "-" + date);
        }

        etCountryName.requestFocus();
        showKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void showDatePicker() {


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,android.R.style.Theme_Holo_Light_Panel,
                DetailFragment.this, 2000, 01, 01);
        final Calendar c = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    private int convertDpToPx(int dp){
        return Math.round(dp*(getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));

    }
}

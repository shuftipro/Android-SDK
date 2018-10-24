package pro.shufti.hp.shuftisdk_demo_proj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pro.shufti.hp.shuftisdk_demo_proj.helpers.FontHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView btnCreditCardVerify;
    private ImageView btnIDVerify;
    private ImageView btnPassportVerify;
    private ImageView btnDrivinhgLiecenseVerify;
    private DetailFragment detailFragment;
    private TextView tvChooseMethod;
    private TextView tvCreditTitle;
    private TextView tvPassportTitle;
    private TextView tvIdCardTitle;
    private TextView tvDrivingTitle;


    private String TAG_FRAGMENT = "tag_sample";
    public static String key_data = "key_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        //AndroidBug5497Workaround.assistActivity(this);

        btnCreditCardVerify = findViewById(R.id.iv_credit_card);
        btnDrivinhgLiecenseVerify = findViewById(R.id.iv_driving_license);
        btnPassportVerify = findViewById(R.id.iv_passport);
        btnIDVerify = findViewById(R.id.iv_id_card);

        tvChooseMethod = findViewById(R.id.tv_choose_method);
        tvCreditTitle = findViewById(R.id.tv_credit_title);
        tvDrivingTitle = findViewById(R.id.tv_driving_license);
        tvPassportTitle = findViewById(R.id.tv_passport);
        tvIdCardTitle = findViewById(R.id.tv_id_card);

        tvChooseMethod.setTypeface(FontHelper.getFuturaFont(this));
        tvCreditTitle.setTypeface(FontHelper.getFuturaFont(this));
        tvDrivingTitle.setTypeface(FontHelper.getFuturaFont(this));
        tvPassportTitle.setTypeface(FontHelper.getFuturaFont(this));
        tvIdCardTitle.setTypeface(FontHelper.getFuturaFont(this));


        btnCreditCardVerify.setOnClickListener(this);
        btnDrivinhgLiecenseVerify.setOnClickListener(this);
        btnPassportVerify.setOnClickListener(this);
        btnIDVerify.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_credit_card:
                Bundle bundle = new Bundle();
                bundle.putString(key_data,"credit_card");
                detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                addFragment(MainActivity.this,detailFragment,
                        R.id.rl_sample_fragment_container,TAG_FRAGMENT);
                break;

            case R.id.iv_driving_license:
                bundle = new Bundle();
                bundle.putString(key_data,"driving_license");
                detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                addFragment(MainActivity.this,detailFragment,
                        R.id.rl_sample_fragment_container,TAG_FRAGMENT);
                break;

            case R.id.iv_id_card:
                bundle = new Bundle();
                bundle.putString(key_data,"id_card");
                detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                addFragment(MainActivity.this,detailFragment,
                        R.id.rl_sample_fragment_container,TAG_FRAGMENT);
                break;

            case R.id.iv_passport:
                bundle = new Bundle();
                bundle.putString(key_data,"passport");
                detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                addFragment(MainActivity.this,detailFragment,
                        R.id.rl_sample_fragment_container,TAG_FRAGMENT);
                break;
        }
    }


    @Override
    public void onBackPressed() {

        if(removeFragment(this,TAG_FRAGMENT)){

        }else{
            this.finish();
        }

    }



    private void addFragment(AppCompatActivity activity, android.support.v4.app.Fragment fragment, int containerId, String fragmentTag){

        android.support.v4.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId,fragment,fragmentTag);
        fragmentTransaction.commit();

    }

    private boolean removeFragment(AppCompatActivity activity,String tag){

        android.support.v4.app.Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment != null) {
            activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            return true;
        }

        return false;
    }
}

package com.example.kotlindemoapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlindemoapp.databinding.ActivityMainBinding
import com.shutipro.sdk.Shuftipro
import com.shutipro.sdk.listeners.ShuftiVerifyListener
import com.shutipro.sdk.utils.Utils
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(){

    private val clientId = ""
    private val secretKey = ""

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        mainBinding.faceLayout.setOnClickListener {
            mainBinding.faceCheck.performClick()
        }

        mainBinding.docLayout.setOnClickListener {
            mainBinding.docCheck.performClick()
        }

        mainBinding.doc2Layout.setOnClickListener {
            mainBinding.doc2Check.performClick()
        }

        mainBinding.addressLayout.setOnClickListener {
            mainBinding.addressCheck.performClick()
        }

        mainBinding.consentLayout.setOnClickListener {
            mainBinding.consentCheck.performClick()
        }


        val authObject = JSONObject()
        authObject.apply {
            put("auth_type", "basic_auth")
            put("client_id", clientId)
            put("secret_key", secretKey)
//            put("access_token", "")
        }



            val shuftiPro = Shuftipro()
            mainBinding.startVerification.setOnClickListener {
                shuftiPro
                    .shuftiproVerification(getRequestObject(),
                        authObject,
                        getConfigObject(),
                        this@MainActivity,
                        ShuftiVerifyListener { responseSet: HashMap<String?, String?> ->
                            Log.e("Response", responseSet.toString())
                            resetParameters()
                            Toast.makeText(this@MainActivity, responseSet.toString(), Toast.LENGTH_SHORT).show()
                        })
            }
    }

    private fun getConfigObject(): JSONObject {
        val configObject = JSONObject()

        configObject.apply { put("async", false)
            put("captureEnabled", false)
            put("open_webview", false)
            put("dark_mode", false)
        }
        return configObject
    }


    private fun getRequestObject(): JSONObject {
        val jsonObject = JSONObject().apply {
            put("reference", Utils.getUniqueReference())
            put("show_consent", "1")
            put("allow_retry", "1")
            put("allow_na_ocr_inputs", "1")
            put("decline_on_single_step", "1")
            put("show_privacy_policy", "1")
            put("show_results", "1")
            put("country", "GB")
            put("verification_mode", "image_only")
            put("email", "")
            put("callback_url", "https://www.yourdomain.com")
        }


        //Creating face object
        val faceObject = JSONObject().put("proof", "")
        if (mainBinding.faceCheck.isChecked) jsonObject.put("face", faceObject)


        //Creating document object
        val documentObject = JSONObject().apply {
            put("proof", "")
            put("supported_types", JSONArray().apply {
                put("id_card")
                put("passport")
                put("driving_license")
                put("credit_or_debit_card")
            })
        }

        if (mainBinding.docCheck.isChecked) jsonObject.put("document", documentObject)


        //Creating document TWO object
        val documentTwoObject = JSONObject().apply {
            put("proof", "")
            put("backside_proof_required", "0")
            put("supported_types", JSONArray().apply {
                put("id_card")
                put("passport")
                put("driving_license")
                put("credit_or_debit_card")
            })
        }

        if (mainBinding.doc2Check.isChecked) jsonObject.put("document_two", documentTwoObject)


        //Creating consent object
        val consentObject = JSONObject().apply {
            put("proof", "")
            put("text", "This is my consent.")
            put("supported_types", JSONArray().apply {
                put("handwritten")
                put("printed")
            })
        }


        if (mainBinding.consentCheck.isChecked) jsonObject.put("consent", consentObject)


        //Creating Address object
        val addressSupportedTypes = JSONArray().apply { put("id_card")
            put("passport")
            put("driving_license")
            put("utility_bill")
            put("bank_statement")
            put("rent_agreement")
            put("employer_letter")
            put("insurance_agreement")
            put("tax_bill")
            put("envelope")
            put("cpr_smart_card_reader_copy")
            put("property_tax")
            put("lease_agreement")
            put("insurance_card")
            put("permanent_residence_permit")
            put("credit_card_statement")
            put("insurance_policy")
            put("e_commerce_receipt")
            put("bank_letter_receipt")
            put("birth_certificate")
            put("salary_slip")
            put("any")
        }

        val addressObject = JSONObject().apply {
            put("supported_types", addressSupportedTypes)
            put("backside_proof_required", "0")
            put("proof", "")
        }

        if (mainBinding.addressCheck.isChecked) jsonObject.put("address", addressObject)

        //Creating Bg checks object
        val backgroundChecksObject = JSONObject()
        if (mainBinding.bgCheck.isChecked) jsonObject.put("background_checks", backgroundChecksObject)

        //Creating Phone/2FA object
        val phoneObject = JSONObject()
        if (mainBinding.twoFACheck.isChecked) jsonObject.put("phone", phoneObject)



        return jsonObject
    }

    private fun resetParameters() {
        mainBinding.faceCheck.isChecked = false
        mainBinding.docCheck.isChecked = false
        mainBinding.doc2Check.isChecked = false
        mainBinding.addressCheck.isChecked = false
        mainBinding.consentCheck.isChecked = false
        mainBinding.bgCheck.isChecked = false
        mainBinding.twoFACheck.isChecked = false
    }
}

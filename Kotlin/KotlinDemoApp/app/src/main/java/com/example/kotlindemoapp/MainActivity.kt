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


class MainActivity : AppCompatActivity() {

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
        authObject.put("auth_type", "basic_auth")
        authObject.put("client_id", clientId)
        authObject.put("secret_key", secretKey)
//        authObject.put("access_token", "")


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
        configObject.put("async", false)
        configObject.put("captureEnabled", false)
        configObject.put("open_webview", false)
        return configObject
    }


    private fun getRequestObject(): JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("reference", Utils.getUniqueReference())
        jsonObject.put("show_consent", "1")
        jsonObject.put("allow_retry", "1")
        jsonObject.put("allow_na_ocr_inputs", "1")
        jsonObject.put("decline_on_single_step", "1")
        jsonObject.put("show_privacy_policy", "1")
        jsonObject.put("show_results", "1")
        jsonObject.put("country", "GB")
        jsonObject.put("verification_mode", "image_only")
        jsonObject.put("email", "")
        jsonObject.put("callback_url", "https://www.yourdomain.com")


        //Creating face object
        val faceObject = JSONObject()
        faceObject.put("proof", "")


        //Creating document object
        val documentObject = JSONObject()
        val docSupportedTypes = ArrayList<String?>()
        docSupportedTypes.add("id_card")
        docSupportedTypes.add("passport")
        docSupportedTypes.add("driving_license")
        docSupportedTypes.add("credit_or_debit_card")

        documentObject.put("proof", "")
        documentObject.put("supported_types", JSONArray(docSupportedTypes))
        if (mainBinding.docCheck.isChecked) jsonObject.put("document", documentObject)


        //Creating document TWO object
        val documentTwoObject = JSONObject()
        documentTwoObject.put("proof", "");
        val docTwoSupportedTypes = ArrayList<String?>()

        docTwoSupportedTypes.add("id_card")
        docTwoSupportedTypes.add("passport")
        docTwoSupportedTypes.add("driving_license")
        docTwoSupportedTypes.add("credit_or_debit_card")
        documentTwoObject.put("supported_types", JSONArray(docTwoSupportedTypes))
        documentTwoObject.put("backside_proof_required", "0")
        if (mainBinding.doc2Check.isChecked) jsonObject.put("document_two", documentTwoObject)


        //Creating consent object
        val consentObject = JSONObject()
        val consentSupportedTypes = ArrayList<String?>()
        consentSupportedTypes.add("handwritten")
        consentSupportedTypes.add("printed")
        consentObject.put("proof", "")
        consentObject.put("text", "This is my consent.")
        consentObject.put("supported_types", JSONArray(consentSupportedTypes))
        if (mainBinding.consentCheck.isChecked) jsonObject.put("consent", consentObject)


        //Creating Address object
        val addressObject = JSONObject()
        val addressSupportedTypes = ArrayList<String?>()
        addressSupportedTypes.add("id_card")
        addressSupportedTypes.add("passport")
        addressSupportedTypes.add("driving_license")
        addressSupportedTypes.add("utility_bill")
        addressSupportedTypes.add("bank_statement")
        addressSupportedTypes.add("rent_agreement")
        addressSupportedTypes.add("employer_letter")
        addressSupportedTypes.add("insurance_agreement")
        addressSupportedTypes.add("tax_bill")
        addressSupportedTypes.add("envelope")
        addressSupportedTypes.add("cpr_smart_card_reader_copy")
        addressSupportedTypes.add("property_tax")
        addressSupportedTypes.add("lease_agreement")
        addressSupportedTypes.add("insurance_card")
        addressSupportedTypes.add("permanent_residence_permit")
        addressSupportedTypes.add("credit_card_statement")
        addressSupportedTypes.add("insurance_policy")
        addressSupportedTypes.add("e_commerce_receipt")
        addressSupportedTypes.add("bank_letter_receipt")
        addressSupportedTypes.add("birth_certificate")
        addressSupportedTypes.add("salary_slip")
        addressSupportedTypes.add("any")
        addressObject.put("supported_types", JSONArray(addressSupportedTypes))
        addressObject.put("backside_proof_required", "0")
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
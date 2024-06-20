package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.sampleapplication.databinding.ActivityMainBinding
import com.sp.shuftipro_sdk.listener.ShuftiVerifyListener
import com.sp.shuftipro_sdk.models.Shuftipro
import com.sp.shuftipro_sdk.utilities.SPUtils.getUniqueReference
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnStartVerification.setOnClickListener {
            val verificationObject = Shuftipro.getInstance()
            verificationObject.shuftiproVerification(
                requestObject = createRequestJsonObject(),
                configObject = createConfigJsonObject(),
                authObject = authenticateUsingClientIdAndSecretKey(),
                activity = this,
                shuftiVerifyListener = object : ShuftiVerifyListener {
                    override fun verificationStatus(responseSet: Map<String, Any>) {
                        Log.d("mapResponse", responseSet.toString())
                        val jsonObject = JSONObject(responseSet)
                        Log.d("jsonObjectResponse", jsonObject.toString())
                    }

                }
            )
        }

    }


    private fun authenticateUsingClientIdAndSecretKey(): JSONObject {
        val clientId = "" // Enter your client ID
        val secretKey = ""// Enter your secret Key
        return JSONObject().apply {
            put("auth_type", "basic_auth")
            put("client_id", clientId)
            put("secret_key", secretKey)
        }
    }

    private fun authenticateWithToken(): JSONObject {
        val authToken = ""
        return JSONObject().apply {
            put("auth_type", "access_token")
            put("access_token", authToken)
        }
    }

    private fun createConfigJsonObject(): JSONObject {
        return JSONObject().apply {
            put("base_url", "api.shuftipro.com")
            put("consent_age", 16)
        }
    }

    private fun createRequestJsonObject(): JSONObject {
        val requestObject = JSONObject()
        try {
            //global level
            requestObject.put("reference", getUniqueReference()) //  you can use your own unique reference
            requestObject.put("country", "")
            requestObject.put("language", "")
            requestObject.put("show_results", "1")
            requestObject.put("decline_on_single_step", "0")
            requestObject.put("allow_retry", "0")


            // face service
            val faceObject = JSONObject()
            faceObject.put("allow_offline", "0")
            faceObject.put("allow_online", "1")
            faceObject.put("verification_mode", "image_only")
            faceObject.put("proof", "")


            // document service
            val documentObject = JSONObject()
            val docSupportedTypes =
                arrayListOf("passport", "id_card", "driving_license", "credit_or_debit_card")
            documentObject.put("proof", "")
            documentObject.put("additional_proof", "")
            documentObject.put("name",
                JSONObject().apply {
                    put("full_name", "")
                    put("first_name", "")
                    put("middle_name", "")
                    put("last_name", "")
                })
            documentObject.put("dob", "")
            documentObject.put("document_number", "")
            documentObject.put("expiry_date", "")
            documentObject.put("issue_date", "")
            documentObject.put("show_ocr_form", "0")
            documentObject.put("backside_proof_required", "0")
            documentObject.put("gender", "")
            documentObject.put("allow_offline", "0")
            documentObject.put("allow_online", "1")
            documentObject.put("fetch_enhanced_data", "1")
            documentObject.put("verification_mode", "image_only")
            documentObject.put("supported_types", JSONArray(docSupportedTypes))

            requestObject.put("face",faceObject)
            requestObject.put("document",documentObject)


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.d("ref", requestObject.toString())
        return requestObject
    }


}
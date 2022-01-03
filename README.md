[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What is Shufti Pro?
Shufti Pro is an AI-based identity verification SaaS (Software as a Service) provider that offers real-time global identity verifications for seamless KYC/AML and KYB compliance. 

It provides businesses with a sound risk-cover and helps prevent fraud with its automated identity verification services including face verification, document verification, consent verification, address verification, biometric consent verification, phone verification, background checks, and 2-factor authentication.

AML screening services enable clients to gain an extra layer of security, by scanning cybercriminals and fraudsters at the source. 

An easy to integrate API allows smooth customer onboarding and establishes longstanding trust while optimising customer processing costs altogether. 

Shufti Pro is the go-to ID authentication solution for digital payment systems, FinTech firms, Cryptocurrency, banks, and trading platforms. Businesses can eradicate financial risk and digital ID theft, and boost operating efficiency all in a matter of seconds.

## Table of contents
* [General Requirements](#general-requirements)
* [Permissions](#permissions)
* [SDK Installation Guide](#sdk-installation-guide)
* [SDK Version](#sdk-version)
* [Verifications](#verifications)
* [Integration](#Integration)
* [Request Object With OCR](#Request-Object-With-OCR)
* [Request Object Without OCR](#Request-Object-Without-OCR)
* [Initiate Request](#Initiate-Request)
* [Request Object Parameters](#Request-Object-Parameters)
* [Config Object Parameters ](#Config-Object-Parameters)
* [Auth Key Object Parameters ](#Auth-Key-Object-Parameters)
* [HTTP Codes](#HTTP-Codes)
* [Response Logging](#response-logging)
* [Status Response](#status-response)
* [Test IDs](#test-ids)
* [Contact](#contact)
* [Copyright](#copyright)

## General Requirements
Minimum requirements for SDK include: 
- Android 4.4 (API level 19) or higher
- Internet connection
- Camera

## Permissions:
The Shufti Pro application requires permission from your device to access the following:
1. Camera
2. External Storage <br/>
All permissions are handled in SDK.

## SDK Installation Guide
In your **Android Project** follow below steps.
1. Download the 'shuftipro-sdk.aar' file from `Shuftipro Sdk` folder 
2. Select File → New → New Module → Import .aar package from the top menu of Android Studio.
3. Select the provided 'shuftipro-sdk.aar' file.
4. Right-click on 'app module' → Select 'Open Module setting'.
5. Select 'Dependencies' from the right pane.
6. Select '+' icon from the top right corner → select 'module dependency' → select 'shuftipro-sdk'.

## SDK Version:
Currently our updated SDK version is 1.0.8


## Verifications:
Shufti Pro offers three Verification services. You have the option to choose either all three or any one of them for mobile verification of your end-users.

Following is a list of all verification services, along with verification request parameters and other technical details.
<br />

* ### With OCR
During verification with OCR, an end-user simply provides identity documents to Shufti Pro for authentication. The automated Shufti Pro API extracts credentials from the provided documents and verifies them as part of a single identity process. 

* ### Without OCR
During verification without OCR, Shufti Pro customer or end-user is required to provide not only the verification data but also the proof of that data in the form of identity documents. 

* ### Verification through hybrid view
If you opt for mobile verification with Shufti Pro’s hybrid view, a web-view built upon HTML 5 will be displayed to the end-user. All data points and fields are adequately defined in the hybrid view. The format for sending verification data will be a JSON object, similar to other mobile verification formats (OCR and Non-OCR). If your send true in [openWebView](#open_webview) parameter then verification through hybrid view will be started else verification with OCR or without OCR (based upon JSON object) will be triggered.


## Integration: 
Take a look at the sample project and learn the most common use of framework/SDK. Make sure to build on a real device.

Make an instance 
```
Shuftipro shuftipro = Shuftipro.getInstance();
```

Make **AuthKeys** object using Clint ID and Secret Key 
```
JSONObject AuthKeys = new JSONObject();

        try {
            AuthKeys.put("auth_type","basic_auth");
            AuthKeys.put("client_Id", "your-clientId");
            AuthKeys.put("secret_key","your-secretKey");
        } catch (JSONException e) {
            e.printStackTrace();
        }
```
<br>

Or **AuthKeys** object using Access Token
```
JSONObject AuthKeys = new JSONObject();

        try {
            AuthKeys.put("auth_type","access_token");
            AuthKeys.put("access_token","sp-accessToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
```

AuthKeys object parameters are explained [here](#auth-key-object-parameters).

<br>

Make **Config** object
```
 JSONObject Config=new JSONObject();

        try {
            Config.put("open_webview",false);
            Config.put("asyncRequest",false);
        Config.put("captureEnabled",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
```
Config object parameters are explained [here](#config-object-parameters).

<br>

## Request Object With OCR

```
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("reference", "12345678");
            requestObject.put("country", "US");
            requestObject.put("language", "EN");
            requestObject.put("email", "");
            requestObject.put("callback_url", "");
            requestObject.put("redirect_url", "");
            requestObject.put("verification_mode", "image_only");
            requestObject.put("show_privacy_policy","1");
            requestObject.put("show_results", "1");
            requestObject.put("show_consent","1");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Face object

        JSONObject faceObject = new JSONObject();

        try {

            faceObject.put("proof", "");
            requestObject.put("face", faceObject);

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
            
        requestObject.put("document", documentObject);
            
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

            requestObject.put("document_two", documentTwoObject);
            
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

            requestObject.put("address", addressObject);

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
            
            requestObject.put("consent", consentObject);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating phone object

        try {
       requestObject.put("phone", "");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating BGC object

        try {

           requestObject.put("background_checks", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
```

## Request Object Without OCR 

```
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("reference", "12345678");
            requestObject.put("country", "US");
            requestObject.put("language", "EN");
            requestObject.put("email", "");
            requestObject.put("callback_url", "");
            requestObject.put("redirect_url", "");
            requestObject.put("verification_mode", "image_only");
            requestObject.put("show_privacy_policy","1");
            requestObject.put("show_results", "1");
            requestObject.put("show_consent","1");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Face object

        JSONObject faceObject = new JSONObject();

        try {

            faceObject.put("proof", "");
            requestObject.put("face", faceObject);

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

          JSONObject docNameObject = new JSONObject();
            try {
              docNameObject.put("first_name", "Johon");
                docNameObject.put("middle_name", "Johsan");
                docNameObject.put("last_name", "Livone");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            documentObject.put("name", docNameObject);
            documentObject.put("dob", "1980-11-12");
            documentObject.put("document_number", "19901112");
            documentObject.put("expiry_date", "1996-11-12");
            documentObject.put("issue_date", "1990-11-12");
            documentObject.put("backside_proof_required", "0");
            documentObject.put("supported_types",new JSONArray(doc_supported_types));
            
        requestObject.put("document", documentObject);
            
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
            
                  JSONObject docTwoNameObject = new JSONObject();
            try {
                 docTwoNameObject.put("first_name", "Johon");
                docTwoNameObject.put("middle_name", "Johsan");
                docTwoNameObject.put("last_name", "Livone");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            documentTwoObject.put("name", docTwoNameObject);

            documentTwoObject.put("dob", "1970-11-12");
            documentTwoObject.put("document_number", "198011-2");
            documentTwoObject.put("expiry_date", "1995-11-12");
            documentTwoObject.put("issue_date", "1990-11-12");
            documentTwoObject.put("backside_proof_required", "0");
            documentTwoObject.put("supported_types",new JSONArray(doc_two_supported_types));
            documentTwoObject.put("gender", "M");

            requestObject.put("document_two", documentTwoObject);
            
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
            addressObject.put("full_address", "ST#2, 937-B, los angles.");

                 JSONObject addressNameObject = new JSONObject();
            try {
                addressNameObject.put("first_name", "Johon");
                addressNameObject.put("middle_name", "Johsan");
                addressNameObject.put("last_name", "Livone");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            addressObject.put("name", addressNameObject);
            addressObject.put("supported_types",new JSONArray(address_supported_types));

            requestObject.put("address", addressObject);

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
            
            requestObject.put("consent", consentObject);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating phone object

        try {
       requestObject.put("phone", "");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating BGC object

        try {

           requestObject.put("background_checks", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
```

Request Object parameters are explained [here](#Request-Object-Parameters).

<br>

## Initiate Request

```
shuftipro.shuftiproVerification(JSONObject: "your-requested-json-object",
                AuthKeys:"authentication-keys-object",
                Config:"configration-objects", 
                parentActivity:"your-caller-activity",
                new ShuftiVerifyListener() {
            @Override
            public void verificationStatus(HashMap<String, String> responseSet) {

                Log.e("Response",responseSet.toString());
                
            }
        });
```

<br>






## Auth Key Object Parameters 

 In this object, we add authorization Key in verification request.

* ## Basic Auth
  Shufti Pro provides Authorization to clients through the **Basic Auth** header. Your **Client ID** will serve as your **Username** while the **Secret Key** will serve as your **Password**. The API will require this header for every request.


* ## Access Token
  Shufti Pro provides Bearer Access Token Authorization method. The client can generate temporary [access token](https://api.shuftipro.com/api/docs/?_ga=2.165834294.908983928.1607423250-1279517864.1604641664#access-token-request) using new access token endpoint. The shared token will be used to authorize API requests. The token shared with the client will be valid for 10 minutes and can be used once only.

<br>


## Config Object Parameters 

 In this object, we add extra configuration of verification that the user wants.

* ## open_webview

    Required: **No**  
    Type: **boolean** <br>
  Accepted Values: **true**, **false**  

    This boolean type of parameter is used to identify if you want to perform verification in its hybrid view.
  If open_webview is true, it means that the user wants verification in **hybrid view**. If false, then the user wants verification with **OCR or Without OCR**. The value is false by default. 


 * ## asyncRequest

    Required: **No**  
    Type: **boolean** <br>
  Accepted Values: **true**, **false**  

    If **asyncRequest** value is set to **TRUE** you will instantly get the end-user's control back so you do not have to wait for the verification results. When a request is completed you will automatically get a callback. 
    
* ## captureEnabled

    Required: **No**  
    Type: **boolean** <br>
  Accepted Values: **true**, **false**    

    This boolean type of parameter is used to identify whether the user wants to open native camera in Iframe or not. A true value means user wants to open native otherwise not. 

  <br>


## Request Object Parameters 

It is important to note here that each verification option offered by Shufti Pro is an exclusive service, and is activated following the nature of user’s request. Clients can choose one or all of the optional API keys. 

In case a key is given in document and address verification, and no value is provided, then OCR will be performed for those particular keys. 


* ## reference

  Required: **Yes**  
  Type: **string**  
  Minimum: **6 characters**  
  Maximum: **250 characters**

  This is the unique reference ID of request, which we will send you back with each response, so you can verify the request. Only alphanumeric values are allowed. This reference can be used to get the status of already performed verification requests.


* ## country

  Required: **No**  
  Type: **string**  
  Length: **2 characters**

  Send the 2 characters long [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements) country code of where your customer is from. Please consult [Supported Countries](https://api.shuftipro.com/api/docs/#supported-countries) for country codes.

* ## language

  Required: **No**  
  Type: **string**  
  Length: **2 characters**

  Send the 2 characters long language code of your preferred language to display the verification screens accordingly. Please consult [Supported Languages](https://api.shuftipro.com/api/docs/#supported-languages) for language codes. Default language english will be selected if this key is missing in the request.

* ## email

  Required: **No**  
  Type: **string**  
  Minimum: **6 characters**  
  Maximum: **128 characters**

  This field represents email of the end-user. This is an optional field.

* ## callback_url

  Required: **No**  
  Type: **string**  
  Minimum: **6 characters**  
  Maximum: **250 characters**

  During a verification request, we make several server to server calls to keep you updated about the verification state. This way you can update the request status at your end even if the customer is lost midway through the process.

* ## verification_mode

  Required: **No**  
  Type: **string**  
  Accepted Values: **image_only, video_only**

  Verification mode defines what types of proofs are allowed for a verification. In case of **video_only** user will upload videos and if verification mode is **image_only**, user will upload images.   

* ## show_privacy_policy
    
    Required: **No**  
    Type: **string**  
    Accepted Values: **0**, **1**
    
    This key specifies that is privacy policy show to user or not.If show_privacy_policy is **0** than privacy policy is not show. If **1** than privacy policy is displayed to the user on the result screen.

* ## show_results
    
    Required: **No**  
    Type: **string**  
    Accepted Values: **0**, **1**
    
  This key specifies whether the verification results will be displayed to the user or not. .If show_results is 0, then verification results are not shown to the user and sent to the merchant. If show_results is 1, then verification results are shown to the user. 


* ## show_consent
    
    Required: **No**  
    Type: **string**  
    Accepted Values: **0**, **1**
    
  This key specifies if the consent is shown to the user or not. If show_consent is 0, then consent screen is not shown to the user. If show_consent is 1, then consent is shown to the user at the start of the verification.

<!-- ---------------------------------------------------------------------------------->
* ## Face

  The easiest of all verifications is done by authenticating the face of the users. For this verification, user has to upload live image of their face for verification.

<!-- ---------------------------------------------------------------------------------->
* ## Document or Document Two

  Shufti Pro provides document verification through various types of documents. The supported formats are passports, ID Cards, driving licenses and debit/credit cards. You can opt for more than 1 document type as well. In that case, Shufti Pro will give an option to end-users to verify their data from any of the given document types.

    * <h3>proof</h3>

  Required: **Yes**  
  Type: **string** <br>
  Image Format: JPG, JPEG, PNG, PDF Maximum: 16MB <br>
  Video Format: MP4/MOV Maximum: 20MB

  Provide valid BASE64 encoded string. Leave empty for an on-site verification.

    * <h3>additional_proof</h3>

  Required: **No**  
  Type: **string** <br>
  Image Format: JPG, JPEG, PNG, PDF Maximum: 16MB <br>
  Video Format: MP4/MOV Maximum: 20MB

  Provide valid BASE64 encoded string. Leave empty for an on-site verification.
  
  
    * <h3>backside_proof_required</h3>

  Required: **No**  
  Type: **string** <br>
  Accepted values: **0,** **1** <br>
  Default value: **0**

  The 0 value means that the user has the option to skip backside proof of the document provided. 1 means that the user must capture the backside image of           document. In case of passport, 0 value means that the user would not be asked to provide backside proof, and in case of 1 value the user would have choice to     capture or skip the backside proof.


  * <h3>supported_types</h3>

  Required: **No**  
  Type: **Array**

  You can provide anyone, two or more types of documents to verify the identity of the user. For example, if you opt for both passport and driving license, then your user will be given an opportunity to verify data from either of these two documents. All supported types are listed below.

  Supported Types      |
  ---------------------|
  passport             |
  id_card            |
  driving_license    |
  credit_or_debit_card |

  **Example 1** ["driving_license"]  
  **Example 2** ["id_card", "credit_or_debit_card", "passport"]

  * <h3>name</h3>

  Required: **No**  
  Type: **object**

  In name object used in document service, first_name and last_name are extracted from the document uploaded if the name is empty. 

  * <h4>first_name</h4>
  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters** 

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John'O Harra**

  * <h4>middle_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark.  
  Example **Carter-Joe**

  * <h4>last_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John, Huricane Jr.**

  * <h4>fuzzy_match</h4>

  Required: **No**  
  Type: **string**  
  Value Accepted: **1**

  Provide 1 for enabling a fuzzy match of the name. Enabling fuzzy matching attempts to find a match which is not 100% accurate.

  * <h3>dob</h3>

  Required: **No**  
  Type: **string**  
  Format: **yyyy-mm-dd**

  Leave empty to perform data extraction from uploaded proofs. Provide a valid date. Please note that the date should be before today. 
  Example 1990-12-31

  * <h3>gender</h3>

  Required: **No**  
  Type: **string**  
  Accepted values: **M,F,O,m,f,o**

  Leave empty to perform data extraction from uploaded proofs. Provide the gender which is given on the document.
  Example M

  * <h3>document_number</h3>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **100 chracters**

  Leave empty to perform data extraction from the proof which will be uploaded by end-users. Allowed Characters are numbers, alphabets, dots, dashes, spaces, underscores, and commas. 
  Examples 35201-0000000-0, ABC1234XYZ098

  * <h3>issue_date</h3>

  Required: **No**  
  Type: **string**  
  Format: **yyyy-mm-dd**

  Leave empty to perform data extraction from the proof which will be uploaded by end-users. Provide a valid date. Please note that the date should be before today. 
  Example 2015-12-31

  * <h3>expiry_date</h3>

  Required: **No**  
  Type: **string**  
  Format: **yyyy-mm-dd**

  Leave empty to perform data extraction from the proof which will be uploaded by end-users. Provide a valid date. Please note that the date should be after today. 
  Example 2025-12-31
  
  * <h3>fetch_enhanced_data</h3>

  Required: **No**  
  Type: **string**  
  Accepted value: **1**

  Provide 1 for enabling enhanced data extraction for the document. Shufti Pro provides its customers with the facility of extracting enhanced data features using OCR technology. Now, instead of extracting just personal information input fields, Shufti Pro can fetch all the additional information comprising more than 100 data points from the official ID documents supporting 150 languages. For example height, place_of_birth, nationality, marital_status, weight, etc.(additional charges apply)
Extracted data will be returned in object under the key additional_data in case of verification.accepted or verification.declined.
For Details on additional_data object go to [Additional Data](https://api.shuftipro.com/api/docs/#additional-data)

<!-- -------------------------------------------------------------------------------- -->
* ## Address

  Address of an individual can be verified from the document but they have to enter it before it can be verified from an applicable document image.


    * <h3>proof</h3>

  Required: **Yes**  
  Type: **string** <br>
  Image Format: JPG, JPEG, PNG, PDF Maximum: 16MB <br>
  Video Format: MP4/MOV Maximum: 20MB

  Provide valid BASE64 encoded string. Leave empty for an on-site verification.



  * <h3>supported_types</h3>

  Required: **No**  
  Type: **Array**

  Provide any one, two, or more document types in supported_types parameter in Address verification service. For example, if you choose id_card and utility_bill, then the user will be able to verify data using either of these two documents. Following is the list of supported types for address verification.

  Supported Types      |
  ---------------------|
  id_card              |
  passport             |
  driving_license      |
  utility_bill         |
  bank_statement       |
  rent_agreement       |
  employer_letter      |
  insurance_agreement  |
  tax_bill             |

  **Example 1** [ "utility_bill" ]  
  **Example 2** [ "id_card", "bank_statement" ]

  * <h3>full_address</h3>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **250 chracters**

  Leave empty to perform data extraction from the uploaded proof. Allowed Characters are numbers, alphabets, dots, dashes, spaces, underscores, hashes and commas.

  * <h3>name</h3>

  Required: **No**  
  Format **object**

  In name object used in address service, first_name is required if you don't want to perform OCR of the name parameter. Other fields are optional.

  * <h4>first_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John'O Harra**

  * <h4>middle_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark.  
  Example **Carter-Joe**

  * <h4>last_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John, Huricane Jr.**

  * <h4>fuzzy_match</h4>

  Required: **No**  
  Type: **string**  
  Value Accepted: **1**

  Provide 1 for enabling a fuzzy match of the name. Enabling fuzzy matching attempts to find a match which is not a 100% accurate.

<!-- -------------------------------------------------------------------------------- -->
* ## consent
  
  Customised documents/notes can also be verified by Shufti Pro. Company documents, employee cards or any other personalised note can be authenticated by this module. You can choose handwritten or printed document format but only one form of the document can be verified in this verification module. Text whose presence on the note/customized document is to be verified is also needed to be provided.

  * <h3>proof</h3>

  Required: **Yes**  
  Type: **string**  
  Image Format: **JPG, JPEG, PNG, PDF** Maximum: **16MB**  
  Video Format: **MP4/MOV** Maximum: **20MB**
  
  * <h3>supported_types</h3>

  Required: **No**  
  Type: **array**

  Text provided in the consent verification can be verified by handwritten documents or printed documents.

  Supported Types              |
  ---------------------|
  handwritten          |
  printed            |

  **Example 1**  ["printed"]  
  **Example 2**  ["printed", "handwritten"]

  * <h3>text</h3>

  Required: **Yes**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **100 chracters**

  Provide text in the string format which will be verified from the document which the end-user will provide us.

    * <h3>with_face</h3>

  Required: **No**  
  Type: **string**  
  Accepted Values: **0,1**
  Default Value: **1**

  This parameter is applicable if supported_type is handwritten and default value is 1. If value of with_face is 1 then hand written note will be accepted only with face which means your customer must need to show his/her face along with the consent on a paper. If value of with_face is 0 then hand written note is accepted with or without face.

  <!-- -------------------------------------------------------------------------------- -->
* ## phone

  Verify the phone number of end-users by sending a random code to their number from Shufti Pro. Once the sent code is entered into the provided field by end-user, a phone number will stand verified. It is primarily an on-site verification and you have to provide phone number of the end-user to us, in addition to the verification code and the message that is to be forwarded to the end-user. Shufti Pro will be responsible only to send the message along with verification code to the end-user and verify the code entered by the end-user.

  * <h3>phone_number</h3>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **64 chracters**

  Allowed Characters: numbers and plus sign at the beginning. Provide a valid customer’s phone number with country code. Shufti Pro will directly ask the end-user for phone number if this field is missing or empty.

  * <h3>random_code</h3>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **10 chracters**

  Provide a random code. If this field is missing or empty. Shufti Pro will generate a random code.

  * <h3>text</h3>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **100 chracters**

  Provide a short description and random code in this field. This message will be sent to customers. ***This field should contain random_code***. If random_code field is empty than Shufti Pro will generate a random code and append the code with this message at the end.

<!-- -------------------------------------------------------------------------------- -->
* ## background_checks

  It is a verification process that will require you to send us the full Name of end-user in addition to the date of birth. Shufti Pro will perform AML based background checks based on this information. Please note that the name and dob keys will be extracted from document service if these keys are empty.

  * <h3>name</h3>

  Required: **No**  
  Format: **object**

  In name object used in background checks service, first_name is required and other fields are optional.

  * <h4>first_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John'O Harra**

  * <h4>middle_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark.  
  Example **Carter-Joe**

  * <h4>last_name</h4>

  Required: **No**  
  Type: **string**  
  Minimum: **2 characters**  
  Maximum: **32 chracters**

  Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
  Example **John, Huricane Jr.**

  * <h3>dob</h3>

  Required: **No**  
  Type: **string**  
  Format: **yyyy-mm-dd**

  Provide a valid date. Please note that the date should be before today. 
  Example 1990-12-31



<br>



## HTTP Codes

Following is a list of HTTP codes which are generated in responses by Shufti Pro Verification API.

HTTP code     | HTTP message         | Message        |                                   
--------------|----------------------| -------------- |
200           | OK                 | success                                    
400           | Bad Request          | bad request: one or more parameter is invalid or missing
401           | Unauthorized         | unauthorized: invalid signature key provided in the request
402           | Request Failed       | invalid request data: missing required parameters
403           | Forbidden            | forbidden: service not allowed
404           | Not Found            | resource not found
409           | Conflict             | conflicting data: already exists
500           | Server Error         | internal server error
<br>


## Response Logging

Response of verification can be logged via the code given below. You can see this in LogCat at runtime. Write this code in response listener of SDK:


```sh
//To view the errors of request
String error = responseSet.get("error");

Log.e("LoggingResp", error);

//To get the status of request
String event = responseSet.get("event");

if (event.equalsIgnoreCase("verification.accepted")) {

  //Verification accepted do whatever you want to do
  Log.i("LoggingResp", event);
} else {

  //Verification declined do whatever you want to do
  Log.i("LoggingResp", event);
}
```

## Status Response
The Shufti Pro Verification API will send a JSON response if a status request is made.


* <h3>reference</h3>

    This is the user’s unique request reference provided at the time of request, in order for the unique response to be identified. 


* <h3>event</h3>

    The request event shows the status of user’s request, and is different for every response. For more information, click
    [here](https://api.shuftipro.com/api/docs/#status-response)

<aside class="notice">
Note: <b>request.invalid</b> response with <b>HTTP status code 400</b> means the request is invalid.
</aside>

>Sample Response  

```json
{
  "reference": "17374217",
  "event": "request.declined",
  "error": "",
  "verification_url": ""
}

```

## Supported Document Types

Address | Document | 
--------------- | ------------ | 
id_card | id_card 
passport  | passport  
driving_license | driving_lisence
utility_bill | credit_or_debit_card 
bank_statement |
rent_agreement |
employer_letter |
insurance_agreement | 
tax_bill |

<br>

# Test IDs
Shufti Pro provides users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information.  <br><br>
Note: These test IDs only be usefull in Iframe SDK.


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any queries regarding the implementation of SDK, please feel free to contact Shufti Pro [tech support](mailto:tech@shuftipro.com).

## Copyright
2017-21 © Shufti Pro Ltd.

## Revision History

Date            | Description 
--------------- | ------------
18 Jan 2021     | Added all verfications(verification with OCR, without OCR and restful API) in one sdk.
09 Feb 2021     | Updated async parameter
11 Feb 2021     | Updated responses
19 Apr 2021     | Improve Functionality
18 May 2021     | Improve user experience
27 May 2021     | Update content and improve user experience
12 Jul 2021     | Improved user experience
01 Oct 2021     | Improved Functionality
09 Nov 2021     | Bug fixes and improve user experience
30 Nov 2021     | Android 12 support added
10 Dec 2021     | Improved functionality

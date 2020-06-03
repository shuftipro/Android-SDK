[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What is Shufti Pro?
Shufti Pro is an AI based Identity Verification SaaS (Software as a Service) that offers real-time, global verifications for seamless KYC/AML/KYB compliance. 

It provides businesses with a sound risk-cover and helps prevent fraud with its automated identity verification services including: Face Verification, Document and Address Verification, Biometric Consent and 2 Factor Authentication.

AML screening services enable clients to gain an extra layer of security, by scanning cybercriminals and fraudsters at source. 

An easy to integrate API allows smooth customer onboarding and establishes longstanding trust, while optimising customer processing costs altogether. 

Shufti Pro is the go-to ID authentication solution for digital payment systems, FinTech firms, Cryptocurrency, banks, and trading platforms. Businesses can eradicate financial risk and digital ID theft, and boost operating efficiency all in a matter of seconds.


## Table of contents
* [General Requirements](#general-requirements)
* [Permissions](#permissions)
* [SDK Installation Guide](#sdk-installation-guide)
* [Verifications](#verifications)
* [Get Started with SDK](#get-started-with-sdk)
* [Response Logging](#response-logging)
* [Status Response](#status-response)
* [Sample Project Setup](#sample-project-setup)
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
2. Recording Audio
3. External Storage<br />
`Note: All permissions are handled in SDK.`

### SDK Installation Guide
1. Select File → New → New Module → Import .aar package from top menu of Android Studio.
2. Select the provided 'shuftipro-sdk.aar' file.
3. Right click on 'app module' → Select 'Module setting'.
4. Select 'Dependencies' from the right pane.
5. Select '+' icon from the top right corner → select 'module dependency' → select 'shuftipro-sdk'.



## Verifications:
Shufti Pro offers three Verification services with OCR on its Mobile SDK. You have the option to choose either all three, or any one of them for mobile verification of your end-users.

Following is a list of all verification services, along with verification request parameters and other technical details.
<br />

## 1- Face Verification

To conduct a successful face verification, the native camera of the device is used. The liveness detection technique used for facial recognition ensures that authentic facial proof, in the form of biological identifiers, belongs to the actual user. 
To perform face verification, create an instance for the
```FaceVerification```. Set face verification to **TRUE**

```
FaceVerification faceVerification = FaceVerification.getInstance();
faceVerification.setFaceVerificationService(true);
```

## 2- Document Verification

In order to perform Document Verification, the user is required to display identity documents in front of the device’s camera. Shufti Pro’s API currently supports authentication of official ID cards, passports and driving licenses, as well as bank credit and debit cards. 

To perform document verification, create an instance of
 ```DocumentVerification``` and follow these steps

```
DocumentVerification documentVerification = DocumentVerification.getInstance();
```

 ### Fetch Enhanced Data

Use ```setFetchEnhancedData``` for enabling enhanced data extraction for the document. Shufti Pro provides its customers with the facility of extracting enhanced data features using OCR technology. Now, instead of extracting just personal information input fields, Shufti Pro can fetch all the additional information comprising more than 100 data points from the official ID documents supporting 150 languages. For example height, place_of_birth, nationality, marital_status, weight, etc.(additional charges apply) Extrated data will be returned in object under the key additional_data in case of verification.accepted or verification.declined. For Details on additional_data object go to [Additional Data](https://api.shuftipro.com/api/docs/#additional-data)
``` 
documentVerification.setFetchEnhancedData(true);
```


 ### Document Type

Use ```setSupportedTypes``` to pass list of [supported document types](#supported-document-types). This is a mandatory step in performing Document Verification.

``` 
documentVerification.setSupportedTypes("DOCUMENTTYPES");
```
### Name Extraction

For Name Extraction, set ```extractName``` to **TRUE**

``` 
documentVerification.extractName(true);
```
### Dob Extraction

For Dob Extraction, set ```extractDob``` to **TRUE**

``` 
documentVerification.extractDob(true);
```

### Document Number Extraction

For Document Number Extraction, set ```extractDocumentNumber``` to **TRUE**

``` 
documentVerification.extractDocumentNumber(true);
```

### Expiry Date Extraction

For Expiry Date Extraction, set ```extractExpiryDate``` to **TRUE**
``` 
documentVerification.extractExpiryDate(true);
```

### Issue Date Extraction

For Issue Date Extraction, set ```extractIssueDate``` to **TRUE**
``` 
documentVerification.extractIssueDate(true);
```

## 3- Address Verification

Shufti Pro’s API carries out Address Verification by cross-checking addresses printed on official ID documents, driving licenses and passports. The address of an end-user can also be verified using user bank statements and utility bills (no older than 3 months). 

To perform Address Verification, create an instance of
 ```AddressVerification``` and follow these steps

``` 
AddressVerification addressVerification = AddressVerification.getInstance();
```

### Document Type

Use ```setSupportedTypes``` to pass list of  [supported document types](#supported-document-types). This is a mandatory step in performing Address Verification.


``` 
addressVerification.setSupportedTypes("DOCUMENTTYPES");
```

### Address Extraction

For Address Extraction, set  ```extractFullAddress``` to **TRUE**

```
addressVerification.extractFullAddress(true);
```
### Name Extraction

For Name Extraction, set  ```extractName``` to **TRUE**

```
addressVerification.extractName(true);
```

## Request Parameters 

It is important to note here that each verification option offered by Shufti Pro is an exclusive service, and is activated following the nature of user’s request. Clients can choose one or all of the optional API keys. 

In case a key is given in document and address verification, and no value is provided, then OCR will be performed for those particular keys. 

 

* ## reference

	Required: **Yes**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**  

	This is the unique reference ID of request, which Shufti Pro will send back with each response, in order for the request to be verified by the client. 
	Here, only alphanumeric values are allowed. This reference can also be used to identify the status of verification requests previously performed.



* ## country

	Required: **No**  
	Type: **string**  
	Length: **2 characters**

	Submit the 2 characters long [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements) country code of the country your customer belongs to. 
	Please consult [Supported Countries](countries.md) for country codes. `Note: You may pass empty string if you don't want to enforce country verification.`

* ## language

	Required: **No**  
	Type: **string**  
	Length: **2 characters**

	Send the 2 characters long language code of your preferred language for the verification screens to display accordingly. Please consult [Supported Languages](languages.md) for language codes. English will be selected as the ‘default language’ in case the key is missing in the request.

* ## email

	Required: **No**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **128 characters**

	This field represents the email id of the end-user. In case it is missing in a request, the Shufti Pro API will ask the user for its email id.

* ## callbackUrl

	Required: **Yes**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	During a verification request, Shufti Pro makes several server-to-server calls to keep clients updated about the status of verification. This way users can update the request status at their end even if the customer is lost midway through the process.


* ## redirectUrl

	Required: **No**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	Once the verification process is complete, the user is moved to a ‘Redirect URL’ after the results have been displayed.
	

* ## verification_mode
	
	Required: **Yes**  
    Type: **string**  
	
	This key specifies the types of proof that will be provided for verification. If you set the verification_mode to “video”, a 10-second video of the user will be captured. If you set the verification_mode to “image”, proof will be captured in the form of users’ images.


* ## Asyncronous Feedback
	
	If ```async``` value is set to **TRUE** while creating Shuftipro instance you will instantly get the user's control back so you do not have to wait for the verification results. When a request is completed you will automatically get a callback.



## Get Started with SDK

See the sample project provided to learn the most common use. Make sure to build on real device.

```
import com.shutipro.sdk.Shuftipro;
import com.shutipro.sdk.listeners.ShuftiVerifyListener;
import com.shutipro.sdk.models.ShuftiproVerification;

```
 Make an instance:<br>
 Instance can be created in two ways. First is by using `clientId` and `secretKey`, other one is by providing `accessToken`.<br>
 You can read more about `accessToken`[here](https://api.shuftipro.com/api/docs/#access-token)
```
// using clientId and secretKey
Shuftipro instance = Shuftipro.getInstance(clientId: "your-clientId",secretKey: "your-secretKey", async: false);
// or using accessToken
Shuftipro instance = Shuftipro.getInstance(accesstoken: "sp-accessToken", async: false);
```
After making an instance, you are required to perform a method call to first provide all the necessary parameters. Then, you can select the desired verification services you want to perform e.g. Face Recognition, Address Verification, Document Verification, and Optional Parameters. Please do not mention any parameters or services that are not required by the user.  
Note: All necessary parameters and at least one service parameter will need to be provided in order to run a verification request.


```
ShuftiproVerification.RequestBuilder requestBuilder = new ShuftiproVerification.RequestBuilder(reference, country,   callback_url, this, verification_mode, new ShuftiVerifyListener() {
            @Override
            public void verificationStatus(HashMap<String, String> responseSet) {
                Log.e("Response", responseSet.toString());
            }
        });
	requestBuilder.withFaceVerification(faceVerification);
	requestBuilder.withAddressVerification(null);
	requestBuilder.withDocumentVerification(null);
	requestBuilder.withLanguage("");
	requestBuilder.withRedirectUrl("");
	instance.shuftiproVerification(requestBuilder.buildShuftiModel());
```
## Response Logging

Response of verification can be logged via the code given below. You can see this in LogCat at runtime. Write this code in Response listener of SDK:


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
	[here](status_codes.md)

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
driving_license | driving_licence
utility_bill | credit_or_debit_card 
bank_statement |
rent_agreement |
employer_letter |
insurance_agreement | 
tax_bill |


## Sample project setup
In HomeActivity.java file add your **Client ID** on line 36 and **Secret Key** on line 37. Thats it!
> **Note:** Run project on a real device.

# Test IDs
Shufti Pro provides users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information.  <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any queries regarding the implementation of SDK, please feel free to contact Shufti Pro [tech support](mailto:support@shuftipro.com).

## Copyright
2016-2020 © Shufti Pro Ltd.

Date            | Description 
--------------- | ------------
3rd June 2020   | Result dialog cancelled on backPressed.

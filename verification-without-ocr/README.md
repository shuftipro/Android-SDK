[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What Is This?
Shufti Pro is an Identity Verification software as a service that enables enterprises and individuals to secure their KYC and AML compliance. With quick, and realtime results, it acts as a security measure for sound risk assessment by using Face Verification, Document Verification, Address Verification, Biometric Consent Verification and Handwritten Note Verification. It is the go-to option for Payment Systems, FinTECH industry, Crypto, Banks, Lending Economy, Trading platforms and  any other business that wants to eradicate financial risk and identity theft risk from their business practices.

## Table of contents
* [General Requirements](#general-requirements)
* [SDK Installation Guide](#sdk-installation-guide)
* [Permissions](#permissions)
* [Verifications](#verifications)
* [Get Started with SDK](#get-started-with-sdk)
* [Response Logging](#response-logging)
* [Status Response](#status-response)
* [Sample Project Setup](#sample-project-setup)
* [Test IDs](#test-ids)
* [Contact](#contact)
* [Copyright](#copyright)

## General Requirements
Followings are minimum requirements for SDK:
- Android 4.4 (API level 19) or higher
- Internet connection
- Camera

## SDK Installation Guide
1. Select File → New → New Module → Import .jar/Aar package from top menu of Android Studio.
2. Select the provided 'shuftipro-sdk.aar' file.
3. Right click on 'app module' → Select 'Module setting'.
4. Select 'Dependencies' from the right pane.
5. Select '+' icon from the top right corner → select 'module dependency' → select 'shuftipro-sdk'.

## Permissions:
Application uses the following permissions which you may have to add in privacy policy of the app. All permissions are handled in SDK.
1. Camera
2. Internet
3. Recording Audio
4. External Storage

## Verifications:
Shufti Pro offers 4 Verification services without OCR on its Mobile SDK. You can either choose all 4 of them or can opt to use only one of them for mobile verification of your customers/users.
All the technical details and verification request parameters are explained below for each verification service.<br />

## 1- Face Verification

In order to perform a face verification, the native camera of the smartphone is used. Liveness detection and many other techniques are used to ensure that only an authentic facial proof is used for Face Verification.

To perform face verification, create an instance for the ```FaceVerification```. Set face verification to **TRUE**

```
FaceVerification faceVerification = FaceVerification.getInstance();
faceVerification.setFaceVerification(true);
```

## 2- Document Verification

In order to perform Document Verification, User has to display their identity document in front of their phone camera. Shufti Pro currently supports ID cards, passports & driving license. Shufti Pro can even verify credit cards and debit cards as well.

To perform document verification, create an instance of ```DocumentVerification``` and follow these steps

```
DocumentVerification documentVerification = DocumentVerification.getInstance();
```
 ### Document Type

Use ```setSupportedTypes``` to pass list of [supported document types](#supported-document-types). This is mandatory to perform Document Verification.

``` 
documentVerification.setSupportedTypes("DOCUMENTTYPES");
```
### Name Verification

For Name Verification, use ```setFirstName```, ```setMiddleName```& ```setLastName```. First Name is mandatory, while the middle and last name is optional. Maximum 32 characters allowed including alphabets, - (dash), comma, space, dot and single quotation mark.

``` 
documentVerification.setFirstName("John");
documentVerification.setMiddleName("");
documentVerification.setLastName("Doe");
```
### Dob Verification

For DoB Verification, Use ```setDob```. The allowed format is yyyy-mm-dd.

``` 
documentVerification.setDob("1998-11-01");
```

### Document Number Verification

For Document Number Verification, use ```setDocumentNumber```. Allowed Characters limit is between 2 and 100.

``` 
documentVerification.setDocumentNumber("12668899755");
```

### Expiry Date Verification

For Expiry Date Verification, Use ```setExpiryDate```. The  allowed format is yyyy-mm-dd.

```
documentVerification.setExpiryDate("2019-11-01");
```

### Issue Date Verification

For Issue Date Verification, Use ```setIssueDate```. The allowed format is yyyy-mm-dd.

```
documentVerification.setIssueDate("1999-10-01");
```

## 3- Address Verification

Address Verification is performed by Shufti Pro with the help of address printed on the official identity documents. In addition to ID cards, driving licenses and passports, Shufti Pro can verify the address of an end-user with the help of bank statements and utility bills as well, that are no older than 3 months.

To perform Address Verification, create an instance of ```AddressVerification``` and follow these steps

``` 
AddressVerification addressVerification = AddressVerification.getInstance();
```

### Document Type

Use ```setSupportedTypes``` to pass list of  [supported document types](#supported-document-type). This is mandatory to perform Address Verification.

``` 
addressVerification.setSupportedTypes("DOCUMENTTYPES");
```

### Address Verification

For Address Verification, use ```setFullAddress```. Character limit ranges between 2 and 250 characters.

```
addressVerification.setFullAddress("3339 Maryland Avenue, Largo, Florida");
```
### Name Verification

For Name Verification, use ```setFirstName```, ```setMiddleName```& ```setLastName```. First Name is mandatory, while the middle and last name is optional. Maximum 32 characters allowed including alphabets, - (dash), comma, space, dot and single quotation mark.
```
addressVerification.setFirstName("John");
addressVerification.setMiddleName("");
addressVerification.setLastName("Doe");
```
### Fuzzy Match

To use Fuzzy Match, set **True** for ```setFuzzyMatch```. Enabling fuzzy matching attempts to find a match which is not 100% accurate. (edited)

```
addressVerification.setFuzzyMatch(true);
```

## 4- Consent  Verification
Shufti Pro performs Consent Verification with the help of handwritten and printed document. Any form of text written on a document or a note can be easily verified through this service.

### Document Type

Use ```setSupportedTypes``` to pass list of supported document types. This is mandatory to perform Consent Verification.

``` 
consentVerification.setSupportedTypes("DOCUMENTTYPES");
```

### Text Verification
For Text verification, Use ```setConsentText```. Allowed Characters limit is between 2 and 100.
```
consentVerification.setConsentText("This is a customized text");
```

## Request Parameters 

It is important to note here that each service module is independent of other and each one of them is activated according to the nature of request received from you. There are a total of four services which include face, document, address, consent.

All verification services are optional. You can provide Shufti Pro a single service or mixture of several services for verifications. All keys are optional too. If a key is given in document or address sevice and no value is provided then OCR will be performed for those keys. 

* ## reference

	Required: **Yes**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**  

	This is the unique reference ID of request, which we will send you back with each response, so you can verify the request. Only alphanumeric values are allowed. This reference can be used to get status of already performed verification requests.


* ## country

	Required: **Yes**  
	Type: **string**  
	Length: **2 characters**

	Send the 2 characters long [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements) country code of where your customer is from. Please consult [Supported Countries](countries.md) for country codes.

* ## language

	Required: **No**  
	Type: **string**  
	Length: **2 characters**

	Send the 2 characters long language code of your preferred language to display the verification screens accordingly. Please consult [Supported Languages](languages.md) for language codes. Default language english will be selected if this key is missing in the request.

* ## email

	Required: **No**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **128 characters**

	This field represents email of the end-user. If it is missing in a request, than Shuftpro will ask the user for its email in an on-site request.

* ## callbackUrl

	Required: **Yes**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	During a verification request, we make several server to server calls to keep you updated about the verification state. This way you can update the request status at your end even if the customer is lost midway through the process.

* ## redirectUrl

	Required: **No**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	Once verification is complete, User is redirected to this link after showing the results.
	


* ## Asyncronous Feedback
	
	If ```async``` value is set to **TRUE** while creating Shuftipro instance you will instantly get the user's control back so you do not have to wait for the verification results. When a request is completed you will automatically get a callback.

## Get Started with SDK

See the sample project provided to learn the most common use. Make sure to build on real device.
```
import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
```
Make an instance 
```
Shuftipro instance = Shuftipro.getInstance(clientId: "your-clientId",secretKey: "your-secretKey", async: false);
```
After making an instnace,  you are required to perform a method call for all the verification services that you want to perform and for those authentication services that are not desired to be performed, pass a ```null``` value.

```
shuftipro.shuftiproVerification(reference, country, lng, email, callbackUrl, redirectUrl,
                faceVerification, documentVerification, addressVerification, consentVerification, 				  this, new ShuftiVerifyListener() {
                    @Override
                    public void verificationStatus(HashMap<String, String> responseSet) {

						//I'm printing my response you can do whatever you want to do
						Log.e("Response", responseSet.toString());
                    }
                });
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
**Note:** Run project on a real device.

## Status Response
The Shufti Pro Verification API will send a JSON response if a status request is made.

* <h3>reference</h3>

	Your unique request reference, which you provided us at the time of request, so that you can identify the response in relation to the request made.

* <h3>event</h3>

	This is the request event which shows status of request. Event is changed in every response. Please consult 
	[here](status_codes.md) for more information

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

### Supported Document Types

Address | Document | Consent |
--------------- | ------------ | ------------ |
id_card | id_card | handwritten 
passport  | passport  | printed  
driving_license | driving_lisence
utility_bill | credit_or_debit_card 
bank_statement |
rent_agreement |
employer_letter |
insurance_agreement | 
tax_bill |

## Sample project setup
In HomeActivity.java file add your **Client ID** on line 38 and **Secret Key** on line 39. Thats it!
> **Note:** Run project on a real device.

## Test IDs
Shufti Pro provides the users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information. <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any questions/queries regarding implementation of SDK please feel free to contact our [tech support](mailto:support@shuftipro.com).

## Copyright
2016-18 © Shufti Pro Ltd.

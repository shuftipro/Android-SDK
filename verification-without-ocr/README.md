[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What Is This?
Shufti Pro is an Identity Verification software as a service that enables enterprises and individuals to secure their KYC and AML compliance. With quick, and realtime results, it acts as a security measure for sound risk assessment by using Face Verification, Document Verification, Information Authentication, and PEP's, Criminals and Money Laundering Checks. It is the go-to option for Payment Systems, FinTECH industry, Crypto, Banks, Lending Economy & Trading platforms.

# Table of contents
* [Basic Setup](#basic-setup)
* [General Requirements](#general-requirements)
* [SDK Installation Guide](#sdk-installation-guide)
* [Permissions](#permissions)
* [Integration](#integration)
* [Sample Request](#sample-request)
* [Response Logging](#response-logging)
* [Status Response](#status-response)
* [Sample Project Setup](#sample-project-setup)
* [Test IDs](#test-ids)
* [Contact](#contact)
* [Copyright](#copyright)

# Basic Setup
## General Requirements
Followings are minimum requirements for SDK:
- API Level 15  and higher
- Internet connection
- Camera

## SDK Installation Guide
1. Select File → New → New Module → Import .jar/Aar package from top menu of Android Studio.
2. Select the provided 'shufti_pro.aar' file.
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
Shufti Pro supports four modes of verification:<br />
**1. Face Verification**<br />
**2. Document Verification**<br/>
**3. Address Verification**</br>
**4. Consent Verification**

## Integration: 
See the sample project provided to learn the most common use. Make sure to build on real device.
```
import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
```
Make an instance 
```
Shuftipro instance = Shuftipro(clientId: "your-clientId",
                               secretKey: "your-secretKey");
```
## Sample Request
For **Sample** verification request
```sh
instance.shuftiproVerification(reference: "17374217",country: "PK", 
				      language: "EN", email: "example@gmail.com",
				      callback_url: "your-callback_url",
                                      redirect_url: "your-redirect_url",
				      isToMakeFaceVerification: true,
                                      isToPerformDocumentationVerification: true, 
				      isSupportPassportType: true,
				      isSupportIdCardType: true,    				    	     					   	      isSupportDrivingLicenseType: true,
				      isSupportCreditCardType: true,
				      isToVerifyNameOnDoc: true,
				      doc_first_name: "John",
				      doc_last_name: "Doe",
				      doc_middle_name: "",
				      dob: "12-09-1992",
				      documentNumber: "ABC1234XYZ098",
				      expiryDate: "2025-12-20",
				      issueDate: "2015-12-20",
                                      isToPerformAddressVerification: true,
				      isUtilityBillSupportedType: true, 					      					      isIdCardSupportedType: true,
				      isBankStatementSupportedType: true,
				      fullAddress: "This is my address", 
				      isToVerifyNameOnAddress: true,
				      doc_first_name: "John",
				      doc_last_name: "Dev",
				      doc_middle_name: "",
				      isToPerformConsentVerification: true,
				      textToBeVerify: "My custom text",
				      parentActivity: "your-caller-activity",
				      ShuftiVerifyListener: new ShuftiVerifyListener(){
				 
					@Override
					public void verificationStatus(HashMap<String, String> responseSet) {
					
						String event = responseSet.get("event");
				   		if(event.equalsIgnoreCase("verification.accepted")){
						//Do anything you want.. I am showing a toast message
				       		Toast.makeText(this, "Status : Verified...", Toast.LENGTH_LONG).show();
						}
						else{
						
						//Do anything you want.. I am showing a toast message
				      		 String message = responseSet.get("message");
				      	 	Toast.makeText(this, "Status : Not Verified", Toast.LENGTH_LONG).show();
				   }});
```


# Request Parameters 

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

* ## callback_url

	Required: **Yes**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	During a verification request, we make several server to server calls to keep you updated about the verification state. This way you can update the request status at your end even if the customer is lost midway through the process.

* ## redirect_url

	Required: **No**  
	Type: **string**  
	Minimum: **6 characters**  
	Maximum: **250 characters**

	Once an on-site verification is complete, User is redirected to this link after showing the results.
	
* ## isFaceVerification

	Required: **No**  
	Type: **boolean** 

	Set value to true for face verification.
	
* ## isToPerformDocumentationVerification

	Required: **No**  
	Type: **boolean** 

	Set value to true for documentation verification.	

* ## isSupportPassportType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using passport.	

* ## isSupportIdCardType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using Id card.

* ## isSupportDrivingLicenseType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using driving lisence.

* ## isSupportCreditDebitCardType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using credit/debit card.

* ## isToVerifyNameOnDoc

	Required: **No**  
	Type: **boolean** 

	If you set it true first and last name will be verified with the provided proof.
	
* ## doc_first_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters** 

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
	Example **John'O Harra**

* ## doc_middle_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters**

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark.  
	Example **Carter-Joe**

* ## last_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters**

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
	Example **John, Huricane Jr.**

* ## dob

	Required: **No**  
	Type: **String**  
	Format: **yyyy-mm-dd**  

	Provide a valid date. Please note that the date should be before today. Example 1990-12-31

* ## documentNumber

	Required: **No**  
	Type: **String**  
	Minimum: **2 characters**  
	Maximum: **100 characters** 
	
	Allowed Characters are numbers, alphabets, dots, dashes, spaces, underscores and commas. Examples 35201-0000000-0, 	ABC1234XYZ098

* ## expiryDate

	Required: **No**  
	Type: **String**  
	Format: **yyyy-mm-dd**  

	Provide a valid date. Please note that the date should be after today. Example 2025-12-31

* ## issueDate

	Required: **No**  
	Type: **String**  
	Format: **yyyy-mm-dd**  

	Provide a valid date. Please note that the date should be after today. Example 2025-12-31
	
* ## isToPerformAddressVerification

	Required: **No**  
	Type: **boolean** 

	Set value to true for address verification verification.

* ## isUtilityBillSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using utility bills.

* ## isIdCardSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using Id card.

* ## isBankStatementSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it true user will be able to verify data using bank statements.
	
* ## fullAddress

	Required: **No**  
	Type: **String**  
	Minimum: **2 characters**  
	Maximum: **250 characters** 

	Allowed Characters are numbers, alphabets, dots, dashes, spaces, underscores, hashes and commas. Leave empty to perform data extraction from provided proofs.

* ## isToVerifyNameOnAddress

	Required: **No**  
	Type: **boolean** 

	If you set it true first and last name will be verified with the provided proof.
	
* ## add_first_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters** 

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
	Example **John'O Harra**

* ## add_middle_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters**

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark.  
	Example **Carter-Joe**

* ## add_last_name

	Required: **No**  
	Type: **string**  
	Minimum: **2 characters**  
	Maximum: **32 chracters**

	Allowed Characters are alphabets, - (dash), comma, space, dot and single quotation mark. 
	Example **John, Huricane Jr.**

* ## fuzzy_match

	Required: **No**  
	Type: **boolean**  

	Provide true for enabling a fuzzy match of the name. Enabling fuzzy matching attempts to find a match which is not a 100% accurate.
	
* ## isToPerformConsentVerification

	Required: **No**  
	Type: **boolean**
	
	Set value to true for consent verification.
	
* ## textToBeVerified

	Required: **No**  
	Type: **String**  
	Minimum: **2 characters**  
	Maximum: **100 characters** 
	
	Provide text in the string format which will be verified from a given proof.

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
	[here](status_codes.md) for more information.

<aside class="notice">
Note: <b>request.invalid</b> response with <b>HTTP status code 400</b> means the request is invalid.
</aside>

>Sample Response  

```json
{
  "reference": "17374217",
  "event": "request.invalid",
  "error": {
    "service": "document",
    "key": "dob",
    "message": "The dob does not match the format Y-m-d."
  },
  "verification_url": ""
}
```

## Complete Sample Request
```sh
Shuftipro.getInstance(clientId, secretKey).shuftiproVerification(“reference”, “GB”, “EN”, “abc@gamil.com”l,
	“www.url.com”, ”www.url.om”, true, true, true, true, true, true, true, "John","Doe", "", "1992-10-10",
	"ABC1234XYZ098", "2025-12-20", "2015-12-20", true, true, true, true, "London UK", false, "", "", "",
	true,false, "", MainActivity.this, MainActivity.this);

```

# Test IDs
Shufti Pro provides the users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information. <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any questions/queries regarding implementation of SDK please feel free to contact our [tech support](mailto:support@shuftipro.com).

## Copyright
2016-18 © Shufti Pro Ltd.

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
Shuftipro instance = Shuftipro.getInstance(clientId: "your-clientId",secretKey: "your-secretKey", async: false);
```
## Sample Request
For **Sample** verification request
```sh
instance.shuftiproVerification(reference: "17374217",country: "GB", 
				      language: "EN", email: "example@gmail.com",
				      callback_url: "your-callback_url",
                                      redirect_url: "your-redirect_url",
				      isToMakeFaceVerification: true,
                                      isToPerformDocumentationVerification: true, 
				      isSupportPassportType: true,
				      isSupportIdCardType: true,    				    	     					   	      isSupportDrivingLicenseType: true,
				      isSupportCreditCardType: true,
				      nameOnDocument: true, 
				      dob: true,
				      documentNumber: true,
				      expiryDate: true,
				      issueDate: true,
                                      isToPerformAddressVerification: true, 
				      fullAddress: true, 
				      name: true,
				      isUtilityBillSupportedType: true, 					      					      isIdCardSupportedType: true,
				      isBankStatementSupportedType: true,
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
						}
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

	Once verification is complete, User is redirected to this link after showing the results.
	
* ## isFaceVerification

	Required: **No**  
	Type: **boolean** 

	Set value to **true** for face verification.
	
* ## isToPerformDocumentationVerification

	Required: **No**  
	Type: **boolean** 

	Set value to **true** for documentation verification.

* ## isSupportPassportType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using passport.	

* ## isSupportIdCardType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using Id card.

* ## isSupportDrivingLicenseType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using driving lisence.

* ## isSupportCreditDebitCardType

	Required: **No**  
	Type: **boolean** 

    If you set it **true** user will be able to verify data using credit/debit card.

* ## nameOnDocument

	Required: **No**  
	Type: **boolean** 

    Set **true** to perform fisrst and last name extraction from provided proofs.

* ## dob

	Required: **No**  
	Type: **boolean**  

   Set **true** to perform dob extraction from provided proofs.

* ## documentNumber

	Required: **No**  
	Type: **String**  

  Set **true** to perform document number extraction from provided proofs.

* ## expiryDate

	Required: **No**  
	Type: **boolean**  

  Set **true** to perform expiry date extraction from provided proofs.

* ## issueDate

	Required: **No**  
	Type: **boolean**  

  Set **true** to perform issue date extraction from provided proofs.

	
* ## isToPerformAddressVerification

	Required: **No**  
	Type: **boolean** 

	Set value to **true** for address verification verification.

* ## isUtilityBillSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using utility bills.

* ## isIdCardSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using Id card.

* ## isBankStatementSupportedType

	Required: **No**  
	Type: **boolean** 

	If you set it **true** user will be able to verify data using bank statements.
	
* ## fullAddress

	Required: **No**  
	Type: **boolean**  

	Set **true** to perform full address extraction from provided proofs. Allowed Characters are numbers, alphabets, dots, dashes, spaces, underscores, hashes and commas.

* ## name
	Required: **No**  
	Type: **boolean**

  Set **true** to extract name from provided proofs.

* ## Asyncronous Feedback
	
	If **async** value is set to **true** while creating instance you'll instantly get the user's control back so you don't have to wait for the verification results. When a request is completed you'll automatically get a callback.

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

## Sample project setup
In HomeActivity.java file add your **Client ID** on line 32 and **Secret Key** on line 33. Thats it!
> **Note:** Run project on a real device.

## Complete Sample Request
```sh

Shuftipro.getInstance(clientId, secretKey, false).shuftiproVerification("17374217", "GB", "EN", "abc@gmail.com", 
"www.url.com", "www.url.com", true, true, true, true, true, true, true, true, true, true, false, true, 
true, false, true, true, true, MainActivity.this, MainActivity.this);

```

# Test IDs
Shufti Pro provides the users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information. <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any questions/queries regarding implementation of SDK please feel free to contact our [tech support](mailto:support@shuftipro.com).

## Revision History

Date            | Description 
--------------- | ------------
07 Nov 2018     | Add async key in the sdk.

## Copyright
2016-18 © Shufti Pro Ltd.

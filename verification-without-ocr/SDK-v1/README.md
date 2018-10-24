[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What Is This?
Shufti Pro is an Identity Verification software as a service that enables enterprises and individuals to secure their KYC and AML compliance. With quick, and realtime results, it acts as a security measure for sound risk assessment by using Face Verification, Document Verification, Information Authentication, and PEP's, Criminals and Money Laundering Checks. It is the go-to option for Payment Systems, FinTECH industry, Crypto, Banks, Lending Economy & Trading platforms.

# Table of contents
* [Basic Setup](#basic-setup)
* [General Requirements](#general-requirements)
* [SDK Installation Guide](#sdk-installation-guide)
* [Permissions](#permissions)
* [Integration](#integration)
* [Verifications](#verifications)
  * [Identity Verification](#identity-verification)
  * [Card Present Verification](#card-present-verification)
* [Response Logging](#response-logging)
* [Response Status Codes](#response-status-codes)
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
5. Select '+' icon from the top right corner → select 'module dependency' → select 'shufti_pro'.

## Permissions:
Application uses the following permissions which you may have to add in privacy policy of the app. Both permissions are handled in SDK.
1. Camera
2. Internet

## Verifications:
Shufti Pro supports two modes of verification:<br />
**1. Identity Verification**<br />
**2. Card Present Verification**

## Integration: 
See the sample project provided to learn the most common use. Make sure to build on real device.
```
import com.shufti.pro.sdk.Shuftipro;
import com.shufti.pro.sdk.interfaces.ShuftiVerifyListener;
```
Make an instance 
```
Shuftipro instance = Shuftipro(clientId: "your-clientId",
                               secretKey: "your-secretKey");
```
## Card Present Verification
For **Card Present** verification
```sh
instance.creditCardVerification(country: "your country",
                                      cardFirst6Digits: "your cardFirst6Digits",
                                      cardLast4Digits: "your cardLast4Digits",
                                      phoneNumber: "phoneNumber",
			              activity:”your caller activity”,
			              Shuftiverifylistener: new ShuftiVerifyListener(){
				 
					@Override
					public void verificationStatus(HashMap<String, String> responseSet) {

					   String status = responseSet.get("status_code");

					   if(status.equalsIgnoreCase("SP1")){
						//Do anything you want.. I am showing a toast message
					       Toast.makeText(this, "Status : Verified...", Toast.LENGTH_LONG).show();
					   }else{
						//Do anything you want.. I am showing a toast message
					       String message = responseSet.get("message");
					       Toast.makeText(this, "Status : Not Verified..." + message, Toast.LENGTH_LONG).show();
					   }});

```


#### Request Parameters 

| Parameter | Description |
| ------ | ------ |
| cardFirst6Digits | First 6 digits of the customer’s credit/debit card number. |
| cardLast4Digits | Last 4 digits of the customer’s credit/debit card number.  |
| country | Full Country name or ISO2 Code. Example: United Kingdom or GB. |
| phoneNumber | Customer’s phone number with country code. Example: +440000000000 |

## Identity Verification
For **Identity** verification using ID documents (Methods: "**driving_license**" or "**passport**" or "**id_card**")
```sh
instance.documentVerification(method: "type of method for verification",
                            firstName: "your first name",
                            lastName: "your last name",
                            dob: "yyyy-mm-dd",
                            country: "your country",
                            phoneNumber: "your phone number",
		            activity:”your caller activity”,
		            Shuftiverifylistener: new ShuftiVerifyListener(){
			 
				@Override
				public void verificationStatus(HashMap<String, String> responseSet) {

				   String status = responseSet.get("status_code");

				   if(status.equalsIgnoreCase("SP1")){

					//Do anything you want.. I am showing a toast message
				       Toast.makeText(this, "Status : Verified...", Toast.LENGTH_LONG).show();
				   }else{

					//Do anything you want.. I am showing a toast message
				       String message = responseSet.get("message");
				       Toast.makeText(this, "Status : Not Verified..." + message, Toast.LENGTH_LONG).show();
				   }}) ;

```

## Response Logging
Response of verification can be logged via the code given below. You can see this in LogCat at runtime. Write this code in Response listener of SDK:
```sh
 String status = responseSet.get("status_code");

        if(status.equalsIgnoreCase("SP1")){
	
           Log.i("tagResponse","Verified");
        }else{
	
            String message = responseSet.get("message");
            Log.i("tagResponse",message);
        }

```
**Note:** Run project on a real device.

#### Request Parameters 

| Parameter | Description |
| ------ | ------ |
| method | Which type of verification would you like for your customers? <br> Possible   values: <br> <ul><li> passport </li><li> driving_license </li><li> id_card</li></ul> |
| firstName | Customer’s First Name. The maximum length of the string is 32 characters and minimum required length is 2 characters. |
| lastName | Customer’s Last Name. The maximum length of the string is 32 characters and minimum required length is 2 characters. |
| dob | Customer’s date of birth (YYYY-MM-DD). Example: 1980-01-31 |
| country | Full Country name or ISO2 Code. Example: United Kingdom or GB. |
| phoneNumber | Customer’s phone number with country code. Example: +440000000000 |


## Response Status Codes 
| Status Code | Description |
| ------ | ------ |
| SP0 | Not Verified |
| SP1 | Verified |
| SP2 | Success! -- Contains the redirect URL in message parameter |
| SP11 | Length Validation -- [parameter_name] maximum and minimum length limit is [parameter_name] characters. |
| SP14 | Duplicate reference -- If a duplicate reference is provided. |
| SP15 | Invalid client ID -- Client ID is invalid or not found. |
| SP16 | Missing required parameter -- ["parameter_name"] is required but either missing or empty |
| SP17 | Invalid format -- ["parameter_name"] is not in the correct format. |
| SP18 | Invalid signature -- Invalid request signature. |
| SP19 | Invalid country code -- Invalid country code or country is not supported. |
| SP20 | Invalid Phone No -- Invalid phone number is provided. |
| SP21 | Invalid Method Name -- Given verification method is not supported. |
| SP23 | Invalid DOB -- Date of birth is not valid. |
| SP24 | Blocked Client -- Your account is not active. |
| SP25 | Request Timeout -- Send in callback when request timeout occurs. |
| SP26 | User lands on the verification page. |
| SP27 | Request is already processed. |

## Sample project setup
In DetailFragment.java file add your **Client ID** on line 86 and **Secret Key** on line 87, thats it!
> **Note:** Run project on a real device.

# Test IDs
Shufti Pro provides the users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information. <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realId.jpg)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realId.jpg) <br>

## Contact
If you have any questions/queries regarding implementation of SDK please feel free to contact our [tech support](mailto:support@shuftipro.com).

## Copyright
2016-18 © Shufti Pro Ltd.

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What is Shufti Pro?
Shufti Pro is an AI based Identity Verification SaaS (Software as a Service) that offers real-time, global verifications for seamless KYC/AML/KYB compliance. 

It provides businesses with a sound risk-cover and helps prevent fraud with its automated identity verification services including: Face Verification, Document and Address Verification, Biometric Consent and 2 Factor Authentication.

AML screening services enable clients to gain an extra layer of security, by scanning cybercriminals and fraudsters at source. 

An easy to integrate API allows smooth customer onboarding and establishes longstanding trust, while optimising customer processing costs altogether. 

Shufti Pro is the go-to ID authentication solution for digital payment systems, FinTech firms, Cryptocurrency, banks, and trading platforms. Businesses can eradicate financial risk and digital ID theft, and boost operating efficiency all in a matter of seconds. 


# Table of contents
* [Basic Setup](#basic-setup)
* [General Requirements](#general-requirements)
* [Permissions](#permissions)
* [Integration](#integration)
* [Test IDs](#test-ids)
* [Contact](#contact)
* [Copyright](#copyright)

# Basic Setup
## General Requirements
The following are minimum requirements for SDK:
- API Level 21  and higher
- Internet connection
- Device with Camera

## Permissions:
The Shufti Pro application requires permission from your device to access the following:
1. Camera
2. Internet
3. External Storage<br />
`Note: All permissions are handled in SDK.`

## Verifications:
Shufti Pro supports four modes of verification:<br />
**1. Face Verification**<br />
**2. Document Verification**<br/>
**3. Address Verification**<br/>
**4. Consent Verification**<br/>

## Usage: 
Here is a quick guide to get you started with Shufti Pro integration. Please follow **one** of the following the instructions to integrate Shufti Pro into your project.

### Gradle:
Step 1. Add it in your root build.gradle:

```sh
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency:

```sh 
dependencies {
      implementation 'com.github.shuftipro:Shuftipro-Verification:1.2.2'
}
```

### or Maven:
Step 1. Add the JitPack repository to your build file

```sh
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
</repositories>

```
Step 2. Add the dependency

```sh
<dependency>
	    <groupId>com.github.shuftipro</groupId>
	    <artifactId>Shuftipro-Verification</artifactId>
	    <version>1.2.2</version>
</dependency>
```

## Integration: 
Take a look at the sample project and learn the most common use of the API. Make sure to build on a real device.
```
import com.shufti.shuftipro.Shuftipro;
import com.shufti.shuftipro.listeners.ShuftiVerifyListener;
```


 Make an instance:<br>
 Instance can be created in two ways. First is by using `clientId` and `secretKey`, other one is by providing `accessToken`.<br>
 You can read more about `accessToken`[here](https://api.shuftipro.com/api/docs/#access-token)
```
//using clientId and secretKey
Shuftipro instance = Shuftipro.getInstance(clientId: "your-clientId",secretKey: "your-secretKey");

//or using accessToken
Shuftipro instance = Shuftipro.getInstance(accesstoken: "sp-accessToken");
```
## Sample Request
For a sample verification request, enter the following: 
```sh
instance.shuftiproVerification(JSONObject: "your-requested-json-object"      
			       parentActivity: "your-caller-activity",
			       ShuftiVerifyListener: new ShuftiVerifyListener(){
				 
				      @Override
				      public void verificationStatus(HashMap<String, String> responseSet) {

				      //I'm printing response here
				      Log.d("Response", responseSet.toString());
				   }
			       });
```


# Request Parameters 

It is important to note here that each verification option offered by Shufti Pro is an exclusive service, and is activated following the nature of user’s request. Clients can choose one or all of the optional API keys. In case a key is given in document and address verification, and no value is provided, then OCR will be performed for those particular keys. 

There are a total of four services which include face, document, address and consent. 

All verification services are optional. You can provide Shufti Pro a single service or mixture of several services for verifications. All keys are optional too. If a key is given in document or address sevice and no value is provided then OCR will be performed for those keys. 

* ## JSONObject

	Required: **Yes**  
	Type: **JSONObject**  

	This is the json object which we will be sent in the verification request.  Please consult [here](https://github.com/shuftipro/RESTful-API-v1.3/tree/master/on-site_with_ocr) for more information.




* ## parentActivity

	Required: **Yes**  
	Type: **parentActivity**  

	This is the reference of your caller activity in which you're creating an instance.

* ## shuftiVerifyListener

	Required: **Yes**  
	Type: **Interface**  

	This is the reference of your activity which is implemented in the ShuftiVerifyListener interface.
	

## Sample Request
```sh
	JSONObject jsonObject = new JSONObject();
	try {
            jsonObject.put("reference", "13445566743");
            jsonObject.put("country", "GB");
            jsonObject.put("language", "EN");
            jsonObject.put("email", "yourmail@gmail.com");
            jsonObject.put("callback_url", "https://www.yourdomain.com");
            jsonObject.put("redirect_url", "");
            jsonObject.put("verification_mode", "any");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating face object
        JSONObject faceObject = new JSONObject();
        try {
            faceObject.put("proof", "");
            jsonObject.put("face", faceObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	
	//Creating document object
        JSONObject documentationObject = new JSONObject();
        ArrayList<String> doc_supported_types = new ArrayList<String>();

        doc_supported_types.add("passport");
        doc_supported_types.add("id_card");
        doc_supported_types.add("driving_license");
        doc_supported_types.add("credit_or_debit_card");

        try {
            documentationObject.put("proof", "");
            documentationObject.put("supported_types", new JSONArray(doc_supported_types));

            //Set parameters in the requested object
            documentationObject.put("name", "");
            documentationObject.put("dob", "");
            documentationObject.put("document_number", "");
            documentationObject.put("expiry_date", "");
            documentationObject.put("issue_date", "");
            documentationObject.put("fetch_enhanced_data", "1");

            jsonObject.put("document", documentationObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

	//Now, making an instance and calling the verification method
	Shuftipro instance = Shuftipro.getInstance(clientId, secretKey);
        instance.shuftiproVerification(jsonObject, HomeActivity.this, HomeActivity.this);
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
	A unique reference will be provided at the time of request, so that you can identify the response in relation to the request made.

* <h3>event</h3>

	The request event shows the status of user’s request, and is different for every response. For more information, click
	[here](status_codes.md)

## Sample project setup
In HomeActivity.java file add your **Client ID** on line 36 and **Secret Key** on line 37. Thats it!
> **Note:** Run project on a real device.
	
# Test IDs
Shufti Pro provides the users with a number of test documents. Customers may use these to test the demo, instead of presenting their actual information.  <br><br>


[![](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1)](https://raw.githubusercontent.com/shuftipro/integration-guide/master/assets/realFace.jpg?v=1) 

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/real-id-card.jpg)

[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.3/master/assets/fake-id-card.jpg)

## Contact
If you have any queries regarding the implementation of SDK, please feel free to contact Shufti Pro [tech support](mailto:support@shuftipro.com).

## Copyright
2018-20 © Shufti Pro Ltd.

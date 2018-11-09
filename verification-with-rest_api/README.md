[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# What Is This?
Shufti Pro is an Identity Verification software as a service that enables enterprises and individuals to secure their KYC and AML compliance. With quick, and realtime results, it acts as a security measure for sound risk assessment by using Face Verification, Document Verification, Information Authentication, and PEP's, Criminals and Money Laundering Checks. It is the go-to option for Payment Systems, FinTECH industry, Crypto, Banks, Lending Economy & Trading platforms.

# Table of contents
* [Basic Setup](#basic-setup)
* [General Requirements](#general-requirements)
* [SDK Installation Guide](#sdk-installation-guide)
* [Permissions](#permissions)
* [Integration](#integration)
* [Test IDs](#test-ids)
* [Contact](#contact)
* [Copyright](#copyright)

# Basic Setup
## General Requirements
Followings are minimum requirements for SDK:
- API Level 17  and higher
- Internet connection
- Camera

## SDK Installation Guide
1. Select File → New → New Module → Import .jar/Aar package from top menu of Android Studio.
2. Select the provided 'shuftipro_sdk.aar' file.
3. Right click on 'app module' → Select 'Module setting'.
4. Select 'Dependencies' from the right pane.
5. Select '+' icon from the top right corner → select 'module dependency' → select 'shuftipro-sdk'.

## Permissions:
Application uses the following permissions which you may have to add in privacy policy of the app. All permissions are handled in SDK.
1. Camera
2. Internet
3. External Storage

## Verifications:
Shufti Pro supports four modes of verification:<br />
**1. Face Verification**<br />
**2. Document Verification**<br/>
**3. Address Verification**<br/>
**4. Consent Verification**<br/>
**5. Background Checks**<br/>
**6. Phone**<br/>

## Integration: 
See the sample project provided to learn the most common use. Make sure to build on real device.
```
import com.shufti.sdk.shuftipro.Shuftipro;
import com.shufti.sdk.shuftipro.listeners.ShuftiVerifyListener;
```
Make an instance 
```
Shuftipro instance = Shuftipro.getInstance(clientId: "your-clientId",secretKey: "your-secretKey");
```
## Sample Request
For **Sample** verification request
```sh
instance.shuftiproVerification(JSONObject: "your-requested-json-object"      
			       parentActivity: "your-caller-activity",
			       ShuftiVerifyListener: new ShuftiVerifyListener(){
				 
					@Override
					public void verificationStatus(HashMap<String, String> responseSet) {
						
							//I'm printing response here
							Log.d("Response", responseSet.toString());
				   }});
```


# Request Parameters 

It is important to note here that each service module is independent of other and each one of them is activated according to the nature of request received from you. There are a total of four services which include face, document, address, consent.

All verification services are optional. You can provide Shufti Pro a single service or mixture of several services for verifications. All keys are optional too. If a key is given in document or address sevice and no value is provided then OCR will be performed for those keys. 

* ## JSONObject

	Required: **Yes**  
	Type: **JSONObject**  

	This is the json object which we will be sent in the verifiction request.  It is important to note here that each service module is independent of other and each one of them is activated according to the nature of request received from you. There are a total of six services which include face, document, address, consent, phone and background_checks. For more information click [here](https://github.com/shuftipro/RESTful-API-v1.3/blob/master/on-site_with_ocr/on-site_with_ocr.md).

All verification services are optional. You can provide Shufti Pro a single service or mixture of several services for verifications. All keys are optional too. If a key is given in document or address sevice and no value is provided then OCR will be performed for those keys.


* ## parentActivity

	Required: **Yes**  
	Type: **parentActivity**  

	This is the reference of your caller activity in which you're creating an instance.

* ## shuftiVerifyListener

	Required: **Yes**  
	Type: **Interface**  

	This is the reference of your activity which implement the ShuftiVerifyListener interface.
	

## Sample Face Request
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

        //I am adding face object, you may add whatever you want
        JSONObject faceObject = new JSONObject();
        try {
            faceObject.put("proof", "");
            jsonObject.put("face", faceObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

	//Now, making an instance and calling the verification method
        
	Shuftipro instance = Shuftipro.getInstance(clientId, secretKey);
        instance.shuftiproVerification(jsonObject, HomeActivity.this, HomeActivity.this);
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

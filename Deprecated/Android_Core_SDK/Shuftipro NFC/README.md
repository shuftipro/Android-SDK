# ShuftiPro NFC SDK
## Table of Contents
* [General Requirements](#general-requirements)
* [Permissions](#permissions)
* [Prerequisite](#prerequisite)
* [Integration](#integrating-nfc-sdk)
* [nfc_verification Parameter](#nfc_verification-parameter)

## General Requirements
Minimum requirements for SDK include
- Android 5.0 (API level 21) or higher
- Internet connection
- Camera
## Permissions:
The Shufti Pro application requires admin permission to access the following:
- Camera
- External Storage<br>
**Note:** All permissions are handled in the SDK.
## Prerequisite
You must integrate our Shuftipro Core-SDK to be able to get entertained by our NFC SDK services.
## Integrating NFC SDK:
If you have successfully integrated Core-SDK, now you can easily integrate our NFC SDK simply by following the instructions below:
 
**Step 1:** Go to root-level build.gradle in your project and add the following:
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2:** Add the dependency in app level bulid.gradle:
```
dependencies {
		implementation 'com.github.shuftipro:NFC:2.0.2'
}
```
**Step 3:** Enable multi dex in project level bulid.gradle:
```
android {
    defaultConfig {
        multiDexEnabled true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    aaptOption {
        noCompress "tflite"
    }
}
```
**Step 4:** Changes in Request Object
Simply add the following line in the respective service-objects inside request object:

- For Document Object 
```
{
    ...
	documentObject.put("nfc_verification", true);
}
```

- For Document Two Object
```
{
    ...
    documentTwoObject.put("nfc_verification", true);
}
```
- For Address Object
```
{
    ...
    addressObject.put("nfc_verification", true);
}
```
You have successfully integrated our NFC SDK.
## nfc_verification Parameter 
Required: **No**<br>
Type: **boolean**<br>
Accepted values: **true, false**<br>

Near Field Communication (NFC) is a set of short-range wireless technologies. NFC allows sharing small payloads of data between an NFC tag and an NFC-supported device. NFC Chips found in modern passports and similar identity documents contain additional encrypted information about the owner. This information is very useful in verifying the originality of a document. NFC technology helps make the verification process simple, quicker and more secure. This also provides the user with contactless and input less verification. ShuftiPro's NFC verification feature detects MRZ from the document to authenticate NFC chips and retrieve data from it, so the authenticity and originality of the provided document could be verified, if set to TRUE. nfc_verification parameter should be added into the service object(document, document_two, address) for which you want to perform nfc verification. Nfc verification is allowed only on e-passports under document, document_two and address service only. The nfc service is not available in hybrid webview for now.

Other parameters are explained [here](#Request-Object-Parameters).
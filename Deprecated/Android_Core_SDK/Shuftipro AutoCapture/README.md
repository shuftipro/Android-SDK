# ShuftiPro Auto-Capture SDK

## Table of Contents
* [General Requirements](#general-requirements)
* [Permissions](#permissions)
* [Prerequisite](#prerequisite)
* [Integration](#Integrating-auto-capture-sdk)
* [autoCapture Parameter](#autocapture-parameter)

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
You must integrate our **Shufti Pro Core-SDK** to be able to get entertained by our Auto-Capture SDK services.
## Integrating Auto-Capture SDK:
If you have successfully integrated Core-SDK, now you can easily integrate our auto-capture SDK simply by following the instructions below:<br>
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
    ...
	implementation 'com.github.shuftipro:auto-capture:2.0.2'
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
**Step 4:** Make Config object
JSONObject Config=new JSONObject();

        try {
            ...
		Config.put("autoCapture",true);   //New Line in Config Object

        } catch (JSONException e) {
            e.printStackTrace();
        }
You have successfully integrated our Auto-Capture SDK.


## autoCapture Parameter
Required: **No**<br>
Type: **boolean**<br>
Accepted values: **true, false**<br>

AutoCapture provides click-less capturing of required objects. If autoCapture value is set to TRUE it would capture the image without the need of clicking the button. AutoCapture is available for face, document/ document- two (passport, id card, driving licence, credit or debit card) and address. The user would also be able to capture manually while auto capture is set to true. If autoCapture is set to FALSE then the user must capture the image by clicking the capture button. The document will only be captured if it matches with the provided country and document type as well, otherwise errors will be shown.

Other parameters are explained  [here](#Request-Object-Parameters).
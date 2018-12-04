[![](https://raw.githubusercontent.com/shuftipro/RESTful-API-v1.2/master/assets/banner.jpg)](https://www.shuftipro.com/)

# Introduction

This Shufti Pro SDK document has been compiled to assist the developers and technical experts in the integration of Shufti Pro with their mobile-based platforms and applications. Step by step guide has been designed to help explain verification services, technical processes that are performed for these verifications, different kind of verification data that is required and data formats that are required to forward verification data. 

Shufti Pro uses a mix of Artificial Intelligence & Human Intelligence technology that helps it provide 99.6% authentic verification results to its customers in quickest possible response time.

Shufti Pro SDK offers both OCR based and non-OCR verification results. 

## Verification
Rest API functions at the backbone of Shufti Pro SDK, offering technical assistance for smooth mobile-based verifications as well. All the features and data points of API works perfectly with SDK integration, providing a smooth user experience.
Both Rest API and SDK of Shufti Pro operates with a JSON object defining data keys for its Verification request. You can either use SDK or Rest API, as both works perfectly for mobile integration. In case you opt for Rest API, lesser code will have to be written to complete the integration process. 
Shufti Pro allows for both OCR based as well non-OCR Verifications. Both OCR and non-OCR verifications performed by Shufti Pro take assistance from the native camera of the end user's phone. Thus the quality of images/video, received for verification, heavily relies on the resolution prowess of the smartphone camera.

* ### With OCR
During verification with OCR, an end-user or Shufti Pro customer simply provides identity documents to Shufti Pro for verification. The smart system of Shufti Pro extracts credentials automatically from the provided documents and verify them as well. Consult [This Document](verification-with-ocr) to understand the technical requirements to perform verification with OCR.
    
* ### Without OCR
During verification without OCR, Shufti Pro customers or end-user is required to provide not only verification data but also provide proof of that data as well in the form of identity documents. Consult [This Document](verification-without-ocr) to understand the technical requirements to perform verification without OCR.

* ### With Rest Api
In case, you opt to use mobile verification with our Rest API, a web-view is displayed to the end user which is built upon HTML 5. All the data points and fields are properly defined in the Rest API and the format for sending verification data will be a JSON object, similar to other mobile verification formats (OCR & Non-OCR). Consult [This Document](verification-with-rest-api) to understand the technical requirements to perform verification without Rest Api.


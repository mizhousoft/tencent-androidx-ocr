package com.mizhousoft.ocr.sdk;

public interface OCRResultListener
{
    void onSucceed(String vin, String base64Str, String requestId);

    void onFailed(String errorCode, String message, String requestId);
}

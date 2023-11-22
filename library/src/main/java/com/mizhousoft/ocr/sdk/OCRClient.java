package com.mizhousoft.ocr.sdk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.mizhousoft.ocr.R;
import com.tencent.ocr.sdk.common.CustomConfigUi;
import com.tencent.ocr.sdk.common.ISdkOcrEntityResultListener;
import com.tencent.ocr.sdk.common.OcrModeType;
import com.tencent.ocr.sdk.common.OcrSDKConfig;
import com.tencent.ocr.sdk.common.OcrSDKKit;
import com.tencent.ocr.sdk.common.OcrType;
import com.tencent.ocr.sdk.entity.OcrProcessResult;
import com.tencent.ocr.sdk.entity.VinOcrResult;

public class OCRClient
{
    public static void init(Context context, String secretId, String secretKey)
    {
        OcrModeType modeType = OcrModeType.OCR_DETECT_MANUAL;
        OcrType ocrType = OcrType.VinOCR;
        OcrSDKConfig configBuilder = OcrSDKConfig.newBuilder(secretId, secretKey, null).setOcrType(ocrType).setModeType(modeType).build();

        OcrSDKKit.getInstance().initWithConfig(context, configBuilder);
    }

    public static void updateToken(String secretId, String secretKey, String tempToken)
    {
        OcrSDKKit.getInstance().updateFederationToken(secretId, secretKey, tempToken);
    }

    public static void start(Activity activity, final OCRResultListener listener, String tips, boolean tipsIconHidden)
    {
        CustomConfigUi configUi = new CustomConfigUi();
        configUi.setLandscape(false);
        configUi.setLightImageOffResId(R.drawable.ocr_flash_off);
        configUi.setLightImageOnResId(R.drawable.ocr_flash_on);
        configUi.setTakePicturesResId(R.drawable.ocr_ic_take_photo_open);
        configUi.setShowTips(null != tips);
        configUi.setShowTitleBar(false);
        configUi.setShowStatusBar(false);
        configUi.setShowTipsText(tips);
        configUi.setShowTipsIcon(!tipsIconHidden);

        OcrSDKKit.getInstance()
                .startProcessOcrResultEntity(activity, OcrType.VinOCR, configUi, VinOcrResult.class, new ISdkOcrEntityResultListener<VinOcrResult>()
                {
                    @Override
                    public void onProcessSucceed(VinOcrResult vinOcrResult, OcrProcessResult ocrProcessResult)
                    {
                        Log.i("OCR", "carLicensePlate: " + vinOcrResult.getVin());

                        if (null != listener)
                        {
                            String base64Str = ocrProcessResult.getImageBase64Str();

                            listener.onSucceed(vinOcrResult.getVin(), base64Str, vinOcrResult.getRequestId());
                        }
                    }

                    @Override
                    public void onProcessFailed(String errorCode, String message, OcrProcessResult ocrProcessResult)
                    {
                        String requestId = ocrProcessResult.getRequestId();

                        Log.e("OCR", "requestId: " + requestId + ", errorCode: " + errorCode + ", message:" + message);

                        if (!"OcrSdk.UserCancelOcr".equals(errorCode) && null != listener)
                        {
                            listener.onFailed(errorCode, message, requestId);
                        }
                    }
                });
    }

    public static void release()
    {
        OcrSDKKit.getInstance().release();
    }
}

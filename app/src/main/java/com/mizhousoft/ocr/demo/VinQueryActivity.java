package com.mizhousoft.ocr.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.mizhousoft.ocr.sdk.OCRClient;
import com.mizhousoft.ocr.sdk.OCRResultListener;
import com.mizhousoft.widget.activity.ToolbarActivity;
import com.mizhousoft.widget.keyboard.VinEditText;
import com.mizhousoft.widget.toast.SimplexToast;
import com.mizhousoft.widget.util.ActivityUtils;

public class VinQueryActivity extends ToolbarActivity
{
    private VinEditText vinEditText;

    private LinearLayout photoLinearLayout;

    private PhotoView photoView;

    public static void startActivity(Activity activity)
    {
        ActivityUtils.startActivity(activity, VinQueryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin_query);

        renderToolbar("VIN查询");
        bindView();

        vinEditText.enableVinKeyboard(this);
    }

    private void bindView()
    {
        vinEditText = this.findViewById(R.id.et_vin);
        photoLinearLayout = this.findViewById(R.id.ll_photo_view);
        photoView = this.findViewById(R.id.photo_view);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        OCRClient.release();
    }

    public void scanVin(View view)
    {
        OCRClient.start(this, new OCRResultListener()
        {
            @Override
            public void onSucceed(String vin, String base64Str, String requestId)
            {
                vinEditText.setText(vin);

                byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                photoView.setImageBitmap(bitmap);
                photoLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(String errorCode, String message, String requestId)
            {
                SimplexToast.show("识别车架号失败");
            }
        }, null, false);
    }

    public void queryVin(View view)
    {
        String vin = vinEditText.getText().toString();

    }
}
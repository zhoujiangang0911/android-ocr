
package edu.wetisai.orc;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import edu.wetisai.ocr.R;

public class BitmapORC extends Activity {
    private ImageView image;
    private TextView tessResults;
    private Bitmap mTextBitmap;
    private TessBaseAPI baseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orc_bitmap);
        findView();

        //mTextBitmap = getTextImage("hello world123", 300, 300);
        mTextBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.d);
        image.setImageBitmap(mTextBitmap);
        
        
        baseApi = new TessBaseAPI();
        Log.i("------zhoujg77", "--"+getSDPath()+"/");
        baseApi.init(getSDPath(), "eng");
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
        baseApi.setImage(mTextBitmap);
        String outputText = baseApi.getUTF8Text();
        tessResults.setText(outputText);
    }
    
    public String getSDPath(){
    	File sdDir = null;
    	boolean sdCardExist = Environment.getExternalStorageState()
    	.equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
    	if (sdCardExist)
    	{
    	sdDir = Environment.getExternalStorageDirectory();//获取跟目录
    	}
    	return sdDir.toString();

    	}
    @Override
    protected void onDestroy() {
        mTextBitmap.recycle();
        baseApi.end();
        super.onDestroy();
    }

    private void findView() {
        image = findViewId(R.id.image);
        tessResults = findViewId(R.id.results);
    }

    private Bitmap getTextImage(String text, int width, int height) {
        final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Paint paint = new Paint();
        final Canvas canvas = new Canvas(bmp);

        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.CENTER);
        paint.setTextSize(24.0f);
        canvas.drawText(text, width / 2, height / 2, paint);
        

        return bmp;
    }

    private <T extends View> T findViewId(int resId) {
        return (T) findViewById(resId);
    }
}

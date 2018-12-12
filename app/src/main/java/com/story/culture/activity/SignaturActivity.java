package com.story.culture.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.flyco.roundview.RoundTextView;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.story.culture.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SignaturActivity extends AppCompatActivity implements View.OnClickListener {
    SignaturePad mSignaturePad;
    RoundTextView save;
    RoundTextView del;
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/story/";
private String name;
    public static void action2SignaturActivityForResult(Activity c, String s) {
        Intent intent = new Intent(c, SignaturActivity.class);
        intent.putExtra("name", s);
        c.startActivityForResult(intent, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatur);
        mSignaturePad = findViewById(R.id.signature_pad);
        save = findViewById(R.id.save);
        del = findViewById(R.id.del);
        del.setOnClickListener(this);
        save.setOnClickListener(this);
        Intent intent = getIntent();
        if(intent!=null){
            name = intent.getStringExtra("name");
        }
        init();
    }

    private void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                Bitmap bitmap = mSignaturePad.getSignatureBitmap();
                String path = saveImageToLocal(this, bitmap);
                Intent data = new Intent();
                data.putExtra("path", path);
                setResult(100, data);
                finish();
                break;
            case R.id.del:
                mSignaturePad.clear();
                break;
        }
    }


    public  String saveImageToLocal(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = SD_PATH +name;
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        SimpleDateFormat df = new SimpleDateFormat("'IMG'_yyyy-MM-dd_HH:mm:ss");
        String fileName =  df.format(new Date())+ ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            if (isSuccess) {
                return file.getAbsolutePath();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        File filePic;
        File files = new File(SD_PATH);
        if (!files.exists()) {
            files.mkdirs();
        }
        try {
            filePic = new File(SD_PATH + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
}

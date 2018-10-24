package com.story.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.story.culture.R;
import com.story.culture.StoryApplication;

import java.io.File;
import java.util.ArrayList;


public class UIUtils {

    public static Context getContext() {
        return StoryApplication.getContext();
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取
     * public static View inflate(int resId){
     * return LayoutInflater.from(getContext()).inflate(resId,null);
     * }
     * <p>
     * /** 获取资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(Context context, int resId) {
        return getResources(context).getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(Context context, int resId) {
        return getResources(context).getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(Context context, int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(Context context, int resId) {
        return getResources(context).getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(Context context, int resId) {
        return getResources(context).getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(Context context, int resId) {
        return getResources(context).getColorStateList(resId);
    }





    /**
     * 拍照获取图片
     */
    public static void takePictures(final Activity context, final File tempFile, final int requestCode) {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            new TedPermission(context)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 指定调用相机拍照后照片的储存路径
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                            context.startActivityForResult(intent, requestCode);
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setDeniedMessage(context.getString(R.string.permission_tips))
                    .setPermissions(Manifest.permission.CAMERA)
                    .check();
        } else {
            Toast.makeText(context, R.string.memory_card_not_exist,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 拍照获取图片
     */
    public static void takePictures2(final Activity context, final int requestCode) {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            new TedPermission(context)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 指定调用相机拍照后照片的储存路径
                            context.startActivityForResult(intent, requestCode);
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setDeniedMessage(context.getString(R.string.permission_tips))
                    .setPermissions(Manifest.permission.CAMERA)
                    .check();
        } else {
            Toast.makeText(context, R.string.memory_card_not_exist,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 拍照
     */
    public static void takePictures3(final Activity context, final int requestCode) {
        new TedPermission(context)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        context.startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

                    }
                })
                .setDeniedMessage(context.getString(R.string.permission_tips))
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }
}



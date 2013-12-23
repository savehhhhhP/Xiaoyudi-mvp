package com.Xiaoyudi_mvp.tool;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by save on 13-12-21.
 * lxl
 */
public class DrawTool {
    final static String TAG = "DrawTool";

    public static float getScope(Bitmap bitmap,int viewWidth,int viewHeight){
        float height_s,width_s,viewScope,bitmapScope,scope;
        viewScope = (float)viewHeight / (float)viewWidth;
        bitmapScope = (float)bitmap.getHeight() / (float)bitmap.getWidth();
        if(bitmapScope <= viewScope){
            //按宽
            width_s = (float)viewWidth;
            scope = width_s / (float)bitmap.getWidth();
        }else{
            //按长
            height_s = (float)viewHeight;
            scope = height_s / (float)bitmap.getHeight();
        }
        return scope;
    }

    /**
     * 按比例缩放图片使图片不失真
     * 保持原图的长宽比，用缩放后最短的边为标准缩放
     */
    public static Bitmap getRealZBitmap(Bitmap bitmap,int viewWidth,int viewHeight) {
        Matrix matrix = new Matrix();
        float height_s,width_s,viewScope,bitmapScope,scope;
        viewScope = (float)viewHeight / (float)viewWidth;
        bitmapScope = (float)bitmap.getHeight() / (float)bitmap.getWidth();
        if(bitmapScope <= viewScope){
            //按宽
            width_s = (float)viewWidth;
            scope = width_s / (float)bitmap.getWidth();
        }else{
            //按长
            height_s = (float)viewHeight;
            scope = height_s / (float)bitmap.getHeight();
        }
        matrix.postScale(scope,scope); //长和宽放大缩小的比例
        Bitmap resizeBmp;
        resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 根据card在bg中向右移动的距离 width 生成缩放后的bitmap
     */
    public static Bitmap getInsertCard(Bitmap bgMap, Bitmap card,float width){
        Bitmap modifyCard=null;
        if(card !=null){
            //按卡片的宽度缩放
            float scope = 0;
            float newWidth = (float) bgMap.getWidth() - 2.0f * width; //新的宽度
            scope = newWidth / card.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scope, scope); //长和宽放大缩小的比例
            modifyCard = Bitmap.createBitmap(card, 0, 0, card.getWidth(), card.getHeight(), matrix, true);
        }
        return modifyCard;
    }
}

package com.Xiaoyudi_mvp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.Xiaoyudi_mvp.activity.R;
import com.Xiaoyudi_mvp.tool.DrawTool;

/**
 * Created by save on 13-12-21.
 */
public class CardElement extends View{
    static final String TAG = "CardElement";
    private Bitmap whight;
    private Bitmap image;
    private float[] imageOffset;//图像的偏移
    private Bitmap bg;
    private Bitmap wood;
    private Bitmap btn;
    private float[] btnOffset;   //按钮的偏移
    private Bitmap res;
    private float scope; // 木框的缩放比例
    private float[] textOfffset;//文字的偏移
    //attr
    private String textName;
    private float textSize;
    private int textStyle;
    private int textColor;
    private int imgResouse;

    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    public CardElement(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CardElement(Context context) {
        super(context);
    }

    public CardElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init( AttributeSet attrs) {
        btnOffset = new float[2];
        imageOffset = new float[2];
        textOfffset = new float[2];
        mPaint = new Paint();
        //TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组
        //在使用完成后，一定要调用recycle方法
        //属性的名称是styleable中的名称+“_”+属性名称
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CardElementView);

        if (array != null) {
            textName = array.getString(R.styleable.CardElementView_textName);
            textSize = array.getDimension(R.styleable.CardElementView_textSize, 36);
            textColor = array.getColor(R.styleable.CardElementView_textColor, Color.RED);

            array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Canvas中含有很多画图的接口，利用这些接口，我们可以画出我们想要的图形
        Log.v(TAG,"onDraw:this.getHeight():"+this.getHeight()+"this.getWidth():"+this.getWidth());
        loadResource();
        // 在画板上绘制图片
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        drawBitmap(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        float width = scope * 150.0f;
        float hight = scope * 90.0f;
        imageOffset[0] = width;
        imageOffset[1] = hight;
        image = DrawTool.getInsertCard(bg, image, width);

        textOfffset[0] = scope * 300.0f;
        textOfffset[1] = scope * 870.0f;
        canvas.drawText(textName,textOfffset[0],textOfffset[1],mPaint);

        canvas.drawBitmap(whight, 0, 0, mPaint);
        canvas.drawBitmap(image, imageOffset[0], imageOffset[1], mPaint);
        canvas.drawBitmap(bg, 0, 0, mPaint);
        canvas.drawBitmap(wood, 0, 0, mPaint);
        canvas.drawBitmap(btn, btnOffset[0], btnOffset[1], mPaint);
    }

    private void loadResource() {
        // 获得资源
        Resources rec = getResources();
        BitmapDrawable common_card_bg = null;
        BitmapDrawable common_card_white = null;
        BitmapDrawable common_card_wood = null;
        BitmapDrawable img = null;
        BitmapDrawable parent_main_editbtn = null;

        if (rec != null) {
            // BitmapDrawable
            parent_main_editbtn = (BitmapDrawable) rec.getDrawable(R.drawable.parent_main_editbtn);
            common_card_bg = (BitmapDrawable) rec.getDrawable(R.drawable.common_card_bg);
            common_card_white = (BitmapDrawable) rec.getDrawable(R.drawable.common_card_white);
            common_card_wood = (BitmapDrawable) rec.getDrawable(R.drawable.common_card_wood);
            img = (BitmapDrawable) rec.getDrawable(R.drawable.donteat);

        }
        // 得到Bitmap
        res = common_card_bg.getBitmap();
        whight = DrawTool.getRealZBitmap(common_card_white.getBitmap(),this.getWidth(),this.getHeight());
        bg = DrawTool.getRealZBitmap(common_card_bg.getBitmap(),this.getWidth(),this.getHeight());
        image = img.getBitmap();
        wood = DrawTool.getRealZBitmap(common_card_wood.getBitmap(),this.getWidth(),this.getHeight());
        //取得缩放比例
        scope = DrawTool.getScope(common_card_bg.getBitmap(),this.getWidth(),this.getHeight());
        //根据缩放后的bg 算出当前按钮的大小
        btn = getBtnSize(bg,parent_main_editbtn.getBitmap());
    }

    private Bitmap getBtnSize(Bitmap bg, Bitmap bitmap) {
        float width = bg.getWidth() / 6.0f;
        btnOffset[0]= bg.getWidth() - width * 1.2f;   //left
        btnOffset[1]= 0;
        return DrawTool.getRealZBitmap(bitmap,(int)width,(int)width);
    }
}

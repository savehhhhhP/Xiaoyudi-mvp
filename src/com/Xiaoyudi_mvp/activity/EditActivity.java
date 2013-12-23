package com.Xiaoyudi_mvp.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.Xiaoyudi_mvp.po.Card;
import com.Xiaoyudi_mvp.util.Constants;
import com.Xiaoyudi_mvp.util.DataBaseHelper;
import com.Xiaoyudi_mvp.util.GlobalUtil;
import com.Xiaoyudi_mvp.view.CardElement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//  976 768       9.7寸
public class EditActivity extends Activity {
    public static final  String TAG = "EditActivity";
    List<CardElement> cardEleList;
    View cardE1;
    View cardE2;
    View cardE3;
    View cardE4;
    View cardE5;
    View cardE6;
    View cardE7;
    View cardE8;
    View cardE9;
    Map<Integer, Card> cardMap;

    boolean firstTime;    //首次进入标记

    SharedPreferences sp;
    MyHandler myHandler;
    DataBaseHelper myDbHelper;                                  //数据库

    String[] images ;
    String[] audios ;

    private void initDataBase() {
        myDbHelper = DataBaseHelper.getDataBaseHelper(EditActivity.this);           //lxl获取数据库
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        setContentView(R.layout.edit);

        init();            //lxl系统初始化         对于是否第一次启动给出判断
        initDataBase();    //初始化数据库
        initPrivateData();  //
        initDir();         //lxl应用第一次启动的时候  初始化 所有需要的路径文件夹      YY PIC
        LoadView();
    }

    private void initPrivateData() {
        cardEleList = new ArrayList<CardElement>();
    }

    private void LoadView() {
        /*
        // 获取屏幕密度（方法1）
        int screenWidth  = getWindowManager().getDefaultDisplay().getWidth();       // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();      // 屏幕高（像素，如：800p）
        Log.i(TAG + "  getDefaultDisplay", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
        */
        cardE1 = findViewById(R.id.card1);
        cardE2 = findViewById(R.id.card2);
        cardE3 = findViewById(R.id.card3);
        cardE4 = findViewById(R.id.card4);
        cardE5 = findViewById(R.id.card5);
        cardE6 = findViewById(R.id.card6);
        cardE7 = findViewById(R.id.card7);
        cardE8 = findViewById(R.id.card8);
        cardE9 = findViewById(R.id.card9);

    }

    private void init() {
        myHandler = new MyHandler();

        sp = getSharedPreferences("xiaoyudi", 0);
        firstTime = sp.getBoolean("firstTime", true);
        //第一次启动时候复制资源到手机XIAOYUDI目录
        if(firstTime){
            images = this.getResources().getStringArray(R.array.images);
            audios = this.getResources().getStringArray(R.array.audios);
            try {
                String path_image = GlobalUtil.getExternalAbsolutePath(this) + "/" + "XIAOYUDI" + "/PIC/";
                String path_audio = GlobalUtil.getExternalAbsolutePath(this) + "/" + "XIAOYUDI" + "/YY/";
                copyAssets(images, path_image);  //复制资源 图片
                copyAssets(audios, path_audio);  //复制资源 声音
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyAssets(String[] resources, String path) throws IOException {

        for (int i = 0; i < resources.length; i++) {
            String outFileName = path + resources[i];
            File file = new File(outFileName);
            if (!file.exists()) {
                InputStream myInput = getAssets().open(resources[i]);
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
                Log.i(TAG, "copyAssets() over");
            }
        }
    }
    public void initDir() {
        if (firstTime) {
            File dir_YY = new File(Constants.dir_path_yy);
            File dir_PIC = new File(Constants.dir_path_pic);
            if (!dir_YY.exists()) {
                dir_YY.mkdirs();                                       //在存在的目录中创建文件夹  YY-声音
            }
            if (!dir_PIC.exists()) {
                dir_PIC.mkdirs();                                      //PIC 图片
            }
            Log.i(TAG, "initDir() over");
        }
    }


    class MyHandler extends Handler {

        public MyHandler() {

        }
        public MyHandler(Looper L) {
            super(L);
        }

        //子类必须重写此方法,接受数据

        @Override

        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);

            //此处可以更新UI

            Bundle b = msg.getData();

            String error = b.getString("msg");
            Toast.makeText(EditActivity.this, error, Toast.LENGTH_LONG).show();
        }

    }
}

package com.example.applicationzlschoold;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mHomeTimeBt;
    private ImageView mHomeTopImg;
    private ImageView mHomeBottomImg;
    private int time = 5;
    private static final String TAG = "MainActivity";
    private int heightPixels;
    private int widthPixels;
    private String info_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(ApiServcice.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServcice apiServcice = build.create(ApiServcice.class);
        Observable<Bean> data = apiServcice.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {



                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        info_url = bean.getResult().getInfo_url();
                        Log.i(TAG, "onNext: "+info_url);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        mHomeTimeBt = (Button) findViewById(R.id.bt_home_time);
        mHomeTimeBt.setOnClickListener(this);
        mHomeTopImg = (ImageView) findViewById(R.id.img_home_top);
        mHomeBottomImg = (ImageView) findViewById(R.id.img_home_bottom);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time > 0) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHomeTimeBt.setText("跳过" + time);
                                if (time == 3) {
                                    getScreenWH();
                                    mHomeTopImg.setMaxHeight(heightPixels - 100);
                                    mHomeBottomImg.setVisibility(View.VISIBLE);
                                    mHomeTimeBt.setVisibility(View.VISIBLE);
                                    Glide.with(MainActivity.this).load(info_url).into(mHomeTopImg);
                                    if (time == 0) {
                                        Intent intent = new Intent(MainActivity.this, AdvertisingActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
                        time--;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    //获取屏幕宽和高
    private void getScreenWH() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        heightPixels = metrics.heightPixels;
        widthPixels = metrics.widthPixels;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_home_time:
                Intent intent = new Intent(MainActivity.this, AdvertisingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
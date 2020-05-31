package com.example.applicationzlschoold;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.applicationzlschoold.fragment.ClassFragment;
import com.example.applicationzlschoold.fragment.DataFragment;
import com.example.applicationzlschoold.fragment.HomeFragment;
import com.example.applicationzlschoold.fragment.MyFragment;
import com.example.applicationzlschoold.fragment.VipFragment;
import com.google.android.material.tabs.TabLayout;

//广告页
public class AdvertisingActivity extends AppCompatActivity {

    private FrameLayout mLayoutFrame;
    private TabLayout mLayoutTab;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private VipFragment vipFragment;
    private DataFragment dataFragment;
    private MyFragment myFragment;
    private FragmentManager manager;
    private LinearLayout mLayoutHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        initView();
    }

    private void initView() {
        mLayoutHeader = (LinearLayout) findViewById(R.id.header_layout);
        mLayoutFrame = (FrameLayout) findViewById(R.id.frame_layout);
        mLayoutTab = (TabLayout) findViewById(R.id.tab_layout);
        homeFragment = new HomeFragment();
        classFragment = new ClassFragment();
        vipFragment = new VipFragment();
        dataFragment = new DataFragment();
        myFragment = new MyFragment();
        mLayoutTab.addTab(mLayoutTab.newTab().setText("主页").setIcon(R.drawable.home));
        mLayoutTab.addTab(mLayoutTab.newTab().setText("课程").setIcon(R.drawable.classs));
        mLayoutTab.addTab(mLayoutTab.newTab().setText("VIP").setIcon(R.drawable.vip));
        mLayoutTab.addTab(mLayoutTab.newTab().setText("资料").setIcon(R.drawable.data));
        mLayoutTab.addTab(mLayoutTab.newTab().setText("我的").setIcon(R.drawable.my));
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.frame_layout, homeFragment)
                .add(R.id.frame_layout, classFragment)
                .add(R.id.frame_layout, vipFragment)
                .add(R.id.frame_layout, dataFragment)
                .add(R.id.frame_layout, myFragment)
                .hide(classFragment)
                .hide(vipFragment)
                .hide(dataFragment)
                .hide(myFragment)
                .commit()
        ;
        mLayoutTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mLayoutHeader.setVisibility(View.VISIBLE);
                    manager.beginTransaction().show(homeFragment)
                            .hide(classFragment)
                            .hide(vipFragment)
                            .hide(dataFragment)
                            .hide(myFragment)
                            .commit();
                }else if (tab.getPosition() ==1){
                    mLayoutHeader.setVisibility(View.VISIBLE);
                    manager.beginTransaction().show(classFragment)
                            .hide(homeFragment)
                            .hide(vipFragment)
                            .hide(dataFragment)
                            .hide(myFragment)
                            .commit();
                }else if (tab.getPosition() ==2){
                    mLayoutHeader.setVisibility(View.VISIBLE);
                    manager.beginTransaction().show(vipFragment)
                            .hide(homeFragment)
                            .hide(classFragment)
                            .hide(dataFragment)
                            .hide(myFragment)
                            .commit();
                }else if (tab.getPosition() ==3){
                    mLayoutHeader.setVisibility(View.VISIBLE);
                    manager.beginTransaction().show(dataFragment)
                            .hide(homeFragment)
                            .hide(classFragment)
                            .hide(vipFragment)
                            .hide(myFragment)
                            .commit();
                }else if (tab.getPosition() ==4){
                    mLayoutHeader.setVisibility(View.GONE);
                    manager.beginTransaction().show(myFragment)
                            .hide(homeFragment)
                            .hide(classFragment)
                            .hide(vipFragment)
                            .hide(dataFragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
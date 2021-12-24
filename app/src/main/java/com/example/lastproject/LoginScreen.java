package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LoginScreen extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    FloatingActionButton google;
    LoginAdapter adapter;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);

        tabLayout.addTab(tabLayout.newTab().setText("Log In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new LoginAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        google.setTranslationY(300);
        google.setAlpha(v);

        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();



    }


    }

package com.example.lastproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class Teacher_Main_Screen extends AppCompatActivity {


    private DrawerLayout drawer;
    TabLayout tabLayout;
    ViewPager2 pager2;
    main_teacher_tab_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.teacher_drawer_layout);

        tabLayout = findViewById(R.id.tab_layout3);
        pager2 = findViewById(R.id.view_pager3);

        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITIES"));
        tabLayout.addTab(tabLayout.newTab().setText("MY GROUP"));
        tabLayout.addTab(tabLayout.newTab().setText("REQUESTS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new main_teacher_tab_adapter(fm, getLifecycle());
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
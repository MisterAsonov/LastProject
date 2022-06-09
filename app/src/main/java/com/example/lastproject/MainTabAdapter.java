package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainTabAdapter extends FragmentStateAdapter {

    public MainTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    /**
     * מחלקה מחזירה את הפרגמת על ישי לחיצה על TabView
     */
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ActivitiesFragment();

        }
        return new ReportFragment();
    }
    /**
     * מחלקה שמחזירה מספר פרגמנתים בTabView
     * @return
     */
    @Override
    public int getItemCount() {
        return 2;
    }
}

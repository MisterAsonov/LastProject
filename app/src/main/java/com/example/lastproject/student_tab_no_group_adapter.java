package com.example.lastproject;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class student_tab_no_group_adapter extends FragmentStateAdapter {

    public student_tab_no_group_adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new student_not_in_class_fragment();
            }
            return new no_data();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}



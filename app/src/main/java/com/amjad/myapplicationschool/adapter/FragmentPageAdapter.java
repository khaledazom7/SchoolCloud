package com.amjad.myapplicationschool.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class FragmentPageAdapter extends FragmentStateAdapter {


    private final String[] titles;
    private final Fragment[] fragments;

    public FragmentPageAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles, Fragment[] fragments) {
        super(fragmentActivity);
        this.titles = titles;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = fragments[position];
        return fragment;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}

package com.baking.www.baking.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Dell on 28/05/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private int stepPosition;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, int stepPosition) {
        super(fm);
        this.fragments = fragments;
        this.stepPosition = stepPosition;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}

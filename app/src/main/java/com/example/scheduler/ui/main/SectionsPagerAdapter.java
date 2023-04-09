package com.example.scheduler.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.scheduler.ListOfRows;
import com.example.scheduler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2};
    private final Context mContext;

    private final List<Fragment> fragmentList = new ArrayList<>();
    // populating the pager with row fragments goes here

    /**
     * PagerAdapter constructor, u can add the pages statically
     * from this PagerAdapter
     * @param context  Context
     * @param fm  FragmentManager
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        // Adding fragments to the list of fragments shown in the viewPager
        // the first is a list of "carts" and the latter is an empty page
        // ToDo: assign a unique identifier (UUID) for each fragment and \
        //  then pull it from each fragment and create a new Cycle object
        fragmentList.add(ListOfRows.newInstance("hello","world"));
        fragmentList.add(ListOfRows.newInstance("hello","world"));
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return mContext.getResources().getString(TAB_TITLES[position]);
        return "Page"+getCount();
    }

    @Override
    public int getCount() {
        // Show total pages numbers.
        return fragmentList.size();
    }
}
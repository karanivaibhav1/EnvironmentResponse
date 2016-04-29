package com.test.vaibhav.environmentresponse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter_ViewAllIssues extends FragmentPagerAdapter {
    int count;
    ArrayList<String> types = new ArrayList<String>();
    public Adapter_ViewAllIssues(FragmentManager fm, int size)
    {
        super(fm);
        types.add("Type_Air");
        types.add("Type_Trash");
        types.add("Type_Water");
        types.add("Type_Soil");
        types.add("Type_Plant");
        types.add("Type_Other");
        count = size;

    }
    @Override
    public Fragment getItem(int position)
    {
        return Fragment_IssuesList.newInstance(types.get(position));
    }
    @Override
    public int getCount()
    {
        return count;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        Locale l= Locale.getDefault();
        String type = types.get(position);
        return type.toUpperCase(l);
    }
}

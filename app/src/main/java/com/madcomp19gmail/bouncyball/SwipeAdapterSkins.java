package com.madcomp19gmail.bouncyball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapterSkins extends FragmentStatePagerAdapter {

    public SwipeAdapterSkins(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment frag = new SkinFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", i + 1);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return 29;
    }
}

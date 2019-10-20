package de.headlinetwo.exit.menu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by headlinetwo on 01.12.17.
 */

public class MainMenuFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] menuFragments;

    public MainMenuFragmentPagerAdapter(FragmentManager fragmentManager, Fragment... menuFragments) {
        super(fragmentManager);

        this.menuFragments = menuFragments;
    }

    @Override
    public Fragment getItem(int index) {
        return menuFragments[index];
    }

    @Override
    public int getCount() {
        return menuFragments.length;
    }
}
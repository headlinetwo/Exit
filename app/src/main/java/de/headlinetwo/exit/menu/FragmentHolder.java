package de.headlinetwo.exit.menu;

import androidx.fragment.app.Fragment;

import de.headlinetwo.exit.menu.levelselection.MainMenuLevelSelectionPage;

/**
 * Created by headlinetwo on 16.01.18.
 */

public class FragmentHolder {

    public static final int PERSONAL_ADVERTISEMENT_PAGE_INDEX = 0;
    public static final int LANDING_PAGE_INDEX = 1;
    public static final int LEVEL_SELECTION_PAGE_INDEX = 2;

    private MainMenuAboutPage personalAdvertisementPage;
    private MainMenuLandingPage landingPage;
    private MainMenuLevelSelectionPage levelSelectionPage;

    public FragmentHolder() {
        this.personalAdvertisementPage = new MainMenuAboutPage();
        this.landingPage = new MainMenuLandingPage();
        this.levelSelectionPage = new MainMenuLevelSelectionPage();
    }

    public Fragment[] getAllFragments() { //return all fragments in right order
        return new Fragment[] { personalAdvertisementPage, landingPage, levelSelectionPage };
    }

    public MainMenuLandingPage getLandingPage() {
        return landingPage;
    }

    public MainMenuLevelSelectionPage getLevelSelectionPage() {
        return levelSelectionPage;
    }
}
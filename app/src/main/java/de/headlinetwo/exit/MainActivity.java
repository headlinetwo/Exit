package de.headlinetwo.exit;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;

import de.headlinetwo.exit.advertisement.AdvertisementManger;
import de.headlinetwo.exit.config.ConfigManager;
import de.headlinetwo.exit.config.level.BasicLevelConfig;
import de.headlinetwo.exit.config.playerdata.PlayerDataManager;
import de.headlinetwo.exit.menu.FragmentHolder;
import de.headlinetwo.exit.menu.MainMenuFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;

    private MainMenuFragmentPagerAdapter pagerAdapter;
    public static ViewPager viewPager;
    private FragmentHolder fragmentHolder;

    public static PlayerDataManager playerDataManager;
    public static BasicLevelConfig[] basicLevelConfigs;

    public static AdvertisementManger advertisementManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        MobileAds.initialize(this, getResources().getString(R.string.google_admob_app_id));

        advertisementManger = new AdvertisementManger(this);

        //remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_activity_layout);

        playerDataManager = new PlayerDataManager(this); //load the currently saved progress of the player
        basicLevelConfigs = ConfigManager.loadBasicLevelConfigs(this);
        fragmentHolder = new FragmentHolder();
        pagerAdapter = new MainMenuFragmentPagerAdapter(getSupportFragmentManager(), fragmentHolder.getAllFragments());

        viewPager = findViewById(R.id.main_menu_layout_view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(FragmentHolder.LANDING_PAGE_INDEX); //default page is the middle landing fragment
    }

    public FragmentHolder getFragmentHolder() {
        return fragmentHolder;
    }
}
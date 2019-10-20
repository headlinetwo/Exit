package de.headlinetwo.exit.menu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;

/**
 * Created by headlinetwo on 01.12.17.
 */

public class MainMenuLandingPage extends Fragment {

    private static String PROGRESS_BAR_INFO_TEXT = "";

    private TextView progressBarInfoTextView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_landing_page, container, false);

        PROGRESS_BAR_INFO_TEXT = getResources().getString(R.string.main_menu_landing_page_total_progress_info_text);

        progressBarInfoTextView = rootView.findViewById(R.id.main_menu_landing_page_progress_bar_info_text_view);
        progressBar = rootView.findViewById(R.id.level_progress);
        progressBar.setMax(100);
        updateProgressBar(MainActivity.playerDataManager.getNumberOfPerfectlyFinishedLevels());

        Button aboutButton = rootView.findViewById(R.id.main_menu_landing_page_about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(FragmentHolder.PERSONAL_ADVERTISEMENT_PAGE_INDEX, true);
            }
        });

        Button consentButton = rootView.findViewById(R.id.main_menu_consent_button);
        consentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.advertisementManger.getConsentManager().openConsentRequestWindow();
            }
        });

        if (!MainActivity.advertisementManger.isInsideEEA()) {
            consentButton.setVisibility(View.INVISIBLE);
            consentButton.setClickable(false);
        }

        Button playButton = rootView.findViewById(R.id.main_menu_landing_page_play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(FragmentHolder.LEVEL_SELECTION_PAGE_INDEX, true);
            }
        });

        return rootView;
    }

    /**
     * Updates the progress bar that displays the current progress of the user on the main page as well
     * as the associated text reading the amount of solved levels and used swipes.
     *
     * @param numberOfPerfectlySolvedLevels the number of levels solved with the least amount of swipes possible.
     */
    public void updateProgressBar(int numberOfPerfectlySolvedLevels) {
        int totalSwipeCount = 0;
        int lowestPossibleSwipeCount = 0;

        for (int levelIndex = 0; levelIndex < MainActivity.basicLevelConfigs.length; levelIndex++) {
            lowestPossibleSwipeCount += MainActivity.basicLevelConfigs[levelIndex].getShortestCombination();
            if (MainActivity.playerDataManager.hasLevelCompleted(levelIndex)) {
                totalSwipeCount += MainActivity.playerDataManager.getNumberOfUsedSwipes(levelIndex);
            }
        }

        progressBarInfoTextView.setText(PROGRESS_BAR_INFO_TEXT
                .replace("%solved%", numberOfPerfectlySolvedLevels + "")
                .replace("%total%", MainActivity.basicLevelConfigs.length + "")
                .replace("%swipe_count%", totalSwipeCount + "")
                .replace("%min_swipe_count%", lowestPossibleSwipeCount + ""));

        progressBar.setProgress(
                (int) (((double) numberOfPerfectlySolvedLevels / MainActivity.basicLevelConfigs.length) * 100));
    }
}
package de.headlinetwo.exit.game.menu;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.advertisement.AdvertisementRewardCallback;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.LevelActivity;
import de.headlinetwo.exit.menu.AbstractAlertDialogMenu;
import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 04.12.17.
 */

public class LevelStuckMenu extends AbstractAlertDialogMenu<LevelActivity> {

    private boolean skipLevel;

    public LevelStuckMenu(LevelActivity levelActivity, int levelIndex) {
        super(levelActivity);

        skipLevel = !MainActivity.playerDataManager.hasLevelUnlocked(levelIndex + 1);
        if (levelIndex == MainActivity.basicLevelConfigs.length) skipLevel = false;

    }

    @Override
    public void createMenu(AlertDialog.Builder builder) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(skipLevel ? R.layout.level_stuck_menu_skip_level_layout : R.layout.level_stuck_menu_layout, null);

        builder.setView(dialogView);

        TextView description = dialogView.findViewById(skipLevel ? R.id.level_stuck_skip_level_menu_description_text_view : R.id.level_stuck_menu_description_text_view);
        String[] stringArray = getActivity().getResources().getStringArray(R.array.level_stuck_menu_description);
        description.setText(stringArray[GameConstants.RANDOM.nextInt(stringArray.length)]);

        ImageButton mainMenuButton = dialogView.findViewById(skipLevel ? R.id.level_stuck_skip_level_menu_main_menu_button : R.id.level_stuck_menu_main_menu_button);
        ImageButton retryButton = dialogView.findViewById(skipLevel ? R.id.level_stuck_skip_level_menu_retry_level_button : R.id.level_stuck_menu_retry_level_button);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        getActivity().finish();
                    }
                });
                closeMenu();
            }
        });

        if (skipLevel) {
            ImageButton skipLevelButton = dialogView.findViewById(R.id.level_stuck_skip_level_menu_skip_level_button);
            skipLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnExitAnimationFinish(new Callback() {
                        @Override
                        public void onFinish() {
                            getActivity().finish();

                            Toast.makeText(MainActivity.instance, MainActivity.instance.getResources().getString(R.string.loading_advertisement), Toast.LENGTH_SHORT).show();

                            MainActivity.advertisementManger.showAdvertisementToUnlockNextLevel(new AdvertisementRewardCallback() {
                                @Override
                                public void onReward() {
                                    MainActivity.playerDataManager.unlockNextCurrentlyLockedLevel();
                                    MainActivity.instance.getFragmentHolder().getLevelSelectionPage().getAdapter().notifyDataSetChanged();

                                    Toast.makeText(MainActivity.instance, MainActivity.instance.getResources().getString(R.string.advertisement_unlocked_level_successfully), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    closeMenu();
                }
            });
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        getActivity().getGameHandler().initialize(MainActivity.basicLevelConfigs[getActivity().getGameHandler().getCurrentLevel().getLevelIndex()].getCompleteLevelConfig(getActivity()));
                    }
                });
                closeMenu();
            }
        });

        addButtonToDisable(mainMenuButton, retryButton);
    }
}
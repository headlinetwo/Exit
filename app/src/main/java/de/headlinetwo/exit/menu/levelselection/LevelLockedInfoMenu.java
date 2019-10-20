package de.headlinetwo.exit.menu.levelselection;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.advertisement.AdvertisementRewardCallback;
import de.headlinetwo.exit.game.LevelActivity;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.menu.AbstractAlertDialogMenu;
import de.headlinetwo.exit.menu.CancelableAlertDialogMenu;

/**
 * Created by headlinetwo on 27.12.17.
 */

public class LevelLockedInfoMenu extends AbstractAlertDialogMenu<MainActivity> implements CancelableAlertDialogMenu {

    private int levelIndex;

    public LevelLockedInfoMenu(MainActivity activity, int levelIndex) {
        super(activity);

        this.levelIndex = levelIndex;
    }

    @Override
    public void createMenu(AlertDialog.Builder builder) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.level_locked_info_menu_layout, null);
        builder.setView(dialogView);

        ImageButton previousLevel = dialogView.findViewById(R.id.level_locked_info_menu_previous_level_button);
        ImageButton watchAd = dialogView.findViewById(R.id.level_locked_info_menu_watch_ad_button);

        previousLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitAnimationFinish(new  Callback() {
                    @Override
                    public void onFinish() {
                        for (int levelIndexToCheck = levelIndex; levelIndexToCheck >= 0; levelIndexToCheck--) {
                            if (MainActivity.playerDataManager.hasLevelUnlocked(levelIndexToCheck)) {
                                Intent intent = new Intent(MainActivity.instance, LevelActivity.class);
                                intent.putExtra("levelIndex", levelIndexToCheck);
                                MainActivity.instance.startActivity(intent);
                                break;
                            }
                        }
                    }
                });

                closeMenu();
            }
        });

        watchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
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

        addButtonToDisable(previousLevel, watchAd);
    }

    @Override
    public void onCancelMenu() {
        closeMenu();
    }
}
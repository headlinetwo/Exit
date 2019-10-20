package de.headlinetwo.exit.game.menu;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.LevelActivity;
import de.headlinetwo.exit.game.logic.level.Level;
import de.headlinetwo.exit.game.logic.level.LevelState;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.menu.AbstractAlertDialogMenu;
import de.headlinetwo.exit.menu.CancelableAlertDialogMenu;
import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 09.12.17.
 */

public class LevelPauseMenu extends AbstractAlertDialogMenu<LevelActivity> implements CancelableAlertDialogMenu {

    private Level level;

    public LevelPauseMenu(LevelActivity levelActivity, Level level) {
        super(levelActivity);

        this.level = level;
    }

    @Override
    public void createMenu(AlertDialog.Builder builder) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.level_pause_menu_layout, null);
        builder.setView(dialogView);

        TextView description = dialogView.findViewById(R.id.level_pause_menu_description_text_view);
        String[] stringArray = getActivity().getResources().getStringArray(R.array.level_pause_menu_description);
        description.setText(stringArray[GameConstants.RANDOM.nextInt(stringArray.length)]);

        ImageButton mainMenuButton = dialogView.findViewById(R.id.level_pause_menu_main_menu_button);
        ImageButton retryButton = dialogView.findViewById(R.id.level_pause_menu_retry_level_button);
        ImageButton continueButton = dialogView.findViewById(R.id.level_pause_menu_continue_level_button);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        getActivity().finish();
                    }
                });
                closeMenu();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        getActivity().getGameHandler().initialize(MainActivity.basicLevelConfigs[getActivity().getGameHandler().getCurrentLevel().getLevelIndex()].getCompleteLevelConfig(getActivity()));
                    }
                });
                closeMenu();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        level.setCurrentState(LevelState.RUNNING);
                    }
                });
                closeMenu();
            }
        });

        addButtonToDisable(mainMenuButton, retryButton, continueButton);
    }

    @Override
    public void onCancelMenu() {
        setOnExitAnimationFinish(new Callback() {
            @Override
            public void onFinish() {
                level.setCurrentState(LevelState.RUNNING);
            }
        });
        closeMenu();
    }
}
package de.headlinetwo.exit.game.menu;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.util.LevelProgressUtil;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.LevelActivity;
import de.headlinetwo.exit.menu.AbstractAlertDialogMenu;
import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 01.12.17.
 */

public class LevelWonMenu extends AbstractAlertDialogMenu<LevelActivity> {

    private static final int PROGRESS_BAR_MAX_VALUE = Integer.MAX_VALUE; //big value so the animation is smoother

    private int numberOfSwipes;
    private int bestPossibleNumberOfSwipes;
    private int oldRecord; //-1 for no old record -> first time beating this level

    private float progress;
    private ProgressBar progressBar;

    public LevelWonMenu(LevelActivity levelActivity, int numberOfSwipes, int bestPossibleNumberOfSwipes, int oldRecord) {
        super(levelActivity);

        this.numberOfSwipes = numberOfSwipes;
        this.bestPossibleNumberOfSwipes = bestPossibleNumberOfSwipes;
        this.oldRecord = oldRecord;

        progress = LevelProgressUtil.getProgress(numberOfSwipes, bestPossibleNumberOfSwipes);

        setOnEnterAnimationFinish(new Callback() {
            @Override
            public void onFinish() {
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) (progress * PROGRESS_BAR_MAX_VALUE));
                animation.setDuration((long) (3000));
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
            }
        });
    }

    @Override
    public void createMenu(AlertDialog.Builder builder) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.level_won_menu_layout, null);
        builder.setView(dialogView);

        TextView description = dialogView.findViewById(R.id.level_won_menu_description_text_view);
        String[] stringArray = getActivity().getResources().getStringArray(R.array.level_won_description);
        description.setText(stringArray[GameConstants.RANDOM.nextInt(stringArray.length)] + " ");

        if (numberOfSwipes <= bestPossibleNumberOfSwipes) description.setText(description.getText() + getActivity().getResources().getString(R.string.level_won_best_combination));
        else if (oldRecord != -1) {
            if (numberOfSwipes > oldRecord) description.setText(getActivity().getResources().getString(R.string.level_won_has_better_record_already));
            else description.setText(description.getText() + getActivity().getResources().getString(R.string.level_won_beat_previous_record).replaceAll("%NEW_RECORD_DIFF%", oldRecord - numberOfSwipes + ""));
        }
        else description.setText(description.getText() + getActivity().getResources().getString(R.string.level_won_first_time));

        ImageButton mainMenuButton = dialogView.findViewById(R.id.level_won_menu_main_menu_button);
        ImageButton playAgainButton = dialogView.findViewById(R.id.level_won_menu_replay_level_button);
        ImageButton nextLevelButton = dialogView.findViewById(R.id.level_won_menu_next_level_button);
        progressBar = dialogView.findViewById(R.id.level_won_menu_progress_bar);
        progressBar.setMax(PROGRESS_BAR_MAX_VALUE);
        progressBar.setProgress(0);

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

        playAgainButton.setOnClickListener(new View.OnClickListener() {
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

        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnExitAnimationFinish(new Callback() {
                    @Override
                    public void onFinish() {
                        int currentLevelIndex = getActivity().getGameHandler().getCurrentLevel().getLevelIndex();

                        int levelCount = MainActivity.basicLevelConfigs.length;

                        if (currentLevelIndex >= (levelCount - 1)) {
                            getActivity().finish();
                        }
                        else getActivity().getGameHandler().initialize(MainActivity.basicLevelConfigs[currentLevelIndex + 1].getCompleteLevelConfig(getActivity()));
                    }
                });
                closeMenu();
            }
        });

        addButtonToDisable(mainMenuButton, nextLevelButton);
    }
}
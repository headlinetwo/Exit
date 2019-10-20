package de.headlinetwo.exit.menu.levelselection;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.config.level.BasicLevelConfig;
import de.headlinetwo.exit.config.playerdata.PlayerDataManager;
import de.headlinetwo.exit.util.LevelProgressUtil;

/**
 * Created by headlinetwo on 03.12.17.
 */

public class LevelSelectionAdapter extends RecyclerView.Adapter<LevelSelectionViewHolder> {

    private static final int PROGRESS_BAR_MAX_VALUE = 100;

    public static final int VIEW_TYPE_LEVEL_LOCKED = 0;
    public static final int VIEW_TYPE_LEVEL_UNLOCKED = 1;

    private MainMenuLevelSelectionPage levelSelectionFragment;
    private BasicLevelConfig[] levelData;
    private PlayerDataManager playerDataManager;

    public LevelSelectionAdapter(MainMenuLevelSelectionPage levelSelectionFragment, BasicLevelConfig[] levelData, PlayerDataManager playerDataManager) {
        this.levelSelectionFragment = levelSelectionFragment;
        this.levelData = levelData;
        this.playerDataManager = playerDataManager;
    }

    @Override
    public LevelSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        if (viewType == VIEW_TYPE_LEVEL_LOCKED) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_selection_level_card_locked, parent, false);
        else if (viewType == VIEW_TYPE_LEVEL_UNLOCKED) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_selection_level_card_unlocked, parent, false);

        return new LevelSelectionViewHolder(itemView, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (playerDataManager.hasLevelUnlocked(position)) return VIEW_TYPE_LEVEL_UNLOCKED;
        else return VIEW_TYPE_LEVEL_LOCKED;
    }

    @Override
    public void onBindViewHolder(LevelSelectionViewHolder viewHolder, final int position) {
        viewHolder.getLevelIndexTextView().setText((levelData[position].getLevelIndex() + 1) + "");

        if  (viewHolder.getViewType() == VIEW_TYPE_LEVEL_UNLOCKED) {
            int usedSwipes = MainActivity.playerDataManager.getNumberOfUsedSwipes(position);
            if (usedSwipes > 0) viewHolder.getProgressBar().setProgress((int) (PROGRESS_BAR_MAX_VALUE * LevelProgressUtil.getProgress(usedSwipes, MainActivity.basicLevelConfigs[position].getShortestCombination())));
            else viewHolder.getProgressBar().setProgress(0);
        }
    }

    @Override
    public int getItemCount() {
        return levelData.length;
    }
}
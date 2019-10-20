package de.headlinetwo.exit.config.level;

import android.content.Context;

import java.io.IOException;

import de.headlinetwo.exit.util.FileUtil;

/**
 * Created by headlinetwo on 03.12.17.
 */

public class BasicLevelConfig {

    private static final String LEVEL_DIRECTORY = "levels";

    private int levelIndex; //unique index for each level. starting at 0.
    private int shortestCombination = -1; //the minimum number of swipes required to solve this level

    /**
     * Loads the full level data from the associated level data file
     *
     * @param context the main context of this android app
     * @return the full level config of this level
     */
    public LevelConfig getCompleteLevelConfig(Context context) {
        try {
            LevelConfig config = LevelConfigReader.loadLevelConfigInfo(FileUtil.convertStreamToString(context.getAssets().open(LEVEL_DIRECTORY + "/level-" + levelIndex + ".json")));
            config.setLevelIndex(levelIndex);
            return config;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the unique index associated with this level
     */
    public int getLevelIndex() {
        return levelIndex;
    }

    /**
     * @return the minimum number of swipes required to solve this level
     */
    public int getShortestCombination() {
        return shortestCombination;
    }

    /**
     * @param levelID the unique level id of this level
     */
    public void setLevelIndex(int levelID) {
        this.levelIndex = levelID;
    }

    /**
     * @param shortestCombination the minimum number of swipes required to solve this level
     */
    public void setShortestCombination(int shortestCombination) {
        this.shortestCombination = shortestCombination;
    }
}
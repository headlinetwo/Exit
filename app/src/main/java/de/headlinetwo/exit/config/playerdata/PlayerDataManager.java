package de.headlinetwo.exit.config.playerdata;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.util.FileUtil;

/**
 * Created by headlinetwo on 04.12.17.
 */

public class PlayerDataManager {

    private static final int LEVEL_NOT_SOLVED_SWIPES = -1;

    private static final String PLAYER_DATA_FILE_NAME = "playerdata.json";

    private File dataFile;
    private PlayerDataHolder playerDataHolder;

    public PlayerDataManager(Context context) {
        dataFile = new File(context.getFilesDir(), PLAYER_DATA_FILE_NAME);

        if (!dataFile.exists()) try {
            dataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerDataHolder = new PlayerDataHolder(FileUtil.readFile(context, PLAYER_DATA_FILE_NAME));
    }

    /**
     * adds an level as unlocked to the user data storage.
     * This method call will not save the data to the user file. call {@link #saveFile()} to store
     * the data permanently
     *
     * @param levelIndex the index of the level to unlock
     */
    public void addUnlockedLevel(int levelIndex) {
        playerDataHolder.getUnlockedLevels().add(levelIndex);
    }

    /**
     * Unlocks the the next level in order of ascending level indices that has not been unlocked yet
     */
    public void unlockNextCurrentlyLockedLevel() {
        int nextLevelToBeUnlocked = -1;
        for (int levelIndex = 0; levelIndex < MainActivity.basicLevelConfigs.length; levelIndex++) {
            if (!hasLevelUnlocked(levelIndex)) {
                nextLevelToBeUnlocked = levelIndex;
                break;
            }
        }

        if (nextLevelToBeUnlocked == -1) return;

        addUnlockedLevel(nextLevelToBeUnlocked);

        saveFile();
        MainActivity.instance.getFragmentHolder().getLevelSelectionPage().getAdapter().notifyDataSetChanged();
    }

    /**
     * Adds a level to the completed levels or updates the record if a previous record of the
     * given level exits with more swipes.
     * This method call will not save the data to the user file. call {@link #saveFile()} to store
     * the data permanently
     *
     * @param levelIndex the index of the completed levels
     * @param numberOfSwipes
     * @return new record
     */
    public boolean addCompletedLevel(int levelIndex, int numberOfSwipes) {
        if (playerDataHolder.getCompletedLevels().containsKey(levelIndex)) {
            if (numberOfSwipes >= playerDataHolder.getCompletedLevels().get(levelIndex)) return false; //only update if new record
        }

        playerDataHolder.getCompletedLevels().put(levelIndex, numberOfSwipes);
        return true;
    }

    /**
     * Writes the current (in memory stored) data to the player data storage file
     */
    public void saveFile() {
        FileUtil.writeToFile(MainActivity.instance, PLAYER_DATA_FILE_NAME, playerDataHolder.toJSONObject().toString());
    }

    /**
     *
     * @param levelIndex the index of the level to check
     * @return whether or not the given level has been unlocked by the user
     */
    public boolean hasLevelUnlocked(int levelIndex) {
        if (levelIndex <= 0) return true; //first level is unlocked by default
        return playerDataHolder.getUnlockedLevels().contains(levelIndex);
    }

    /**
     *
     * @param levelIndex the index of the level to check
     * @return whether or not the user has solved this level already
     */
    public boolean hasLevelCompleted(int levelIndex) {
        return playerDataHolder.getCompletedLevels().containsKey(levelIndex);
    }

    /**
     *
     * @return the number of levels currently solved with the least amount of swipes possible
     */
    public int getNumberOfPerfectlyFinishedLevels() {
        int count = 0;

        for (int levelIndex : playerDataHolder.getCompletedLevels().keySet()) {
            if (levelIndex >= MainActivity.basicLevelConfigs.length) continue;
            if (playerDataHolder.getCompletedLevels().get(levelIndex) <= MainActivity.basicLevelConfigs[levelIndex].getShortestCombination()) {
                count++;
            }
        }

        return count;
    }

    /**
     *
     * @param levelIndex
     * @return the amount of swipes previously used to solve the given level.
     * returns {@link #LEVEL_NOT_SOLVED_SWIPES} if the level has never been solved by the user
     */
    public int getNumberOfUsedSwipes(int levelIndex) {
        if (playerDataHolder.getCompletedLevels().containsKey(levelIndex)) return playerDataHolder.getCompletedLevels().get(levelIndex);
        else return LEVEL_NOT_SOLVED_SWIPES;
    }

    /**
     * The formatted information string displayed at the top right corner reading the previous
     * swipe record
     *
     * @param levelIndex the index of the level to get the record information string from
     * @return the formatted string that can be displayed within the level
     */
    public String getCurrentRecordInfoString(int levelIndex) {
        int numberOfUsedSwipes = getNumberOfUsedSwipes(levelIndex);
        return numberOfUsedSwipes <= 0 ? MainActivity.instance.getResources().getString(R.string.no_record) : numberOfUsedSwipes + "";
    }
}
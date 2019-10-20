package de.headlinetwo.exit.config.playerdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by headlinetwo on 08.12.17.
 */

public class PlayerDataHolder {

    private static final String UNLOCKED_LEVELS_IDENTIFIER = "unlockedLevels";
    private static final String COMPLETED_LEVELS_IDENTIFIER = "completedLevels";
    private static final String LEVEL_INDEX_IDENTIFIER = "levelIndex";
    private static final String LEVEL_NUMBER_OF_SWIPES_USED_IDENTIFIER = "swipes";

    private HashSet<Integer> unlockedLevels = new HashSet<>(); //contains all unlocked level indices
    private HashMap<Integer, Integer> completedLevels = new HashMap<>(); //contains all completed levels, with (level index, number of swipes used) data pairs

    /**
     * Stores all the statistics as well as unlocked levels and progress about the user playing this game
     *
     * @param playerData the json data string to read the data from
     */
    public PlayerDataHolder(String playerData) {
        if (playerData.isEmpty()) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(playerData);

            JSONArray unlockedLevelsArray = jsonObject.getJSONArray(UNLOCKED_LEVELS_IDENTIFIER);
            for (int index = 0; index < unlockedLevelsArray.length(); index++) {
                unlockedLevels.add(unlockedLevelsArray.getInt(index));
            }

            JSONArray completedLevelsArray = jsonObject.getJSONArray(COMPLETED_LEVELS_IDENTIFIER);
            for (int index = 0; index < completedLevelsArray.length(); index++) {
                JSONObject completedLevelObject = completedLevelsArray.getJSONObject(index);
                int levelIndex = completedLevelObject.getInt(LEVEL_INDEX_IDENTIFIER);
                int numberOfSwipes = completedLevelObject.getInt(LEVEL_NUMBER_OF_SWIPES_USED_IDENTIFIER);

                completedLevels.put(levelIndex, numberOfSwipes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the player data into a json object
     *
     * @return the json object storing all the important user data
     */
    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();

        JSONArray unlockedLevelsArray = new JSONArray();
        for (int levelIndex : unlockedLevels) unlockedLevelsArray.put(levelIndex);

        JSONArray completedLevelsArray = new JSONArray();
        for (int levelIndex : completedLevels.keySet()) {
            JSONObject completedLevelObject = new JSONObject();
            try {
                completedLevelObject.put(LEVEL_INDEX_IDENTIFIER, levelIndex);
                completedLevelObject.put(LEVEL_NUMBER_OF_SWIPES_USED_IDENTIFIER, completedLevels.get(levelIndex));
                completedLevelsArray.put(completedLevelObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            object.put(UNLOCKED_LEVELS_IDENTIFIER, unlockedLevelsArray);
            object.put(COMPLETED_LEVELS_IDENTIFIER, completedLevelsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * @return a set of all level indices unlocked by the user
     */
    public HashSet<Integer> getUnlockedLevels() {
        return unlockedLevels;
    }

    /**
     * @return a map containing all the indices of the levels completed by the user associated
     * with the number of swipes used to solve the level
     */
    public HashMap<Integer, Integer> getCompletedLevels() {
        return completedLevels;
    }
}
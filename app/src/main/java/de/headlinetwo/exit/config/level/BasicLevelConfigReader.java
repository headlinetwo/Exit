package de.headlinetwo.exit.config.level;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicLevelConfigReader {

    private static final String LEVEL_INDEX_IDENTIFIER = "levelIndex";
    private static final String SHORTEST_COMBINATION_IDENTIFIER = "shortestCombination";

    /**
     * Converts the given json data into a {@link BasicLevelConfig}
     *
     * @param data the data to read
     * @return the converted basic level config
     */
    public static BasicLevelConfig readBasicLevelConfig(JSONObject data) {
        BasicLevelConfig basicLevelConfig = new BasicLevelConfig();

        try {
            basicLevelConfig.setLevelIndex(data.getInt(LEVEL_INDEX_IDENTIFIER));

            if (data.has(SHORTEST_COMBINATION_IDENTIFIER)) {
                basicLevelConfig.setShortestCombination(data.getInt(SHORTEST_COMBINATION_IDENTIFIER));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return basicLevelConfig;
    }
}
package de.headlinetwo.exit.config;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import de.headlinetwo.exit.config.level.BasicLevelConfig;
import de.headlinetwo.exit.config.level.BasicLevelConfigReader;
import de.headlinetwo.exit.config.level.LevelConfigReader;
import de.headlinetwo.exit.util.FileUtil;

/**
 * Created by headlinetwo on 09.12.17.
 */

public class ConfigManager {

    private static final String LEVEL_DIRECTORY = "levels";

    /**
     * Loads some basic information about each level into memory for faster accessibility
     *
     * @param context the android apps main context
     * @return basic information about all available levels
     */
    public static BasicLevelConfig[] loadBasicLevelConfigs(Context context) {
        ArrayList<BasicLevelConfig> levelConfigs = new ArrayList<>();

        try {
            String input = FileUtil.convertStreamToString(context.getAssets().open(LEVEL_DIRECTORY + "/basic_level_configs.json"));
            JSONArray levelsJSON = new JSONArray(input);
            for (int index = 0; index < levelsJSON.length(); index++) {
                levelConfigs.add(BasicLevelConfigReader.readBasicLevelConfig(levelsJSON.getJSONObject(index)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LevelConfigReader.sort(levelConfigs);

        return levelConfigs.toArray(new BasicLevelConfig[levelConfigs.size()]);
    }
}
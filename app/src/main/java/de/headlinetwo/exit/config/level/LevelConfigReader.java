package de.headlinetwo.exit.config.level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.blocks.PortalBlock;
import de.headlinetwo.exit.game.logic.entities.player.PlayerType;
import de.headlinetwo.exit.util.Point;

/**
 * Created by headlinetwo on 28.10.17.
 */
public class LevelConfigReader {

    private static final String LEVEL_GRID_WIDTH_IDENTIFIER = "gridWidth";
    private static final String LEVEL_GRID_HEIGHT_IDENTIFIER = "gridHeight";

    private static final String BLOCKS_IDENTIFIER = "blocks";
    private static final String BLOCK_X_COORDINATE_IDENTIFIER = "gridX";
    private static final String BLOCK_Y_COORDINATE_IDENTIFIER = "gridY";
    private static final String BLOCK_TYPE_IDENTIFIER = "blockType";

    private static final String PLAYERS_IDENTIFIER = "players";
    private static final String PLAYER_TYPE_IDENTIFIER = "type";
    private static final String PLAYER_COORDINATE_IDENTIFIER = "coordinates";
    private static final String PLAYER_BODY_X_COORDINATE_IDENTIFIER = "gridX";
    private static final String PLAYER_BODY_Y_COORDINATE_IDENTIFIER = "gridY";

    private static final String PORTAL_BLOCK_TARGET_X_IDENTIFIER = "targetX";
    private static final String PORTAL_BLOCK_TARGET_Y_IDENTIFIER = "targetY";

    private static final String SHORTEST_COMBINATION_IDENTIFIER = "shortestCombination";

    private static final String HINT_TEXT_IDENTIFIER = "hintTextID";

    /**
     * Converts the given json data string into a {@link LevelConfig}
     *
     * @param savedFileData the data to read
     * @return the converted level config read from the given data
     */
    public static LevelConfig loadLevelConfigInfo(String savedFileData) {
        try {
            JSONObject levelData = new JSONObject(savedFileData);

            LevelConfig levelConfig = new LevelConfig(levelData);

            JSONArray blocksArray = levelData.getJSONArray(BLOCKS_IDENTIFIER); //read all blocks
            JSONArray playersArray = levelData.getJSONArray(PLAYERS_IDENTIFIER); //read all player-snakes

            levelConfig.setGridWidth(levelData.getInt(LEVEL_GRID_WIDTH_IDENTIFIER));
            levelConfig.setGridHeight(levelData.getInt(LEVEL_GRID_HEIGHT_IDENTIFIER));

            if (levelData.has(SHORTEST_COMBINATION_IDENTIFIER)) levelConfig.setShortestCombination(levelData.getInt(SHORTEST_COMBINATION_IDENTIFIER));
            if (levelData.has(HINT_TEXT_IDENTIFIER)) levelConfig.setHintTextIndex(levelData.getInt(HINT_TEXT_IDENTIFIER));

            for (int blockIndex = 0; blockIndex < blocksArray.length(); blockIndex++) {
                JSONObject blockObject = (JSONObject) blocksArray.get(blockIndex);
                BlockType blockType = BlockType.getBlockType(blockObject.getString(BLOCK_TYPE_IDENTIFIER));
                AbstractBlock abstractBlock = blockType.getNewBlockInstance(blockObject.getInt(BLOCK_X_COORDINATE_IDENTIFIER), blockObject.getInt(BLOCK_Y_COORDINATE_IDENTIFIER));

                //read special block types
                if (blockType == BlockType.PORTAL) {
                    PortalBlock portalBlock = (PortalBlock) abstractBlock;
                    if (blockObject.has(PORTAL_BLOCK_TARGET_X_IDENTIFIER) && blockObject.has(PORTAL_BLOCK_TARGET_Y_IDENTIFIER)) {
                        portalBlock.setTargetX(blockObject.getInt(PORTAL_BLOCK_TARGET_X_IDENTIFIER));
                        portalBlock.setTargetY(blockObject.getInt(PORTAL_BLOCK_TARGET_Y_IDENTIFIER));
                    }
                    else System.out.println("FOUND PORTAL BLOCK WITH NO TARGET DESTINATION: levelIndex" + levelConfig.getLevelIndex());
                }

                levelConfig.getBlocks().add(abstractBlock);
            }

            for (int playerIndex = 0; playerIndex < playersArray.length(); playerIndex++) {
                JSONObject playerObject = (JSONObject) playersArray.get(playerIndex);

                PlayerType playerType = PlayerType.ACTIVE;
                if (playerObject.has(PLAYER_TYPE_IDENTIFIER)) playerType = PlayerType.valueOf(playerObject.getString(PLAYER_TYPE_IDENTIFIER));

                JSONArray playerBody = playerObject.getJSONArray(PLAYER_COORDINATE_IDENTIFIER);

                ArrayList<Point> playerCoordinates = new ArrayList<>();

                for (int coordinateIndex = 0; coordinateIndex < playerBody.length(); coordinateIndex++) {
                    JSONObject playerCoordinateObject = (JSONObject) playerBody.get(coordinateIndex);
                    playerCoordinates.add(new Point(playerCoordinateObject.getInt(PLAYER_BODY_X_COORDINATE_IDENTIFIER), playerCoordinateObject.getInt(PLAYER_BODY_Y_COORDINATE_IDENTIFIER)));
                }

                levelConfig.addPlayer(new GamePlayerConfig(playerType, playerCoordinates));
            }

            return levelConfig;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sorts the given data by {@link BasicLevelConfig#getLevelIndex()} index in ascending order
     *
     * @param levelData the level data to sort
     */
    public static void sort(ArrayList<BasicLevelConfig> levelData) {
        Collections.sort(levelData, new Comparator<BasicLevelConfig>() {
            @Override
            public int compare(BasicLevelConfig l1, BasicLevelConfig l2) {
                return l1.getLevelIndex() - l2.getLevelIndex();
            }
        });
    }
}
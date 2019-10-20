package de.headlinetwo.exit.config.level;

import org.json.JSONObject;

import java.util.HashSet;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;

/**
 * Created by headlinetwo on 22.10.17.
 */
public class LevelConfig extends BasicLevelConfig {

    public static final int NO_LEVEL_HINT_TEXT = -1;

    private JSONObject data; //the plain data stored in a json object

    private int gridWidth = 0; //the width of the gird in blocks
    private int gridHeight = 0; //the height of the grid in blocks

    private HashSet<AbstractBlock> blocks = new HashSet<>(); //all blocks for this level, besides the empty spots
    private HashSet<GamePlayerConfig> players = new HashSet<>();

    private int hintTextIndex = NO_LEVEL_HINT_TEXT; //the actual text can be loaded from the string resource file

    /**
     * Stores all the required information about this level
     *
     * @param data the plain data stored in a json file about this level
     */
    public LevelConfig(JSONObject data) {
        this.data = data;
    }

    /**
     * @return the total width of the grid associated with this level, measured in blocks
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * @return the total height of the grid associated with this level, measured in blocks
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * @return all the blocks that need to be placed on this levels grid
     */
    public HashSet<AbstractBlock> getBlocks() {
        return blocks;
    }

    /**
     * @return all the necessary data about the player-snakes that can be controlled in this level
     */
    public HashSet<GamePlayerConfig> getPlayers() {
        return players;
    }

    /**
     * @return a unique id used to access the level hint text, or {@link #NO_LEVEL_HINT_TEXT} if
     * no hint text shall be displayed during this level
     */
    public int getHintTextIndex() {
        return hintTextIndex;
    }

    /**
     * @param gridWidth the total width of the grid associated with this level, measured in blocks
     */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /**
     * @param gridHeight the total height of the grid associated with this level, measured in blocks
     */
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    /**
     * @param gamePlayerConfig the player-snake to add to this level
     */
    public void addPlayer(GamePlayerConfig gamePlayerConfig) {
        players.add(gamePlayerConfig);
    }

    /**
     * @param hintTextIndex the level hint text id
     */
    public void setHintTextIndex(int hintTextIndex) {
        this.hintTextIndex = hintTextIndex;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
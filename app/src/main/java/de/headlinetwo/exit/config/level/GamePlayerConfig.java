package de.headlinetwo.exit.config.level;

import java.util.ArrayList;

import de.headlinetwo.exit.game.logic.entities.player.PlayerType;
import de.headlinetwo.exit.util.Point;

public class GamePlayerConfig {

    private PlayerType playerType;
    private ArrayList<Point> coordinates;

    /**
     * Stores the relevant information about a player-snake at the start of a level
     *
     * @param playerType
     * @param coordinates all coordinates of this player-snakes body
     */
    public GamePlayerConfig(PlayerType playerType, ArrayList<Point> coordinates) {
        this.playerType = playerType;
        this.coordinates = coordinates;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * @return all coordinates of this player-snakes body
     */
    public ArrayList<Point> getCoordinates() {
        return coordinates;
    }
}
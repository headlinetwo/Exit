package de.headlinetwo.exit.game.logic.entities.player;

import de.headlinetwo.exit.game.GameConstants;

public enum PlayerType {

    ACTIVE(GameConstants.PLAYER_HEAD_COLOR, GameConstants.PLAYER_BODY_COLOR),
    BLOCKING_PLAYER(GameConstants.PLAYER_HEAD_COLOR_BLOCKING, GameConstants.PLAYER_BODY_COLOR_BLOCKING);

    private int headColor;
    private int bodyColor;

    PlayerType(int headColor, int bodyColor) {
        this.headColor = headColor;
        this.bodyColor = bodyColor;
    }

    /**
     * @return the color of the player-snakes head block
     */
    public int getHeadColor() {
        return headColor;
    }

    /**
     * @return the color of the player-snakes body
     */
    public int getBodyColor() {
        return bodyColor;
    }
}
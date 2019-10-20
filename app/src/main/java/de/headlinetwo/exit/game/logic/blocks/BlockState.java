package de.headlinetwo.exit.game.logic.blocks;

public enum BlockState {

    DEFAULT,

    /**
     * Indicates that a player-snake is currently occupying this block with one of his body blocks.
     */
    OCCUPIED_BY_PLAYER;
}
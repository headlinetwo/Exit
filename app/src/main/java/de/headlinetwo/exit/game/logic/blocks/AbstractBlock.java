package de.headlinetwo.exit.game.logic.blocks;

import de.headlinetwo.exit.game.gui.GamePanel;

/**
 * Created by headlinetwo on 22.10.17.
 */
public abstract class AbstractBlock {

    private BlockType blockType; //the basic type of this block
    private int gridX; //the x coordinate inside the grid
    private int gridY; //the y coordinate inside the grid
    private BlockState blockState; //the current state of this block

    public AbstractBlock(BlockType blockType, int gridX, int gridY) {
        this.blockType = blockType;
        this.gridX = gridX;
        this.gridY = gridY;
        this.blockState = BlockState.DEFAULT;
    }

    /**
     * Draws this block on the given game panel
     *
     * @param panel the game panel to draw this block on
     * @param gridX the x-coordinate of this block on the game panel
     * @param gridY the y-coordinate of this block in the game panel
     */
    public void drawBlock(GamePanel panel, float gridX, float gridY) {
        panel.fillRect(gridX, gridY, blockType.getPaint());
    }

    /**
     * Draws this block on the given game panel with its current coordinates
     *
     * @param gamePanel the game panel to draw this block on
     */
    public void draw(GamePanel gamePanel) {
        drawBlock(gamePanel, getGridX(), getGridY());
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    /**
     * @return whether or not a player-snake may currently move to this block.
     * (for example false if this block is currently occupied by another player-snake)
     */
    public boolean playerCanMoveTo() {
        return true;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBlock that = (AbstractBlock) o;
        return that.blockType == blockType && that.gridX == gridX && that.gridY == gridY;
    }

    @Override
    public int hashCode() {
        int result = blockType.hashCode();
        result = 31 * result + gridX;
        result = 31 * result + gridY;
        return result;
    }

    @Override
    public String toString() {
        return "AbstractBlock{" +
                "blockType=" + blockType +
                ", gridX=" + gridX +
                ", gridY=" + gridY +
                '}';
    }
}
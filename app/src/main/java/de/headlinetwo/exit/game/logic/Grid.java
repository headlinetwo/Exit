package de.headlinetwo.exit.game.logic;

import java.util.HashSet;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockState;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.blocks.VoidBlock;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.direction.Direction;

/**
 * Created by headlinetwo on 22.10.17.
 */
public class Grid {

    private int width; //width measured in blocks
    private int height; //height measured in blocks

    private AbstractBlock[][] blocks; //contains all blocks on the grid; (0, 0) -> top left corner

    public Grid(int initialWidth, int initialHeight) {
        setSize(initialWidth, initialHeight);
    }

    /**
     * Updates the grids dimension to the given values.
     * Also initializes an empty grid filled with {@link de.headlinetwo.exit.game.logic.blocks.blocks.VoidBlock}.
     *
     * @param newWidth the new width of the grid, measured in blocks
     * @param newHeight the new height of the grid, measured in blocks
     */
    public void setSize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;

        this.blocks = new AbstractBlock[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                blocks[y][x] = new VoidBlock(x, y);
            }
        }
    }

    /**
     * Updates the necessary states of the blocks after a player-snake as finished a move in
     * a certain direction. The block previously occupied by the tail is going to revert to
     * {@link BlockState#DEFAULT} state while the newly occupied block by the head is going
     * to be flagged as {@link BlockState#OCCUPIED_BY_PLAYER}
     *
     * @param newHeadX the new head x-coordinate of a player-snake after it has been moved
     * @param newHeadY the new head y-coordinate of a player-snake after it has been moved
     * @param oldTailX the previous tail x-coordinate of a player-snake before it has been moved
     * @param oldTailY the previous tail y-coordinate of a player-snake before it has been moved
     */
    public void updatePlayerBlockStates(int newHeadX, int newHeadY, int oldTailX, int oldTailY) {
        blocks[newHeadY][newHeadX].setBlockState(BlockState.OCCUPIED_BY_PLAYER);
        blocks[oldTailY][oldTailX].setBlockState(BlockState.DEFAULT);
    }

    /**
     * Updates the state of the block at the given coordinate
     *
     * @param gridX the x-coordinate of the block to update
     * @param gridY the y-coordinate of the bock to update
     * @param blockState the block state to change to
     */
    public void updateBlockState(int gridX, int gridY, BlockState blockState) {
        blocks[gridY][gridX].setBlockState(blockState);
    }

    /**
     * Changes multiple blocks on the grid to the given blocks
     * This overrides the existing blocks that previously occupied the grid at the coordinates
     * of the new blocks
     *
     * @param newBlocks the blocks to place on the grid.
     */
    public void setBlocks(HashSet<AbstractBlock> newBlocks) {
        for (AbstractBlock block : newBlocks) {
            blocks[block.getGridY()][block.getGridX()] = block;
        }
    }

    /**
     * Initializes all the blocks that are currently occupied by player-snakes as
     * {@link BlockState#OCCUPIED_BY_PLAYER}
     *
     * @param players all the player-snakes currently placed on the grid
     */
    public void setPlayers(Player[] players) {
        for (Player player : players) {
            for (Point bodyPart : player.getBody()) {
                blocks[bodyPart.getY()][bodyPart.getX()].setBlockState(BlockState.OCCUPIED_BY_PLAYER);
            }
        }
    }

    /**
     * Changes a block on the grid to the given block
     * This overrides the previously existing block on the grid
     *
     * @param block the new block to place on the grid
     */
    public void setBlock(AbstractBlock block) {
        blocks[block.getGridY()][block.getGridX()] = block;
    }

    /**
     * @return the total width of this grid, measured in blocks
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the total height of this grid, measured in blocks
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return all blocks placed on the grid, including {@link VoidBlock}
     */
    public AbstractBlock[][] getBlocks() {
        return blocks;
    }

    /**
     * @param gridX the x-coordinate of the block
     * @param gridY the y-coordinate of the block
     * @return the block at the given coordinate
     * @throws IndexOutOfBoundsException in case the given coordinates are outside of the grids bounds
     */
    public AbstractBlock getBlock(int gridX, int gridY) throws IndexOutOfBoundsException {
        if (gridX < 0 || gridX >= width) throw new IndexOutOfBoundsException("x is out of range! x=" + gridX + ", range=0-" + (width-1));
        if (gridY < 0 || gridY >= height) throw new IndexOutOfBoundsException("y is out of range! y=" + gridY + ", range=0-" + (height-1));

        return blocks[gridY][gridX];
    }

    /**
     * Calculates whether or not a shadow needs to be drawn around this given block given the
     * direction of the shadow as well as the coordinates of the block and the type.
     * A shadow needs to be drawn if a block is not a void block an if it is placed at the border
     * in the direction of shadow
     *
     * @param gridX the x-coordinate of the block
     * @param gridY the y-coordinate of the block
     * @param shadowDirection the direction of the shadow
     * @return whether or not to draw a shadow around the given block
     */
    public boolean isOutlineRelevant(int gridX, int gridY, Direction shadowDirection) {
        if (getBlock(gridX, gridY) instanceof VoidBlock) return false; //void blocks do not have a shadow

        int targetX = gridX + shadowDirection.getAddX();
        int targetY = gridY + shadowDirection.getAddY();

        if (targetX < 0 || targetX >= width || targetY < 0 || targetY >= height) return true; //block is on the edge

        if (blocks[targetY][targetX] instanceof VoidBlock) return true;

        if (targetX != gridX && blocks[gridY][targetX] instanceof VoidBlock) return true;
        if (targetY != gridY && blocks[targetY][gridX] instanceof VoidBlock) return true;

        return false;
    }

    /**
     * @param blockType the type the blocks to count
     * @return the total number of blocks on the grid with the given block type
     */
    public int getNumberOfBlocks(BlockType blockType) {
        int numberOfBlocks = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (blocks[y][x].getBlockType() == blockType) numberOfBlocks++;
            }
        }

        return numberOfBlocks;
    }
}
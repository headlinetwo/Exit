package de.headlinetwo.exit.game.gui;

import de.headlinetwo.exit.game.GameConstants;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class PanelUtil {

    /**
     * Calculates the size of one square block on the grid depending on the dimensions of the screen
     * so that at least {@link GameConstants#MAX_GRID_BLOCK_WIDTH} blocks may be placed in horizontal
     * and {@link GameConstants#MAX_GRID_BLOCK_HEIGHT} blocks may be placed in vertical direction
     * with a margin on all sides.
     *
     * @param screenWidth the current width of the screen, measured in pixel
     * @param screenHeight the current height of the screen, measured in pixel
     * @return the size of one square block in the screen, measured in pixel
     */
    public static int calculateBlockSize(int screenWidth, int screenHeight) {
        int maxXSize = (int) ((screenWidth - (screenWidth * (GameConstants.LEVEL_GRID_BORDER * 2))) / GameConstants.MAX_GRID_BLOCK_WIDTH);
        int maxYSize = (int) ((screenHeight - (screenHeight * (GameConstants.LEVEL_GRID_BORDER * 2))) / GameConstants.MAX_GRID_BLOCK_HEIGHT);
        return maxXSize > maxYSize ? maxYSize : maxXSize;
    }

    /**
     * calculate the left border of this grid, if the grid needs to be centered on the screen
     *
     * @param screenWidth the width of the screen, measured in pixel
     * @param gridWidth the width of the grid, measured in blocks
     * @param blockSize the size of one block on the grid, measured in pixel
     * @return the left most coordinate where the grid shall be drawn on the screen, measured in pixel
     */
    public static int calculateStartX(int screenWidth, int gridWidth, int blockSize) {
        return (screenWidth / 2) - ((gridWidth * blockSize) / 2);
    }

    /**
     * calculate the top border of this grid, if the grid needs to be centered on the screen
     *
     * @param screenHeight the height of the screen, measured in pixel
     * @param gridHeight the height of the grid, measured in blocks
     * @param blockSize the size of on block on the grid, measured in pixel
     * @return the top most coordinate where the grid shall be drawn on the screen, measured in pixel
     */
    public static int calculateStartY(int screenHeight, int gridHeight, int blockSize) {
        return (screenHeight / 2) - ((gridHeight * blockSize) / 2);
    }
}
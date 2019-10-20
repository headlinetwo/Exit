package de.headlinetwo.exit.game.gui;

import android.graphics.Color;
import android.graphics.Paint;

import de.headlinetwo.exit.game.logic.Grid;
import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.VoidBlock;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.util.direction.Direction;
import de.headlinetwo.exit.util.direction.IntercardinalDirection;

/**
 * Created by headlinetwo on 02.12.17.
 */

public class GridDrawer {

    private static final Direction SHADOW_DIRECTION = IntercardinalDirection.SOUTH_EAST;

    private Grid grid; //the grid to draw

    private Paint shadowPaint; //the paint used to draw the black shadow around the blocks on the grid
    private float shadowOffset; //the offset of the shadow, measured in pixel

    public GridDrawer(Grid grid) {
        this.grid = grid;

        shadowPaint = new Paint();
        shadowPaint.setColor(Color.BLACK);

        shadowOffset = GameConstants.THIRTY_PERCENT;
    }

    public void draw(GamePanel graphicsPanel) {
        //draw shadow first to not overlay some blocks
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                if (grid.isOutlineRelevant(x, y, SHADOW_DIRECTION)) {
                    graphicsPanel.fillRect(x + shadowOffset, y + shadowOffset, shadowPaint);
                }
            }
        }

        //draw the actual squares on top of the shadow
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                AbstractBlock block = grid.getBlock(x, y);

                if (block instanceof VoidBlock) continue; //void blocks dont need to be drawn. (same as backgroud color)
                block.draw(graphicsPanel);
            }
        }
    }
}
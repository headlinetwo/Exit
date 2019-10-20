package de.headlinetwo.exit.game.logic.blocks;

import android.graphics.Paint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.logic.blocks.blocks.AppendPlayerBodyBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.FieldBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.GoalBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.PortalBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.RemovePlayerBodyBlock;
import de.headlinetwo.exit.game.logic.blocks.blocks.VoidBlock;

/**
 * Created by headlinetwo on 22.10.17.
 */
public enum BlockType {

    VOID(GameConstants.LEVEL_BACKGROUND_COLOR, VoidBlock.class),
    FIELD(GameConstants.FIELD_BLOCK_COLOR, FieldBlock.class),

    GOAL(GameConstants.GOAL_BLOCK_COLOR, GoalBlock.class, 10),
    PORTAL(GameConstants.PORTAL_BLOCK_COLOR, PortalBlock.class, 1),
    ADD_PLAYER_BODY_BLOCK(GameConstants.ADD_PLAYER_BODY_BLOCK_COLOR, AppendPlayerBodyBlock.class, 2),
    REMOVE_PLAYER_BODY_BLOCK(GameConstants.REMOVE_PLAYER_BODY_BLOCK_COLOR, RemovePlayerBodyBlock.class, 3),

    PLAYER_HEAD(GameConstants.PLAYER_HEAD_COLOR, null),
    PLAYER_HEAD_CONNECTOR_BLOCK(GameConstants.PLAYER_BODY_COLOR, null),
    PLAYER_STOMACH(GameConstants.PLAYER_BODY_COLOR, null),
    PLAYER_TAIL(GameConstants.PLAYER_BODY_COLOR, null);

    private int defaultColor;
    private Class<? extends AbstractBlock> blockClass;
    private Paint paint;
    private int actionBlockSortIndex; //once multiple player-snakes enter multiple WalkableBlocks the blocks with the lowest sortIndex are activated first

    BlockType(int defaultColor, Class<? extends AbstractBlock> blockClass) {
        this(defaultColor, blockClass, -1);
    }

    BlockType(int defaultColor, Class<? extends AbstractBlock> blockClass, int actionBlockSortIndex) {
        this.defaultColor = defaultColor;
        this.blockClass = blockClass;
        this.actionBlockSortIndex = actionBlockSortIndex;

        this.paint = new Paint();
        this.paint.setColor(defaultColor);
    }

    /**
     * @param value the string of a block type
     * @return the block type associated by the given string
     */
    public static BlockType getBlockType(String value) {
        try {
            return valueOf(value);
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * @return the default color representing the given block type
     */
    public int getColor() {
        return defaultColor;
    }

    /**
     * @return the paint used to draw a block of the given type to the screen
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * @return used as an indicator to determine which blocks shall be triggered before others if
     * multiple player-snakes enter multiple action-blocks during one swipe. (smaller numbers
     * are triggered before bigger numbers)
     */
    public int getActionBlockSortIndex() {
        return actionBlockSortIndex;
    }

    /**
     * Constructs a new class instance of the given block types associated block class
     * @param gridX the x-coordinate of the newly constructed block
     * @param gridY the y-coordinate of the newly constructed block
     * @return the newly constructed block of the given type
     */
    public AbstractBlock getNewBlockInstance(int gridX, int gridY) {
        try {
            Constructor<?> constructor = blockClass.getConstructor(int.class, int.class);
            return (AbstractBlock) constructor.newInstance(gridX, gridY);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
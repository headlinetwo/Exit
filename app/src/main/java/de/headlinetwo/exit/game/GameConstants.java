package de.headlinetwo.exit.game;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class GameConstants {

    public static final Random RANDOM = new Random();

    public static final int PLAYER_HEAD_COLOR = Color.rgb(146, 43, 33);
    public static final int PLAYER_BODY_COLOR = Color.rgb(205, 97, 85);

    public static final int PLAYER_HEAD_COLOR_BLOCKING = Color.argb(200, 169, 169, 169);
    public static final int PLAYER_BODY_COLOR_BLOCKING = Color.argb(200, 192, 192, 192);

    public static final int GOAL_BLOCK_COLOR = Color.YELLOW;
    public static final int FIELD_BLOCK_COLOR = Color.rgb(247, 220, 111);

    public static final int PORTAL_BLOCK_COLOR = Color.rgb(100, 10, 100);
    public static final int ADD_PLAYER_BODY_BLOCK_COLOR = Color.GRAY;
    public static final int REMOVE_PLAYER_BODY_BLOCK_COLOR = Color.DKGRAY;

    public static final int LEVEL_BACKGROUND_COLOR = Color.rgb(47, 47, 79);

    public static final float LEVEL_GRID_BORDER = .05f;

    public static final int MAX_GRID_BLOCK_WIDTH = 15; //the maximum number of blocks stacked in horizontal direction on the grid
    public static final int MAX_GRID_BLOCK_HEIGHT = 15; //the maximum number of blocks stacked in vertical direction on the grid

    public static final float FIVE_PERCENT = 0.05f;
    public static final float TEN_PERCENT = .1f;
    public static final float TWENTY_PERCENT = .2f;
    public static final float THIRTY_PERCENT = 0.3f;
    public static final float FORTY_PERCENT = 0.4f;
    public static final float FORTY_FIVE_PERCENT = 0.45f;
    public static final float FIFTY_PERCENT = 0.5f;
    public static final float EIGHTY_PERCENT = .8f;
    public static final float SEVENTY_PERCENT = 0.7f;
    public static final float NINETY_PERCENT = .9f;
    public static final float NINETY_FIVE_PERCENT = 0.95f;
}
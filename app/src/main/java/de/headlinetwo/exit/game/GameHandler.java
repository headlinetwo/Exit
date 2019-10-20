package de.headlinetwo.exit.game;

import android.widget.TextView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.level.Level;
import de.headlinetwo.exit.config.level.LevelConfig;
import de.headlinetwo.exit.util.direction.CardinalDirection;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class GameHandler {

    private LevelActivity levelActivity;
    private GamePanel gamePanel; //the panel used for all drawing
    private Level currentLevel; //the level which is currently played
    private GameThread gameThread; //tick / fps thread
    private TextView swipeCountTextView = null; //used to display the current swipe count

    public GameHandler(LevelActivity levelActivity, GamePanel gamePanel, LevelConfig levelData) {
        this.levelActivity = levelActivity;
        this.gamePanel = gamePanel;

        initialize(levelData);
        startNewThread();
    }

    public void initialize(LevelConfig levelData) {
        this.currentLevel = new Level(this, levelData);
        gamePanel.initialize(this);
        gamePanel.onGridSizeChange();
        if (swipeCountTextView != null) updateSwipeCountTextView(0);
    }

    public void startNewThread() {
        gameThread = new GameThread(this);
        gameThread.start();
    }

    /**
     * Handles a new move of all player-snakes in the given direction
     * @param direction the direction in which all player-snakes shall move
     */
    public void handleMove(CardinalDirection direction) {
        currentLevel.getUserInputHandler().handleUserInput(direction);
    }

    /**
     * Draws all the level contents (player-snakes, grid, blocks etc.) on the screen
     */
    public void draw() {
        getCurrentLevel().getGridDrawer().draw(gamePanel);

        for (Player player : currentLevel.getPlayers()) {
            player.draw(gamePanel);
        }

        gamePanel.drawLevelHintText(currentLevel.getHintBoxTextLines());

        gamePanel.redraw();
    }

    /**
     * Called every tick to update the whole game (e.g. player-snake animations etc.)
     */
    public void tick() {
        currentLevel.tick();
    }

    /**
     * Updates the swipe count, displayed in the top right corner of the screen, accordingly
     * @param currentSwipeCount the new swipe count value
     */
    public void changeSwipeCount(int currentSwipeCount) {
        if (swipeCountTextView == null) return;

        updateSwipeCountTextView(currentSwipeCount);
    }

    /**
     * Updates the text view (top right corner of the screen) to the given value
     * @param currentSwipeCount the new swipe count value
     */
    public void updateSwipeCountTextView(int currentSwipeCount) {
        swipeCountTextView.setText(currentSwipeCount + " | " + MainActivity.playerDataManager.getCurrentRecordInfoString(currentLevel.getLevelIndex()));
    }

    /**
     * Called if the screens dimensions change
     */
    public void onScreenSizeChange() {
        if(currentLevel != null) currentLevel.onScreenSizeChange();
    }

    public LevelActivity getLevelActivity() {
        return levelActivity;
    }

    public GamePanel getGraphicsPanel() {
        return gamePanel;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    /**
     * @param swipeCountTextView the text view used to display the current swipe count
     */
    public void setSwipeCountTextView(TextView swipeCountTextView) {
        this.swipeCountTextView = swipeCountTextView;
    }
}
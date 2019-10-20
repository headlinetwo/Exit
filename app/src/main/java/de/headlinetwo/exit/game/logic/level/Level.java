package de.headlinetwo.exit.game.logic.level;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.config.level.GamePlayerConfig;
import de.headlinetwo.exit.config.level.LevelConfig;
import de.headlinetwo.exit.game.GameHandler;
import de.headlinetwo.exit.game.gui.GridDrawer;
import de.headlinetwo.exit.game.logic.Grid;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.level.animations.LevelAnimationHandler;
import de.headlinetwo.exit.game.menu.LevelPauseMenu;
import de.headlinetwo.exit.game.menu.LevelStuckMenu;
import de.headlinetwo.exit.game.menu.LevelWonMenu;
import de.headlinetwo.exit.menu.AbstractAlertDialogMenu;
import de.headlinetwo.exit.util.Point;

/**
 * Created by headlinetwo on 22.10.17.
 */
public class Level {

    private Level instance;

    private LevelConfig config;
    private GameHandler gameHandler;
    private LevelState currentState = LevelState.RUNNING;
    private LevelUserInputHandler userInputHandler;

    private int levelIndex;

    private Grid grid;
    private GridDrawer gridDrawer;
    private Player[] players;

    private int currentSwipeCount = 0;

    private String[] hintBoxTextLines; //the hint text lines displayed below the level grid on the screen

    private LevelAnimationHandler animationHandler;

    public Level(GameHandler gameHandler, LevelConfig config) {
        this.instance = this;

        this.gameHandler = gameHandler;
        this.config = config;
        this.userInputHandler = new LevelUserInputHandler(this);
        this.levelIndex = config.getLevelIndex();

        onScreenSizeChange(); //initialize the hint text

        grid = new Grid(config.getGridWidth(), config.getGridHeight());
        grid.setBlocks(config.getBlocks());

        players = new Player[config.getPlayers().size()];

        gridDrawer = new GridDrawer(grid);

        int playerIndex = 0;
        for (GamePlayerConfig gamePlayerConfig : config.getPlayers()) {
            players[playerIndex++] = new Player(this, gamePlayerConfig.getPlayerType(), gamePlayerConfig.getCoordinates().toArray(new Point[gamePlayerConfig.getCoordinates().size()]));
        }

        grid.setPlayers(players);

        this.animationHandler = new LevelAnimationHandler();
    }

    /**
     * Updates the level hint text as soon as the apps drawing area on the users screen change
     */
    public void onScreenSizeChange() {
        if (config.getHintTextIndex() != LevelConfig.NO_LEVEL_HINT_TEXT) {
            String[] allHintTexts = gameHandler.getLevelActivity().getResources().getStringArray(R.array.level_hint_texts);
            if (allHintTexts.length > config.getHintTextIndex()) {
                this.hintBoxTextLines = gameHandler.getGraphicsPanel().splitHintBoxTextLines(allHintTexts[config.getHintTextIndex()]);
            }
            else this.hintBoxTextLines = new String[]{"ERROR!", "The hint text could not be found!"};
        }
        else hintBoxTextLines = new String[0];
    }

    /**
     * Increases the displayed swipe count on the top left of the screen by one
     */
    public void increaseSwipeCount() {
        currentSwipeCount++;
        gameHandler.changeSwipeCount(currentSwipeCount);
    }

    public void tick() {
        for (Player player : players) player.tick();

        animationHandler.tick();
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public LevelState getCurrentState() {
        return currentState;
    }

    public LevelUserInputHandler getUserInputHandler() {
        return userInputHandler;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public Grid getGrid() {
        return grid;
    }

    public Player[] getPlayers() {
        return players;
    }

    public GridDrawer getGridDrawer() {
        return gridDrawer;
    }

    public String[] getHintBoxTextLines() {
        return hintBoxTextLines;
    }

    public LevelAnimationHandler getAnimationHandler() {
        return animationHandler;
    }

    public void setCurrentState(LevelState newState) {
        if (newState == currentState) return; //cant change to same state twice!

        currentState = newState;

        //show and create menu only in ui thread
        gameHandler.getLevelActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AbstractAlertDialogMenu menu = null;

                if (currentState == LevelState.WON) {
                    menu = new LevelWonMenu(gameHandler.getLevelActivity(), currentSwipeCount, MainActivity.basicLevelConfigs[getLevelIndex()].getShortestCombination(), MainActivity.playerDataManager.getNumberOfUsedSwipes(getLevelIndex()));

                    MainActivity.playerDataManager.addUnlockedLevel(levelIndex + 1); //unlock the next level
                    MainActivity.playerDataManager.addCompletedLevel(levelIndex, currentSwipeCount);
                    MainActivity.playerDataManager.saveFile();
                    MainActivity.instance.getFragmentHolder().getLevelSelectionPage().getAdapter().notifyDataSetChanged();
                    MainActivity.instance.getFragmentHolder().getLandingPage().updateProgressBar(MainActivity.playerDataManager.getNumberOfPerfectlyFinishedLevels());

                    gameHandler.updateSwipeCountTextView(currentSwipeCount); //instantly show new record
                }
                else if (currentState == LevelState.STUCK) menu = new LevelStuckMenu(gameHandler.getLevelActivity(), gameHandler.getCurrentLevel().getLevelIndex());
                else if (currentState == LevelState.PAUSE) menu = new LevelPauseMenu(gameHandler.getLevelActivity(), instance);

                if (menu != null) {
                    final AbstractAlertDialogMenu finalMenu = menu;
                    gameHandler.getLevelActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finalMenu.showMenu();
                        }
                    });
                }
            }
        });
    }
}
package de.headlinetwo.exit.game.logic.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.WalkableBlock;
import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.level.animations.LevelGridShakeHorizontallyAnimation;
import de.headlinetwo.exit.game.logic.level.animations.LevelGridShakeVerticallyAnimation;
import de.headlinetwo.exit.util.CallbackGroup;
import de.headlinetwo.exit.util.GroupableCallback;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.direction.CardinalDirection;

public class LevelUserInputHandler {

    private Level level;

    private boolean currentlyHandlingPlayerAnimations = false;

    public LevelUserInputHandler(Level level) {
        this.level = level;
    }

    /**
     * Handles a swipe in the given direction on the screen by the user
     * @param direction the direction in which the swipe was handled
     */
    public void handleUserInput(CardinalDirection direction) {
        if (level.getCurrentState() != LevelState.RUNNING || currentlyHandlingPlayerAnimations) return;

        HashSet<Player> ableToMovePlayers = new HashSet<>();

        for (Player player : level.getPlayers()) {
            if (player.getCurrentState() == EntityState.IN_GOAL) continue;
            if (!player.canMove(direction, level.getGrid())) continue;

            ableToMovePlayers.add(player);
        }

        CallbackGroup allPlayersFinishedMoving = new CallbackGroup() {
            @Override
            public void onAllFinish() {
                triggerActionBlocks(new HashMap<Player, Point>());
            }
        };

        if (!ableToMovePlayers.isEmpty()) {
            level.increaseSwipeCount();
            currentlyHandlingPlayerAnimations = true;
        }
        else {
            if (direction.isHorizontal()) level.getAnimationHandler().addAnimation(new LevelGridShakeHorizontallyAnimation(level.getGameHandler().getGraphicsPanel(), direction));
            else level.getAnimationHandler().addAnimation(new LevelGridShakeVerticallyAnimation(level.getGameHandler().getGraphicsPanel(), direction));
        }

        for (Player player : ableToMovePlayers) {
            player.move(direction, new GroupableCallback(allPlayersFinishedMoving));
        }
    }

    /**
     * Triggers all the different action blocks after all the player-snakes have been moved
     * if multiple action blocks are triggered simultaneously the
     * {@link BlockType#getActionBlockSortIndex()} is used to determine which block to trigger first
     * @param alreadyTriggeredBlocks
     */
    public void triggerActionBlocks(final HashMap<Player, Point> alreadyTriggeredBlocks) { //alreadyTriggeredBlocks -> avoid that the same block can be triggered multiple times
        final HashMap<Player, BlockType> playerAbleToTriggerAction = new HashMap<>();
        final ArrayList<Player> playersOnActionBlocks = new ArrayList<>();

        for (Player player : level.getPlayers()) {
            if (player.getCurrentState() == EntityState.IN_GOAL) continue;

            AbstractBlock currentBlock = level.getGrid().getBlock(player.getBody().getHead().getX(), player.getBody().getHead().getY());

            if (alreadyTriggeredBlocks.containsKey(player) && alreadyTriggeredBlocks.get(player).equals(new Point(currentBlock.getGridX(), currentBlock.getGridY()))) continue;

            if (currentBlock instanceof WalkableBlock) {
                if (((WalkableBlock) currentBlock).triggersActionOnEnter(player)) {
                    playerAbleToTriggerAction.put(player, currentBlock.getBlockType());
                    playersOnActionBlocks.add(player);
                }
            }
        }

        //sort in ascending order
        Collections.sort(playersOnActionBlocks, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return playerAbleToTriggerAction.get(p1).getActionBlockSortIndex() - playerAbleToTriggerAction.get(p2).getActionBlockSortIndex();
            }
        });

        if (!playerAbleToTriggerAction.isEmpty()) {
            CallbackGroup allPlayersFinishedTheirAction = new CallbackGroup() {
                @Override
                public void onAllFinish() {
                    triggerActionBlocks(alreadyTriggeredBlocks); //keep triggering until no player is able to trigger something
                }
            };

            for (Player player : playersOnActionBlocks) {
                AbstractBlock currentBlock = level.getGrid().getBlock(player.getBody().getHead().getX(), player.getBody().getHead().getY());
                alreadyTriggeredBlocks.put(player, new Point(currentBlock.getGridX(), currentBlock.getGridY()));
                ((WalkableBlock) currentBlock).onPlayerEnter(player, new GroupableCallback(allPlayersFinishedTheirAction));
            }
        }
        else allActionsFinished();
    }

    /**
     * Called after all action block have been triggered after the current swipe
     */
    private void allActionsFinished() {
        int numberOfPlayersCurrentlyMoving = 0;
        int numberOfPlayersInGoal = 0;
        int numberOfGoals = level.getGrid().getNumberOfBlocks(BlockType.GOAL);

        for (Player toCheck : level.getPlayers()) {
            if (toCheck.getCurrentState() == EntityState.MOVING) numberOfPlayersCurrentlyMoving++;
            else if (toCheck.getCurrentState() == EntityState.IN_GOAL) numberOfPlayersInGoal++;
        }

        if (numberOfPlayersCurrentlyMoving <= 0) { //all players finished their moving process
            if (numberOfPlayersInGoal >= numberOfGoals) level.setCurrentState(LevelState.WON);
            else {
                int numberOfPlayersAbleToMove = 0;
                for (Player toCheck : level.getPlayers()) {
                    if (!toCheck.isStuck(level.getGrid()) && toCheck.getCurrentState() != EntityState.IN_GOAL) numberOfPlayersAbleToMove++;
                }
                if (numberOfPlayersAbleToMove <= 0) level.setCurrentState(LevelState.STUCK);
            }
        }

        currentlyHandlingPlayerAnimations = false;
    }
}
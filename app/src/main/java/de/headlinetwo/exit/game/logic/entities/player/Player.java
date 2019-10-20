package de.headlinetwo.exit.game.logic.entities.player;

import de.headlinetwo.exit.game.logic.entities.player.drawmodel.AbstractPlayerDrawModel;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.Grid;
import de.headlinetwo.exit.game.logic.blocks.BlockState;
import de.headlinetwo.exit.game.logic.entities.EntityBody;
import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.AnimateablePlayerDrawModel;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerIdleDrawModel;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerMoveDrawModel;
import de.headlinetwo.exit.game.logic.level.Level;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.direction.CardinalDirection;

public class Player extends AbstractEntity {

    private static byte idCounter = 0; //creates unique ids for each player-snake

    private byte id = idCounter++; //unique id

    private PlayerDrawManager drawManager;
    private EntityState playerState;
    private AbstractPlayerDrawModel currentDrawModel;

    private Player instance;

    public Player(Level level, PlayerType playerType, Point... initialBody) {
        super(level, new EntityBody(initialBody));

        this.drawManager = new PlayerDrawManager(playerType);

        instance = this;

        setCurrentState(EntityState.IDLE, new PlayerIdleDrawModel(this));
    }

    /**
     * Moves this player-snake in the given direction (with animation)
     *
     * @param direction the direction of travel
     * @param animationFinishCallback called after the move animation has finished
     */
    public void move(final CardinalDirection direction, final Callback animationFinishCallback) {
        setCurrentState(EntityState.MOVING, new PlayerMoveDrawModel(this, direction, new Callback() {
            @Override
            public void onFinish() {
                handlePlayerMovement(getBody().getHead().getX() + direction.getAddX(), getBody().getHead().getY() + direction.getAddY());

                setCurrentState(EntityState.IDLE, new PlayerIdleDrawModel(instance));

                animationFinishCallback.onFinish();
            }
        }));
    }

    /**
     * Moves this player-snake in the given direction (without animation) immediately
     * @param targetX the new head target x-coordinate
     * @param targetY the new head target y-coordinate
     */
    public void handlePlayerMovement(int targetX, int targetY) {
        getLevel().getGrid().updatePlayerBlockStates(targetX, targetY, getBody().getTail().getX(), getBody().getTail().getY());
        getBody().move(targetX, targetY);
    }

    /**
     * needs to be called every tick in order to update current animations (move animation)
     */
    public void tick() {
        if (currentDrawModel instanceof AnimateablePlayerDrawModel) ((AnimateablePlayerDrawModel) currentDrawModel).tick();
    }

    /**
     * Draws this player-snake on the given game panel
     *
     * @param panel the game panel to draw this player-snake on
     */
    public void draw(GamePanel panel) {
        currentDrawModel.draw(panel);
    }

    /**
     * Checks whether or not this player-snake is currently able to move given the current state
     * of the grid and its position
     *
     * @param direction the direction in which this player-snake wants to move
     * @param grid the grid on which this player-snake is placed
     * @return {@code true} if this player-snake is able to move in the given direction,
     * {@code false} otherwise
     */
    public boolean canMove(CardinalDirection direction, Grid grid) {
        int targetX = getBody().getHead().getX() + direction.getAddX();
        int targetY = getBody().getHead().getY() + direction.getAddY();

        if (targetX < 0 || targetY < 0 || targetX >= grid.getWidth() || targetY >= grid.getHeight()) {
            //outside of the grids bounds
            return false;
        }

        if (!grid.getBlock(targetX, targetY).playerCanMoveTo()) return false;
        if (grid.getBlock(targetX, targetY).getBlockState() == BlockState.OCCUPIED_BY_PLAYER) return false;

        return true;
    }

    /**
     * Checks whether or not this player-snake is currently stuck on its position
     * @param grid the grid on which this player-snake is placed
     * @return {@code true} if this player-snake is not able to move in any direction, {@code false}
     * otherwise
     */
    public boolean isStuck(Grid grid) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (canMove(direction, grid)) return false;
        }

        return true;
    }

    /**
     * @return the draw manager used to calculate the different body rectangles
     * outlining this player-snakes body
     */
    public PlayerDrawManager getDrawManager() {
        return drawManager;
    }

    public EntityState getCurrentState() {
        return playerState;
    }

    public void setCurrentState(EntityState entityState, AbstractPlayerDrawModel drawModel) {
        this.playerState = entityState;
        this.currentDrawModel = drawModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
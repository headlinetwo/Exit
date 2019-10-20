package de.headlinetwo.exit.game.logic.entities.player;

import de.headlinetwo.exit.game.logic.entities.EntityBody;
import de.headlinetwo.exit.game.logic.level.Level;

public abstract class AbstractEntity {

    private Level level;
    private EntityBody body;

    public AbstractEntity(Level level, EntityBody initialBody) {
        this.level = level;
        this.body = initialBody;
    }

    /**
     * @return the level that this entity is currently a part of
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return the body of this entity
     */
    public EntityBody getBody() {
        return body;
    }
}
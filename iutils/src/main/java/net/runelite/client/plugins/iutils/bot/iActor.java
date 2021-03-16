package net.runelite.client.plugins.iutils.bot;

import net.runelite.api.Actor;
import net.runelite.api.AnimationID;
import net.runelite.client.plugins.iutils.api.Interactable;
import net.runelite.client.plugins.iutils.scene.Locatable;
import net.runelite.client.plugins.iutils.scene.Position;

public abstract class iActor implements Locatable, Interactable {
    protected final Actor actor;
    protected final Bot bot;

    public iActor(Bot bot, Actor actor) {
        this.bot = bot;
        this.actor = actor;
    }

    public Bot bot() {
        return bot;
    }

    @Override
    public Position position() {
        return new Position(actor.getWorldLocation());
    }

    /**
     * The {@link iActor} this actor is currently targetting, or {@code null} if none. An
     * NPC may target another {@link iActor} if they are attacking them, talking to them,
     * following them, etc.
     */
    public iActor target() { //TODO: check if int is required
        return (iActor) actor.getInteracting();
//        return target < 0x8000 ? bot.npcs.get(target) : bot.player(target - 0x8000);
    }

    public int orientation() {
        return actor.getOrientation();
    }

    /**
     * Gets the current animation the actor is performing.
     *
     * @return the animation ID
     * @see AnimationID
     */
    public int animation() {
        return actor.getAnimation();
    }

    /**
     * The current spot animation of the actor. Spot animations are animations that
     * temporarily replace the entire model of the actor (for example, the teleport
     * tablet animation for players)
     */
    public int spotAnimation() {
        return actor.getSpotAnimationFrame();
    }

    /**
     * Gets the health of the actor in {@link #getHealthScale()} units.
     *
     * The server does not transmit actors' real health, only this value
     * between zero and {@link #getHealthScale()}. Some actors may be
     * missing this info, in which case -1 is returned.
     */
    public int getHealthRatio() { return actor.getHealthRatio(); }

    /**
     * Gets the maximum value {@link #getHealthRatio()} can return
     *
     * For actors with the default size health bar this is 30, but
     * for bosses with a larger health bar this can be a larger number.
     * Some actors may be missing this info, in which case -1 is returned.
     */
    public int getHealthScale() { return actor.getHealthScale(); }

    public boolean isMoving() { return actor.isMoving(); }

    /**
     * Returns true if this Actor has died
     *
     * @return
     */
    public boolean isDead() { return actor.isDead(); }

    /**
     * The name of the actor, or {@code null} if it has none.
     */
    public abstract String name();

    /**
     * The combat level of the Actor, or {@code 0} if it has none.
     */
    public abstract int combatLevel();
}

/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.ui.events;

import java.util.EventObject;

import com.shadowfist.domain.game.Player;

/**
 * @author cgreene
 */
public class PlayerActionRequestEvent extends EventObject
{
    private static final long serialVersionUID = -2815023244354129157L;

    private PlayerAction playerAction;

    /**
     * @param player
     * @param playerAction
     */
    public PlayerActionRequestEvent(Player player, PlayerAction playerAction)
    {
        super(player);
        this.playerAction = playerAction;
    }

    /**
     * @return the player
     */
    public Player getPlayer()
    {
        return (Player)getSource();
    }

    /**
     * @return the playerAction
     */
    public PlayerAction getPlayerAction()
    {
        return playerAction;
    }

    // -------------------------------------------------------------------------
    /**
     * Enums to indicate a player action for a response is requested.
     */
    public static enum PlayerAction
    {
        RESPONSE, MAIN_SHOT_ACTION;
    }
}

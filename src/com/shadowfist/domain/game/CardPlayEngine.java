/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Edge;
import com.shadowfist.domain.card.types.Event;
import com.shadowfist.domain.card.types.Site;
import com.shadowfist.domain.card.types.State;
import com.shadowfist.domain.location.Location;
import com.shadowfist.domain.scene.PlayCardEffect;
import com.shadowfist.ui.PlayerUI;
import com.shadowfist.ui.console.Prompts;

/**
 * Separates the logic that the user interface needs to acquire from the
 * player in order to play a card.
 *
 * @author cgreene
 */
public class CardPlayEngine
{

    private PlayerUI playerUI;
    private Card card;

    /**
     * @param playerUI
     * @param card
     */
    public CardPlayEngine(PlayerUI playerUI, Card card)
    {
        this.playerUI = playerUI;
        this.card = card;
    }

    /**
     * @player the player attempting to play the card
     * @throws CardPlayException
     */
    public void play(Player player) throws CardPlayException
    {
        checkRequirements(player);

        Character subject = null;
        Location location = null;
        if (card instanceof State)
        {
            State state = (State)card;
            subject = playerUI.chooseSubject(player, state.getValidSubjects(player));
        }
        else if (card instanceof Site)
        {
            Site site = (Site)card;
            location = playerUI.chooseLocation(player, site.getValidLocations(player));
        }
        else if (card instanceof Character)
        {
            Character character = (Character)card;
            location = playerUI.chooseLocation(player, character.getValidLocations(player));
        }

        payCost(player);

        placeCard(player, subject, location);

        playEffectIntoScene(player);
    }

    /**
     * @param player
     * @throws CardPlayException
     */
    protected void checkRequirements(Player player) throws CardPlayException
    {
        if (!player.isRequirementsMet(card))
        {
            throw new CardPlayException(Prompts.NOT_ENOUGH_RESOURCES);
        }
        if (player.getPower() < card.getCost())
        {
            throw new CardPlayException(Prompts.NOT_ENOUGH_POWER);
        }
    }

    /**
     * @param player
     */
    protected void payCost(Player player)
    {
        player.payPower(card.getCost());
    }

    /**
     * @param player
     * @throws CardPlayException 
     */
    protected void placeCard(Player player, Character subject, Location location) throws CardPlayException
    {
        if (card instanceof Event)
        {
            card.play();
        }
        else if (card instanceof Edge)
        {
            card.play(player.getInPlay());
        }
        else if (card instanceof State)
        {
            State state = (State)card;
            state.play(subject);
        }
        else if (card instanceof Site)
        {
            playIntoLocation(player, location, card);
        }
        else //if (card instanceof Character)
        {
            playIntoLocation(player, location, card);
        }
    }

    /**
     * @param location
     * @param card
     * @throws CardPlayException 
     */
    protected void playIntoLocation(Player player, Location location, Card card) throws CardPlayException
    {
        if (location == null)
        {
            throw new CardPlayException(Prompts.NO_LOCATION_SELECTED);
        }
        if (!player.getInPlay().getLocations().contains(location))
        {
            player.getInPlay().addLocation(location);
        }
        card.play(location);
    }

    /**
     * @param player
     */
    protected void playEffectIntoScene(Player player)
    {
        Game game = player.getGame();
        game.queueEffect(new PlayCardEffect(card));
    }
}

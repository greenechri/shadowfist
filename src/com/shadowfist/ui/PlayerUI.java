/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.ui;

import java.util.List;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.location.Location;

/**
 * @author cgreene
 */
public interface PlayerUI
{

    /**
     * @param player
     * @param characters
     * @return
     */
    Character chooseSubject(Player player, List<Character> characters);

    /**
     * @param player
     * @param locations
     * @return
     */
    Location chooseLocation(Player player, List<Location> locations);

}

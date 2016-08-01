/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.cards.cik;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Foundation;
import com.shadowfist.domain.resource.Faction;
import com.shadowfist.domain.resource.Resource;
import com.shadowfist.domain.resource.Talent;

/**
 * @author cgreene
 */
public class Imp extends Character implements Foundation
{

    /**
     */
    public Imp()
    {
        super("Imp", "Demon Minion", 1, 1, null, new Resource[] { Faction.LOTUS, Talent.MAGIC });
    }
}

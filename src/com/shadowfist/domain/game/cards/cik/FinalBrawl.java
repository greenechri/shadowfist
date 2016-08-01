/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.cards.cik;

import com.shadowfist.domain.card.PlayAsPrinted;
import com.shadowfist.domain.resource.Faction;

/**
 * @author cgreene
 */
@PlayAsPrinted
public class FinalBrawl extends com.shadowfist.domain.game.cards.ltd.FinalBrawl
{

    /**
     * @param title
     * @param subtitle
     * @param cost
     */
    public FinalBrawl()
    {
        setResourceRequirements(Faction.DRAGONS, Faction.DRAGONS);
    }

}

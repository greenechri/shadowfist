/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

import com.shadowfist.domain.ability.Ability;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.resource.Resource;

/**
 * @author cgreene
 */
public abstract class Event extends Card
{

    /**
     * @param title
     * @param cost
     */
    public Event(String title, int cost, Resource[] resourceRequirements)
    {
        super(title, "Event", cost, resourceRequirements, null);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.game.Card#resolveEffect()
     */
    @Override
    public void resolveEffect()
    {
        for (Ability ability : getAbilities())
        {
            ability.use();
        }
    }
}

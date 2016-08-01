/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.scene;

/**
 * @author cgreene
 */
public abstract class Effect
{

    private Trigger trigger;

    /**
     * @return the trigger
     */
    public Trigger getTrigger()
    {
        return trigger;
    }

    /**
     * @param trigger the trigger to set
     */
    public void setTrigger(Trigger trigger)
    {
        this.trigger = trigger;
    }

    /**
     * Override to do something upon generation.
     */
    public abstract void generate();

    /**
     * Override to do something upon resolution.
     */
    public abstract void resolve();

}

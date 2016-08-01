/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

/**
 * @author cgreene
 */
public abstract class FengShuiSite extends Site
{

    private boolean revealed;

    /**
     * @param title
     * @param power 
     * @param body
     */
    public FengShuiSite(String title, int power, int body)
    {
        super(title, "Feng Shui Site", -1, power, body);
    }

    /**
     * @return the revealed
     */
    public boolean isRevealed()
    {
        return revealed;
    }

    /**
     * @param revealed the revealed to set
     */
    public void setRevealed(boolean revealed)
    {
        this.revealed = revealed;
    }

    /**
     * Overridden to gain the player 1 Power.
     */
    @Override
    public void effectGenerated()
    {
        if (getOwner().getInPlay().getFengShuiSites().size() == 1)
        {
            getOwner().gainPower(1);
        }
    }

    /**
     * Overridden to provide variable cost.
     */
    @Override
    public int getCost()
    {
        return getOwner().getInPlay().getFengShuiSites().size();
    }
}
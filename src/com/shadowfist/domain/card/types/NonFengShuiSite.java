/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

/**
 * @author cgreene
 */
public abstract class NonFengShuiSite extends Site
{

    /**
     * @param title
     * @param cost
     * @param power
     * @param body
     */
    public NonFengShuiSite(String title, int cost, int power, int body)
    {
        super(title, "Site", cost, power, body);
    }
}

/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.ui.events;

import java.util.EventListener;

/**
 * @author cgreene
 */
public interface PlayerActionRequestListener extends EventListener
{

    /**
     * @param event
     */
    public void playerActionRequested(PlayerActionRequestEvent event);
}

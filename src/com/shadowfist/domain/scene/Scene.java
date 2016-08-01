/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.scene;


/**
 * @author cgreene
 */
public interface Scene
{

    /**
     * 
     * @param effect
     * @return
     */
    public Effect generate(Effect effect);

    /**
     * 
     * @return
     */
    public Effect resolveNext();

    /**
     * @return
     */
    public boolean isEmpty();
    
}

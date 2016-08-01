/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.EventListenerList;

/**
 * @author cgreene
 */
public abstract class GamePart
{

    /** Delegate used to implement property-change-support. */
    protected transient EventListenerList listenerList = new EventListenerList();

    private long id;
    private GamePart parent;

    /**
     * Constructor.
     *
     * @param game
     */
    public GamePart(GamePart parent)
    {
        id = System.currentTimeMillis();
        this.parent = parent;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @return the game parent
     */
    public GamePart getParent()
    {
        return parent;
    }

    /**
     * Attach a non-null PropertyChangeListener to this object.
     *
     * @param l a non-null PropertyChangeListener instance
     * @throws IllegalArgumentException if the parameter is null
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener l)
    {
        listenerList.add(PropertyChangeListener.class, l);
    }

    /**
     * Report a property change to registered listeners.
     *
     * @param property the name of the property that changed
     * @param oldValue the old value of this property
     * @param newValue the new value of this property
     */
    protected void firePropertyChange(String property, Object oldValue, Object newValue)
    {
        if ((oldValue == newValue) || (oldValue != null && oldValue.equals(newValue)))
        {
            return;
        }
        PropertyChangeEvent event = new PropertyChangeEvent(this, property, oldValue, newValue);
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == PropertyChangeListener.class)
            {
                ((PropertyChangeListener)listeners[i + 1]).propertyChange(event);
            }
        }
    }
}

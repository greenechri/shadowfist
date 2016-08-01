/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.scene;

import java.util.Stack;

/**
 * @author cgreene
 */
@SuppressWarnings("serial")
public class SceneImpl extends Stack<Effect> implements Scene
{

    /* (non-Javadoc)
     * @see com.shadowfist.domain.scene.Scene#generate(com.shadowfist.domain.scene.Effect)
     */
    @Override
    public Effect generate(Effect effect)
    {
        effect.generate();
        return push(effect);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.scene.Scene#resolve()
     */
    @Override
    public Effect resolveNext()
    {
        Effect effect = pop();
        effect.resolve();
        return effect;
    }

}

package org.socialforce.drawer.impl;

import org.socialforce.model.InteractiveEntity;

import java.awt.*;

/**
 * Created by sunjh1999 on 2017/3/26.
 */
public class NonPaintDrawer<T extends InteractiveEntity> extends EntityDrawer<T>  {

    public NonPaintDrawer(Graphics2D device) {
        super(device);
    }

    /**
     * render no shapes for pattern.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, T pattern) {
    }
}

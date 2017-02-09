package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;

import java.awt.*;

/**
 * Created by Ledenel on 2016/8/10.
 */
public class SolidBox2DDrawer extends AwtDrawer2D<Box2D> {

    public SolidBox2DDrawer(Graphics2D device) {
        super(device);
    }

    /**
     * render the modelShape on the @code {Graphics2D} with color built-in.
     *  @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Box2D pattern) {
        if (g != null && pattern != null) {
            double size[] = new double[2];
            pattern.getSize().get(size);
            g.fill(new Rectangle.Double(pattern.getXmin(), pattern.getYmin(), size[0], size[1]));
        }
    }
}

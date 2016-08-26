package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Line2D;

import java.awt.*;

/**
 * Created by Ledenel on 2016/8/10.
 */
public class SolidBox2DDrawer extends AwtDrawer2D {

    public SolidBox2DDrawer(Graphics2D device, Box2D box) {
        super(device);
    }

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Drawable pattern) {
        if (pattern instanceof Box2D) {
            Box2D box = (Box2D) pattern;
            double size[] = new double[2];
            box.getSize().get(size);
            g.fill(new Rectangle.Double(box.getXmin(), box.getYmin(), size[0], size[1]));
        }
    }
}

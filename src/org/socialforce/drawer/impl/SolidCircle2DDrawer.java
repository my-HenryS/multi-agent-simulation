package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Ledenel on 2016/8/12.
 */
public class SolidCircle2DDrawer extends AwtDrawer2D {

    public SolidCircle2DDrawer(Graphics2D device, Circle2D circle) {
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
        if (pattern instanceof Circle2D) {
            Circle2D circle = (Circle2D) pattern;
            Point point = circle.getReferencePoint();
            double left = point.getX() - circle.getRadius();
            double top = point.getY() - circle.getRadius();
            double d = 2 * circle.getRadius();
            g.fill(new Ellipse2D.Double(left, top, d, d));
        }
    }
}

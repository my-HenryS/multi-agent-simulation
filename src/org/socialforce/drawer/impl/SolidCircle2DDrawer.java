package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Ledenel on 2016/8/12.
 */
public class SolidCircle2DDrawer extends AwtDrawer2D<Circle2D> {

    public SolidCircle2DDrawer(Graphics2D device) {
        super(device);
    }


    /**
     * render the physicalEntity on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Circle2D pattern) {
        if (g != null && pattern != null) {
            Point point = pattern.getReferencePoint();
            double left = point.getX() - pattern.getRadius();
            double top = point.getY() - pattern.getRadius();
            double d = 2 * pattern.getRadius();
            g.fill(new Ellipse2D.Double(left, top, d, d));
        }
    }
}

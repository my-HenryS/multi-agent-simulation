package org.socialforce.drawer.impl;

import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Segment2D;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by Ledenel on 2016/11/27.
 */
public class SolidSegment2DDrawer extends AwtDrawer2D<Segment2D> {
    public SolidSegment2DDrawer(Graphics2D device) {
        super(device);
    }

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Segment2D pattern) {
        Point2D[] pts = pattern.getExtrimePoint();
        float thick= 0.1f;
        g.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        g.draw(new Line2D.Double(pts[0].getX(),pts[0].getY(),pts[1].getX(),pts[1].getY()));
    }
}

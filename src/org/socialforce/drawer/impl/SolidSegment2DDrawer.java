package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Segment2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by sunjh1999 on 2017/1/20.
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
        if (g != null && pattern != null) {
            double x1 = pattern.getExtrimePoint()[0].getX();
            double x2 = pattern.getExtrimePoint()[1].getX();
            double y1 = pattern.getExtrimePoint()[0].getY();
            double y2 = pattern.getExtrimePoint()[1].getY();
            g.fill(new Rectangle.Double(2,2,10,10));
        }
    }
}

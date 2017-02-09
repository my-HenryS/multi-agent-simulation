package org.socialforce.drawer.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Rectangle2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class SolidRectangle2DDrawer extends AwtDrawer2D<Rectangle2D> {
    public SolidRectangle2DDrawer(Graphics2D device) {
        super(device);
    }


    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Rectangle2D pattern) {
        if (g != null && pattern != null) {
            AffineTransform old = g.getTransform();
            Point point = pattern.getReferencePoint();
            double length = pattern.getScale()[0], width = pattern.getScale()[1];
            double angle = pattern.getAngle();
            double x1 = point.getX() - length * Math.cos(angle);
            double y1 = point.getY() - length * Math.sin(angle);
            g.rotate(pattern.getAngle(), point.getX(), point.getY());
            g.draw(new java.awt.geom.Rectangle2D.Double(x1, y1, length, width));
            /*double length = pattern.getScale()[0], width = pattern.getScale()[1];

            float thick= (float) width;
            g.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));

            double x2 = point.getX()+length*Math.cos(angle)/2;
            double y2 = point.getY()+length*Math.sin(angle)/2;
            g.draw(new Line2D.Double(x1,y1,x2,y2));*/
        }
    }
}

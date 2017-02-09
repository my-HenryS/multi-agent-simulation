package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Rectangle2D;
import org.socialforce.geom.impl.Segment2D;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class SolidRectangle2DDrawer extends AwtDrawer2D<Rectangle2D> {
    public SolidRectangle2DDrawer(Graphics2D device) {
        super(device);
    }


    /**
     * render the modelShape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, Rectangle2D pattern) {
        if (g != null && pattern != null) {
            double length = pattern.getScale()[0], weidth = pattern.getScale()[1];
            double angle = pattern.getAngle();
            float thick= (float) weidth;
            g.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            Point center = pattern.getReferencePoint();
            double x1 = center.getX()-length*Math.cos(angle)/2;
            double y1 = center.getY()-length*Math.sin(angle)/2;
            double x2 = center.getX()+length*Math.cos(angle)/2;
            double y2 = center.getY()+length*Math.sin(angle)/2;
            g.draw(new Line2D.Double(x1,y1,x2,y2));
        }
    }
}

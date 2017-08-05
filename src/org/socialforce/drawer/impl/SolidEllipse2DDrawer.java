package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Ellipse2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Ledenel on 2017/6/24.
 */
public class SolidEllipse2DDrawer extends AwtDrawer2D<Ellipse2D> {
    public SolidEllipse2DDrawer(Graphics2D device) {
        super(device);
    }

    @Override
    public void renderShape(Graphics2D g, Ellipse2D pattern) {
        org.socialforce.geom.Point center = pattern.getReferencePoint();
        AffineTransform old = g.getTransform();
        AffineTransform rotated = new AffineTransform();
        rotated.rotate(pattern.getAngle(), center.getX(), center.getY());
        g.transform(rotated);
        g.fill(new java.awt.geom.Ellipse2D.Double(
                center.getX() - pattern.getA(),
                center.getY() - pattern.getB(),
                pattern.getA() * 2,
                pattern.getB() * 2
        ));
        g.setTransform(old);
    }
}

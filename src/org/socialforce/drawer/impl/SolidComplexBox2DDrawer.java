package org.socialforce.drawer.impl;

import org.socialforce.geom.impl.ComplexBox2D;

import java.awt.*;

/**
 * Created by Ledenel on 2016/10/23.
 */
public class SolidComplexBox2DDrawer extends AwtDrawer2D<ComplexBox2D> {

    private SolidBox2DDrawer drawer;

    public SolidComplexBox2DDrawer(Graphics2D device) {
        super(device);
        this.drawer = new SolidBox2DDrawer(device);
    }

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, ComplexBox2D pattern) {
        pattern.getBoxDictionary();
    }
}

package org.socialforce.drawer;

import org.socialforce.entity.Drawer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Ledenel on 2016/8/10.
 */
public abstract class AwtDrawer2D implements Drawer<Graphics2D> {
    @Override
    public Graphics2D getDevice() {
        return device;
    }

    @Override
    public void setDevice(Graphics2D device) {
        this.device = device;
    }

    public AwtDrawer2D(Graphics2D device) {
        this.device = device;
    }

    protected Graphics2D device;

    @Override
    public int getColor() {
        int c = color.getAlpha();
        c <<= 8;
        c |= color.getRed();
        c <<= 8;
        c |= color.getGreen();
        c <<= 8;
        c |= color.getBlue();
        return c;
    }

    @Override
    public void setColor(int color) {
        int b = color & 0xFF;
        color >>= 8;
        int g = color & 0xFF;
        color >>= 8;
        int r = color & 0xFF;
        color >>= 8;
        int a = color & 0xFF;
        this.color = new Color(r,g,b,a);
    }

    protected Color color = Color.black;

    //protected Rectangle2D bound = new Rectangle2D.Double();

    /**
     * draw the pattern on the specific device.
     */
    @Override
    public void draw() {
        device.setColor(color);
        renderShape(device);
    }

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     * @param g the graphics
     */
    public abstract void renderShape(Graphics2D g);
}

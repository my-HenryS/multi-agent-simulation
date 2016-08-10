package org.socialforce.drawer;

import org.socialforce.entity.Drawer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Ledenel on 2016/8/10.
 */
public abstract class AwtDrawer2D implements Drawer<Graphics> {
    @Override
    public Graphics getDevice() {
        return device;
    }

    @Override
    public void setDevice(Graphics device) {
        this.device = device;
    }

    protected Graphics device;

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

    Rectangle2D bound = new Rectangle2D.Double();

    /**
     * draw the pattern on the specific device.
     */
    @Override
    public void draw() {
        device.setColor(color);
        renderShape(device);
    }

    public abstract void renderShape(Graphics g);
}

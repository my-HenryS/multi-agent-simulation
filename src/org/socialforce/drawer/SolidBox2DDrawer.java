package org.socialforce.drawer;

import org.socialforce.entity.impl.Box2D;

import java.awt.*;

/**
 * Created by Ledenel on 2016/8/10.
 */
public class SolidBox2DDrawer extends AwtDrawer2D {

    public SolidBox2DDrawer(Graphics2D device, Box2D box) {
        super(device);
        this.box = box;
    }

    public Box2D getBox() {
        return box;
    }

    public void setBox(Box2D box) {
        this.box = box;
    }

    protected Box2D box;

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g the graphics
     */
    @Override
    public void renderShape(Graphics2D g) {
        double size[] = new double[2];
        box.getSize().get(size);
        g.fill(new Rectangle.Double(box.getXmin(),box.getYmin(),size[0],size[1]));
    }
}

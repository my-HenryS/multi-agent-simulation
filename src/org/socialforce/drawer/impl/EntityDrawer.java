package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.geom.*;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Monitor;

import java.awt.*;

/**
 * Created by Ledenel on 2017/3/1.
 */
public class EntityDrawer<EntityType extends InteractiveEntity> extends AwtDrawer2D<EntityType>{
    public EntityDrawer(Graphics2D device) {
        super(device);
        defaultShapeInstaller = new ShapeDrawer2DInstaller(device);
    }

    private DrawerInstaller defaultShapeInstaller;

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, EntityType pattern) {
        org.socialforce.geom.Shape shape = pattern.getShape();
        Drawer drawer = shape.getDrawer();
        if(drawer != null){
            if(!(pattern instanceof Monitor))
                colorfulDraw(shape);
        }
        else {
            defaultShapeInstaller.addDrawerSupport(shape);
            if(shape.getDrawer() != null){
                colorfulDraw(shape);
            }
        }
    }

    protected void colorfulDraw(Drawable shape) {
        Drawer drawer = shape.getDrawer();
        drawer.setColor(this.getColor());
        drawer.draw(shape);
    }
}

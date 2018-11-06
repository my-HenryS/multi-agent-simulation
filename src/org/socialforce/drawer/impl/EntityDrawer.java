package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.geom.*;
import org.socialforce.model.InteractiveEntity;

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
     * render the physicalEntity on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, EntityType pattern) {
        PhysicalEntity physicalEntity = pattern.getPhysicalEntity();
        Drawer drawer = physicalEntity.getDrawer();
        if(drawer != null){
            colorfulDraw(physicalEntity);
        }
        else {
            defaultShapeInstaller.addDrawerSupport(physicalEntity);
            if(physicalEntity.getDrawer() != null){
                colorfulDraw(physicalEntity);
            }
        }
    }

    protected void colorfulDraw(Drawable shape) {
        Drawer drawer = shape.getDrawer();
        drawer.setColor(this.getColor());
        drawer.draw(shape);
    }
}

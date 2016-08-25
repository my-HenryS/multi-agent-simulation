package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;

import java.awt.*;

/**
 * Created by Ledenel on 2016/8/25.
 */
public class ShapeDrawer2DInstaller implements DrawerInstaller {
    protected ShapeDrawer2DInstaller() {

    }

    public ShapeDrawer2DInstaller(Graphics2D graphics2D) {
        this();
        this.graphics2D = graphics2D;
    }



    protected Graphics2D graphics2D;


    /**
     * creates and set a proper drawer for a drawable.
     *
     * @param drawable the drawable.
     * @return true if the installer has a proper drawer for the drawable; otherwise false.
     */
    @Override
    public boolean addDrawerSupport(Drawable drawable) {
        return false;
    }

    /**
     * register a drawer in this installer for a specific drawable type.
     * the drawer will be replaced while there is already a drawer registered for the type.
     *
     * @param registeredDrawer
     * @param drawableType
     */
    @Override
    public void registerDrawer(Drawer registeredDrawer, Class<? extends Drawable> drawableType) {

    }

    @Override
    public void unregister(Class<? extends Drawable> type) {

    }
}

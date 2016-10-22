package org.socialforce.drawer.impl;

import org.socialforce.app.Scene;
import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Ledenel on 2016/8/27.
 */
public class SceneDrawerInstaller implements DrawerInstaller {
    /**
     * creates and set a proper drawer for a drawable.
     *
     * @param drawable the drawable.
     * @return true if the installer has a proper drawer for the drawable; otherwise false.
     */
    @Override
    public boolean addDrawerSupport(Drawable drawable) {
        if (drawable instanceof Scene) {
            drawable.setDrawer(sceneDrawer);
            Scene scene = (Scene) drawable;
            Iterable<InteractiveEntity> iterable = scene.getAllEntitiesStream()::iterator;
            for(InteractiveEntity entity : iterable) {
                sceneDrawer.installer.addDrawerSupport(entity.getShape());
            }
            return true;
        }
        return false;
    }

    public SceneDrawerInstaller(Graphics2D graphics, double ctrlWidth, double ctrlHeight) {
        this.sceneDrawer = new SceneDrawer(graphics, ctrlWidth,ctrlHeight);
    }

    protected SceneDrawer sceneDrawer;

    /**
     * register a drawer in this installer for a specific drawable type.
     * the drawer will be replaced while there is already a drawer registered for the type.
     *
     * @param registeredDrawer
     * @param drawableType
     */
    @Override
    public void registerDrawer(Drawer registeredDrawer, Class<? extends Drawable> drawableType) {
        if (Scene.class.isAssignableFrom(drawableType) && registeredDrawer instanceof SceneDrawer) {
            sceneDrawer = (SceneDrawer) registeredDrawer;
        }
    }

    @Override
    public void unregister(Class<? extends Drawable> type) {
        sceneDrawer = null;
    }

    @Override
    public Iterable<Drawer> getRegisteredDrawers() {
        return Collections.singletonList(sceneDrawer);
    }
}

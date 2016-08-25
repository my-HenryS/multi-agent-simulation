package org.socialforce.drawer;

import org.socialforce.geom.Shape;

/**
 * Created by Ledenel on 2016/8/24.
 */
public interface DrawerInstaller {
    /**
     * creates and set a proper drawer for a drawable.
     * @param drawable the drawable.
     * @return true if the installer has a proper drawer for the drawable; otherwise false.
     */
    boolean addDrawerSupport(Drawable drawable);

    /**
     * register a drawer in this installer for a specific drawable type.
     * the drawer will be replaced while there is already a drawer registered for the type.
     * @param registeredDrawer
     * @param drawableType
     */
    void registerDrawer(Drawer registeredDrawer,Class<? extends Drawable> drawableType);

    void unregister(Class<? extends Drawable> type);
}

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
}

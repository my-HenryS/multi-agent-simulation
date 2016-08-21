package org.socialforce.drawer;

/**
 * objects which can be drawed by a drawer.
 * @author Ledenel
 * @see Drawer
 * Created by Ledenel on 2016/8/1.
 */
public interface Drawable {
    /**
     * set the drawer for the drawable.
     * @param drawer the drawer.
     */
    void setDrawer(Drawer drawer);

    /**
     * get the current drawer the object is using.
     * @return the drawer.
     */
    Drawer getDrawer();
}

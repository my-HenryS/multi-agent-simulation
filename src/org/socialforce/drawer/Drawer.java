package org.socialforce.drawer;


import org.socialforce.geom.ModelShape;

/**
 * a drawer can draw a specific pattern on a specific device.
 * the pattern's color can be adjusted.
 *
 * @author Ledenel
 * @see ModelShape
 * @see Drawable
 * Created by Ledenel on 2016/7/31.
 */
public interface Drawer<DeviceType, DrawableType extends Drawable> {
    /**
     * draw the pattern on the specific device.
     * @param pattern
     */
    void draw(DrawableType pattern);

    /**
     * get the primary color of the pattern. <br>
     * the color scheme is ARGB.<br>
     * CAUTION: there is no guarantee that the color is the color drawed on the device.
     * please call draw() before to sync the color you get from the device.
     *
     * @return the ARGB-format color.
     */
    int getColor();

    /**
     * set the primary color of the pattern. <br>
     * the color scheme is ARGB.<br>
     * CAUTION: there is no guarantee that the pattern on the device will changed.
     * please call draw() to ensure that the changes have been applied on the device.
     *
     * @param color the ARGB-format color to set.
     */
    void setColor(int color);

    /**
     * get the device of the drawer.
     * @return the device which the drawer is using.
     */
    DeviceType getDevice();

    /**
     * set the device for the drawer.
     * @param device the device for the drawer.
     */
    void setDevice(DeviceType device);
}

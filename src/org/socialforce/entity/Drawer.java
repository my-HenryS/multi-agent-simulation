package org.socialforce.entity;


/**
 * a drawer can draw a specific pattern on a specific device.
 * the pattern's color can be adjusted.
 *
 * @author Ledenel
 * @see Shape
 * @see Drawable
 * Created by Ledenel on 2016/7/31.
 */
public interface Drawer {
    /**
     * draw the pattern on the specific device.
     */
    void draw();

    /**
     * get the primary color of the pattern. <br/>
     * the color scheme is ARGB.</br>
     * CAUTION: there is no guarantee that the color is the color drawed on the device.
     * please call draw() before to sync the color you get from the device.
     *
     * @return the ARGB-format color.
     */
    int getColor();

    /**
     * set the primary color of the pattern. <br/>
     * the color scheme is ARGB.</br>
     * CAUTION: there is no guarantee that the pattern on the device will changed.
     * please call draw() to ensure that the changes have been applied on the device.
     *
     * @param color the ARGB-format color to set.
     */
    void setColor(int color);
}

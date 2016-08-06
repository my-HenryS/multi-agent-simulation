package org.socialforce.entity;

/**
 * represent a box.
 * a box is a special shape which can be bounds of other shapes. <br>
 * a start point and a end point define a box like this: <br>
 * -----------.(end point)<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * .-----------<br>
 * (start point)
 *
 * @author Ledenel
 * @see Shape
 * Created by Ledenel on 2016/8/1.
 */
public interface Box extends Shape {
    /**
     * get the start point of the box.
     *
     * @return the start point.
     */
    Point getStartPoint();

    /**
     * get the end point of the box.
     *
     * @return the end point.
     */
    Point getEndPoint();

    /**
     * get the size of the box.
     *
     * @return the vector represented the size.
     */
    Vector getSize();

    /**
     * returns the intersection of this box and other box.
     *
     * @param other the box to be intersected.
     * @return the intersection.
     */
    Box intersect(Box other);

}

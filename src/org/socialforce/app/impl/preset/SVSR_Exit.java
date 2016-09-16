package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.app.StaticSceneValue;
import org.socialforce.geom.ClippableShape;
import org.socialforce.geom.ClipperShape;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Wall;

/**
 * 代表一个可变的缺口（出口）的一个具体取值。
 * 该场景变量以name作为场景中的实体名，
 * 以一个ClipperShape来规定出口的位置和大小。
 */
public class SVSR_Exit extends StaticSceneValue<ClipperShape> implements SceneValue<ClipperShape> {
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int priority;

    /**
     * 如果门有宽度，就切墙。
     * 如果门宽度为零，就什么也不做。
     * @param entity 场景里所有的墙
     */
    @Override
    public void applyEach(InteractiveEntity entity) {
        if (value.getBounds().getStartPoint().getX() != value.getBounds().getEndPoint().getX()
                && value.getBounds().getStartPoint().getY() != value.getBounds().getEndPoint().getY()) {
            Wall wall = (Wall) entity;
            wall.setShape(value.clip((ClippableShape) wall.getShape()));
        }
        else /*do nothing*/;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(SceneValue<ClipperShape> o) {
        return o.getPriority() - this.getPriority();
    }
}

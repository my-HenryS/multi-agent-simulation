package org.socialforce.scene.impl;

import org.socialforce.geom.ClipperModelShape;
import org.socialforce.geom.ModelShape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/1/17.
 */
public class SVSR_SingleExit implements SceneValue<ClipperModelShape> {
    protected ClipperModelShape exit;
    protected String name;
    protected SimpleSocialForceModel model = new SimpleSocialForceModel();

    /**
     * 获取所关联的实体名称。
     *
     * @return 场景中的实体名称。
     */
    @Override
    public String getEntityName() {
        return name;
    }

    /**
     * 设置获取所关联的实体名称。
     *
     * @param name 要设置的实体名称。
     */
    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    /**
     * 获得该场景参数赋值的值。
     *
     * @return 返回的具体值。
     */
    @Override
    public ClipperModelShape getValue() {
        return exit;
    }

    /**
     * 设置改场景参数赋值的值。
     *
     * @param value 要设置的值。
     */
    @Override
    public void setValue(ClipperModelShape value) {
        exit = value;
    }

    /**
     * 在墙上切出门
     * 如果墙宽度为零则什么都不做
     * 目前门的参考点必须在墙内
     * select(Point)偶尔会导致诡异的空指针问题,可以考虑尝试更加诡异的造一个0半径的圆
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
        InteractiveEntity wall;
        Wall temp;
        if (exit.getBounds().getStartPoint().getX() != exit.getBounds().getEndPoint().getX()
                && exit.getBounds().getStartPoint().getY() != exit.getBounds().getEndPoint().getY()) {
            wall = scene.getStaticEntities().selectTop(exit.getReferencePoint());
            Box2D wallShape = (Box2D) wall.getModelShape();
            ModelShape[] boxes =  exit.clip(wallShape);
            for (int j=0;j<boxes.length;j++){
                temp = new Wall(boxes[j]);
                temp.setModel(model);
                scene.getStaticEntities().add(temp);
            }
            scene.getStaticEntities().remove(wall);
        } else /*do nothing*/;
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
    public int compareTo(SceneValue<ClipperModelShape> o) {
        return 0;
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int priority;


}

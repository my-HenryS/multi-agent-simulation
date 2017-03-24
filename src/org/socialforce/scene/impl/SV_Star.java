package org.socialforce.scene.impl;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Velocity;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Star_Planet;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
/**
 * Created by Whatever on 2017/3/2.
 */
public class SV_Star implements SceneValue<Star_Planet> {
    protected Velocity velocity;
    protected DistanceShape shape;
    public SV_Star(DistanceShape shape, Velocity velocity) {
        this.shape = shape;
        this.velocity = velocity;
    }
    /**
     * 获取所关联的实体名称。
     *
     * @return 场景中的实体名称。
     */
    @Override
    public String getEntityName() {
        return name;
    }
    protected String name;
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
    public Star_Planet getValue() {
        return planet;
    }
    protected Star_Planet planet;
    /**
     * 设置改场景参数赋值的值。
     *
     * @param value 要设置的值。
     */
    @Override
    public void setValue(Star_Planet value) {
        this.planet = value;
    }
    /**
     * 将该赋值运用于特定场景。
     * 即，使用该赋值更改一个指定的场景。
     *
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
        planet = new Star_Planet(shape, velocity);
        planet.setName("Star");
        scene.addAgent(planet);
        planet.setScene(scene);
        planet.setModel(new SimpleSocialForceModel());
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
    public int compareTo(SceneValue<Star_Planet> o) {
        return 0;
    }
    @Override
    public int getPriority() {
        return 0;
    }
    @Override
    public void setPriority(int priority) {
    }
}

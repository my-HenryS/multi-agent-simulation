package org.socialforce.app.impl;


import org.socialforce.app.SceneParameter;
import org.socialforce.app.SceneValue;
import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SVSR_Exit;
import org.socialforce.app.impl.preset.SVSR_RandomAgentGenerator;
import org.socialforce.app.impl.preset.SVSR_SafetyRegion;

import java.util.LinkedList;


/**
 * Created by Whatever on 2016/12/2.
 */
public class SimpleSceneParameter implements SceneParameter {

    public SimpleSceneParameter(){}
    public SimpleSceneParameter(LinkedList<SceneValue> values){
        this.values = values;
    }

    protected String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 这个真的没问题么……
     * 我有点怀疑这个是不是设计的初衷，先mark
     * 应该是个ValueSet？
     */
    protected LinkedList<SceneValue> values = new LinkedList<>();
    public void addValue(SceneValue value){
        if (isValid(value)){
            values.addLast(value);
        }
        else ;//TODO 可能需要一个warning或者什么的，不确定
    }

    public SceneValue removeValue(){
        if (!values.isEmpty()){
            return values.removeLast();
        }
        else return null;
    }

    public LinkedList<SceneValue> getParameter(){
        return values;
    }



    /**
     * 作用应该是判断是不是合适的value
     * 实现上来说似乎只能先这么搞……
     * 具体什么样的参数才算valid再说吧。
     * TODO 在判据中加入具体的东西
     * @param value
     * @return
     */
    @Override
    public boolean isValid(SceneValue value) {
        if (value instanceof SVSR_AgentGenerator || value instanceof SVSR_RandomAgentGenerator){
            return true;
        }
        if (value instanceof SVSR_Exit){
            return true;
        }
        if (value instanceof SVSR_SafetyRegion){
            return true;
        }
        return false;
    }

    @Override
    public int getPreferedSize() {
        return 0;
    }

    @Override
    public Iterable<SceneValue> sample(int size) {
        return null;
    }

    @Override
    public Iterable<SceneValue> sample() {
        return null;
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
    public int compareTo(Object o) {
        return 0;
    }

    public SceneParameter clone(){
        return new SimpleSceneParameter(getParameter());
    }
}

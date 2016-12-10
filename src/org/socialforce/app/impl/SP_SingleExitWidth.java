package org.socialforce.app.impl;


import org.socialforce.app.SceneParameter;
import org.socialforce.app.SceneValue;
import org.socialforce.app.impl.preset.SVSR_SingleExit;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;

import java.util.LinkedList;


/**
 * 这里后面应该是尖括号，但是会出一些我不懂的问题，再说。
 * Created by Whatever on 2016/12/2.
 */
public class SP_SingleExitWidth implements SceneParameter {
    protected String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    protected Box2D exit = new Box2D(0,0,2,2);
    protected Point2D position = new Point2D(0,0);
    public void setPosition(Point2D position){
        this.position = position;
    }

    /**
     * 判断一个门的值是不是合理的
     * @param value 需要判断的sceneValue
     * @return 合理返回true
     */
    @Override
    public boolean isValid(SceneValue value) {
        if (value instanceof SVSR_SingleExit){
            if (value.getValue() instanceof Box2D) {
                Box2D temp = (Box2D) value.getValue();
                double width;
                if (temp.getAxisExpanede()){//X轴方向伸展，为门宽
                    width = temp.getEndPoint().getX()-temp.getStartPoint().getX();
                }
                else width = temp.getEndPoint().getY()-temp.getStartPoint().getY();
                if (width>=Min_width && width <= Max_width){
                    return true;
                }
                else return false;
            }
            else return false;//TODO 之后可能会有不是BOX的value……吗？
        }
        else return false;
    }

    /**
     * 获取一个默认的样本数量
     * @return
     */
    @Override
    public int getPreferedSize() {
        return (int) ((Max_width - Min_width)/0.25)+1;
    }

    /**
     * 自定义样本数量生成SceneValue
     * @param size 样本的数量
     * @return 一系列SceneValue
     */
    @Override
    public Iterable<SceneValue> sample(int size) {
        double delta = (Max_width - Min_width)/(size-1);
        LinkedList<SceneValue> exits = new LinkedList<>();
        exit.moveTo(position);
        for (int i = 0;i<size;i++){
            SVSR_SingleExit oneExit = new SVSR_SingleExit();
            exit.expandTo(Min_width+i*delta);
            oneExit.setValue(exit);
            exits.addLast(oneExit);
        }
        return exits;
    }

    /**
     * 生成一组默认数量的SceneValue
     * @return
     */
    @Override
    public Iterable<SceneValue> sample() {
        return sample(getPreferedSize());
    }

    /**
     * 最大宽度和最小宽度
     */
    protected double Max_width, Min_width;

    public void setWidths(double min_width, double max_width){
        if (min_width > max_width){
            double temp = min_width;
            min_width = max_width;
            max_width = temp;
        }
        this.Min_width = min_width;
        this.Max_width = max_width;
    }

    public void setWidths(){
        Min_width = 0.5;
        Max_width = 2;
    }

    /**
     * True for X,False for Y
     * @param direction
     */
    public void setExitDirection(boolean direction){
        exit.setxAxisExpanded(direction);
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
}

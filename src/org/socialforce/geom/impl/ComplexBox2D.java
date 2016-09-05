package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;

/**
 * 一组BOX的集合，对这些BOX的位置条件暂无要求。
 * 可能需要避免重叠等。
 * TODO: 明显需要支持更多的功能，包括更加奇怪的切削拼组，退化成BOX，和一些Shape的基本功能。
 * @see Box
 * Created by Whatever on 2016/8/31.
 */
public class ComplexBox2D implements Shape {
    protected Drawer drawer;
    /**
     * BoxDictionary[0]是起始点，[1]是结束点。
     */
    protected Point[][] BoxDictionary = new  Point[2][];
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer ;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public boolean contains(Point point) {
        for (int i= 0;i<BoxDictionary[0].length;i++)
        {
            if (new Box2D(BoxDictionary[0][i],BoxDictionary[1][i]).contains(point)){
                return true;
            }
        }
        return false;
    }

    @Override
    public double getDistance(Point point) {
        double distance=0,temp;
        for (int i = 0;i < BoxDictionary[0].length;i++){
            temp = new Box2D(BoxDictionary[0][i],BoxDictionary[1][i]).getDistance(point);
            if (temp < distance){
                distance = temp;
            }
        }
        return distance;
    }

    @Override
    public Point getReferencePoint() {
        return null;
    }

    @Override
    public Box getBounds() {
        return null;
    }

    @Override
    public boolean hits(Box hitbox) {
        return false;
    }

    @Override
    public void moveTo(Point location) {

    }

    @Override
    public Shape clone() {
        return null;
    }

    public void SetWithArray(Point[][] boxDictionary){
        this.BoxDictionary = boxDictionary;
    }
}

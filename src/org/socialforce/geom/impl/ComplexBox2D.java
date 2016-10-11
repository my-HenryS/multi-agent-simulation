package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;

/**
 * 一组BOX的集合，对这些BOX的位置条件暂无要求。
 * 可能需要避免重叠等。
 * @see Box
 * Created by Whatever on 2016/8/31.
 */
public class ComplexBox2D implements Shape {
    protected Drawer drawer;
    /**
     * BoxDictionary[0]是起始点，[1]是结束点。
     */
    protected Point[][] BoxDictionary = new  Point[2][];
    public ComplexBox2D(){

    }
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
        boolean flag = false;
        for (int i= 0;i<BoxDictionary[0].length;i++)
        {
            if (new Box2D(BoxDictionary[0][i],BoxDictionary[1][i]).contains(point)){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public double getDistance(Point point) {
        double distance=Double.POSITIVE_INFINITY,temp;
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
        return getBounds().getReferencePoint();
    }

    @Override
    public Box getBounds() {
        double xmin,xmax,ymin,ymax,temp;
        xmin = Double.POSITIVE_INFINITY;
        ymin = Double.POSITIVE_INFINITY;
        xmax = Double.NEGATIVE_INFINITY;
        ymax = Double.NEGATIVE_INFINITY;
        if (BoxDictionary[0].length > 0) {
            for (int i = 0; i < BoxDictionary[0].length; i++) {
                temp = BoxDictionary[0][i].getX();
                if (temp < xmin) {
                    xmin = temp;
                }
                temp = BoxDictionary[0][i].getY();
                if (temp < ymin) {
                    ymin = temp;
                }
                temp = BoxDictionary[1][i].getX();
                if (temp > xmax) {
                    xmax = temp;
                }
                temp = BoxDictionary[1][i].getY();
                if (temp > ymax) {
                    ymax = temp;
                }
            }
            return new Box2D(xmin, ymin, xmax - xmin, ymax - ymin);
        }
        return new Box2D();
    }

    @Override
    public boolean hits(Box hitbox) {
        boolean flag = false;
        for (int i= 0;i<BoxDictionary[0].length;i++)
        {
            if (new Box2D(BoxDictionary[0][i],BoxDictionary[1][i]).hits(hitbox)){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void moveTo(Point location) {
        double movedX,movedY;
        movedX = location.getX() - getReferencePoint().getX();
        movedY = location.getY() - getReferencePoint().getY();
        for (int i = 0;i < BoxDictionary[0].length;i++){
            BoxDictionary[0][i] = new Point2D(BoxDictionary[0][i].getX()+movedX,BoxDictionary[0][i].getY()+movedY);
            BoxDictionary[1][i] = new Point2D(BoxDictionary[1][i].getX()+movedX,BoxDictionary[1][i].getY()+movedY);
        }
    }

    @Override
    public Shape clone() {
        ComplexBox2D cloned = new ComplexBox2D();
        cloned.SetWithArray(BoxDictionary);
        return cloned;
    }

    public void SetWithArray(Point[][] boxDictionary){
        this.BoxDictionary = boxDictionary;
    }

    public Point[][] getBoxDictionary(){
        return BoxDictionary;
    }


    @Override
    public boolean equals(Object obj){
        Point[][] objDictionary;
        boolean[] flag = new boolean[2*BoxDictionary[0].length];
        boolean flag1 = true;
        if (obj instanceof ComplexBox2D){
            objDictionary = ((ComplexBox2D) obj).getBoxDictionary();
            if (objDictionary[0].length == BoxDictionary[0].length){
                for (int i = 0;i < BoxDictionary[0].length;i++){
                    flag[i] = false;
                    for (int j = 0;j < BoxDictionary[0].length;j++){
                        if (objDictionary[0][i].equals(BoxDictionary[0][j])
                                && objDictionary[1][i].equals(BoxDictionary[1][j])){
                            flag[i] = true;
                        }
                    }
                }
                for (int i = 0;i < BoxDictionary[0].length;i++){
                    flag[BoxDictionary[0].length+i] = false;
                    for (int j = 0;j < BoxDictionary[0].length;j++){
                        if (BoxDictionary[0][i].equals(objDictionary[0][j])
                                && BoxDictionary[1][i].equals(objDictionary[1][j])){
                            flag[BoxDictionary[0].length+i] = true;
                        }
                    }
                }
                for (int i = 0;i < flag.length;i++){
                    if (flag[i] == false){
                        flag1 = false;
                    }
                }
                return flag1;
            }
            return false;
        }
        return false;
    }
}

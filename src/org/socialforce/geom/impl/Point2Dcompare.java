package org.socialforce.geom.impl;

/**
 * Created by simulation on 2017/10/24.
 */
public class Point2Dcompare extends Point2D implements Comparable<Point2Dcompare> {
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point2Dcompare(int index,double x, double y) {
        super(x,y);
        this.index = index;
    }
    public void setX(double x){this.values[0]=x;}
    public void setY(double y){this.values[1]=y;}

    //从大到小排列
    public int compareTo(Point2Dcompare other){
        double mysize=this.getX()*this.getX()+this.getY()*this.getY();
        double othersize=other.getX()*other.getX()+other.getY()*other.getY();
        if(mysize<othersize){
            return -1;
        }else if(mysize>othersize){
            return 1;
        }else
            return 0;


    }

}

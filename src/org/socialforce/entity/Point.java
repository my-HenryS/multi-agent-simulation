package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface Point extends Vector {
    
    /**
     * get the X coordinate of the point
     * @return the X coordinate
     */ 
    public double getX();
    
    /**
     * get the Y coordinate of the point
     * @return the Y coordinate
     */ 
    public double getY();
    
    /**
     * @todo public double[dimention] getvector()?
     */
}

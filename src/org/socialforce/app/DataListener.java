package org.socialforce.app;

/**
 * Created by Ledenel on 2016/8/28.
 */
public interface DataListener<DataType> {
    void update(DataType data);
}

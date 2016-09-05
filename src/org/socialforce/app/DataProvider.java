package org.socialforce.app;

import javax.swing.event.ListDataListener;

/**
 * Created by Ledenel on 2016/8/28.
 */
public interface DataProvider<DataType> {
    DataType provide();
    String getName();
    void addListener(DataListener<DataType> listener);
    void removeListener(DataListener<DataType> listener);
}

package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.app.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ledenel on 2017/1/29.
 */
public abstract class DataProcessor<Input,Output> implements DataProvider<Output>,DataListener<Input> {
    Input data = null;List<DataListener<Output>> listeners = new ArrayList<>();

    @Override
    public void update(Input data) {
        this.data = data;
        for(DataListener<Output> l : listeners) {
            l.update(provide());
        }
    }

    @Override
    public abstract Output provide();

    @Override
    public String getName() {
        return "Input Dumper";
    }

    @Override
    public void addListener(DataListener<Output> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(DataListener<Output> listener) {
        listeners.remove(listener);
    }
}

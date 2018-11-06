package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.app.DataProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 代表一个数据处理单元。
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
        Type[] typeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        return String.format("%s -> %s Processor", typeArguments[0], typeArguments[1]);
    }

    @Override
    public void addListener(DataListener<Output> listener) {
        listeners.add(listener);
    }

    //@SafeVarargs
    public DataProcessor(DataListener<Output> ... outputs) {
        for(DataListener<Output> out: outputs) {
            this.addListener(out);
        }
    }

    @Override
    public void removeListener(DataListener<Output> listener) {
        listeners.remove(listener);
    }
}

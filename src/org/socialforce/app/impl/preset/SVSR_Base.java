package org.socialforce.app.impl.preset;

import org.socialforce.app.SceneValue;
import org.socialforce.app.ValueSet;
import org.socialforce.model.impl.SafetyRegion;

/**
 * Created by Ledenel on 2016/10/6.
 */
public abstract class SVSR_Base<ValueType> implements SceneValue<ValueType> {
    protected String name;
    protected int priority;

    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

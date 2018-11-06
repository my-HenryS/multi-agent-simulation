package org.socialforce.scene;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public interface ParameterPool {
    int size();

    boolean isEmpty();

    boolean contains(SceneParameter value);

    ParameterPool addLast(SceneParameter value);

    SceneParameter get(int index);

    SceneParameter remove(SceneParameter value);

    int indexOf(SceneParameter value);

    Iterator<SceneParameter> iterator();
}

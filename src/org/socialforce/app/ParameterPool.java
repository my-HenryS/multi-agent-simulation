package org.socialforce.app;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public interface ParameterPool {
    int size();

    boolean isEmpty();

    boolean contains(SceneParameter value);

    SceneParameter addLast(SceneParameter value);

    SceneParameter get(int index);

    SceneParameter remove(SceneParameter value);

    int indexOf(SceneParameter value);

    Iterator<SceneParameter> iterator();
}

package org.socialforce.scene;

import java.util.Iterator;

/**
 * set the value of a map
 */
public interface ValueSet{
    int size();

    boolean isEmpty();

    boolean contains(SceneValue value);

    SceneValue add(SceneValue value);

    SceneValue remove(SceneValue value);

    Iterator<SceneValue> iterator();
}

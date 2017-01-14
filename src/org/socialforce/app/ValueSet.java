package org.socialforce.app;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

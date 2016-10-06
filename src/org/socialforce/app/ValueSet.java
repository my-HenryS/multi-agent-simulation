package org.socialforce.app;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * set the value of a map
 */
public interface ValueSet extends Collection<SceneValue> {
    SceneValue findValueByName(String name);
}


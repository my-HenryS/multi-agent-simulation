package org.socialforce.app;

import java.util.Collection;
import java.util.Map;

/**
 * Setting the parameter of Map
 */
public interface ParameterSet extends Collection<SceneParameter> {
    void findParameterByName(String name);
}

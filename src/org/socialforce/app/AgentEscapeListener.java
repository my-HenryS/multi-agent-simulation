package org.socialforce.app;

import org.socialforce.model.Agent;

/**
 * Created by Ledenel on 2016/8/30.
 */
public interface AgentEscapeListener {
    /**
     * triggered while a agent is escaped.
     * @param scene the scene where the agent in.
     * @param escapeAgent the escaped agent.
     */
    void onAgentEscape(Scene scene, Agent escapeAgent);
}

package org.socialforce.app;

import org.socialforce.model.Agent;

/**
 * Created by Ledenel on 2016/8/30.
 */
public interface AgentEscapeListener {
    /**
     * 在Agent逃离场景时被触发的listener
     * @param scene Agent所在的场景
     * @param escapeAgent 逃跑的Agent
     */
    void onAgentEscape(Scene scene, Agent escapeAgent);
}

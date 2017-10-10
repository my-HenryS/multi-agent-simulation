package org.socialforce.model.impl;


import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public class DoorTurn extends Exit implements Influential {

    protected Box2D door;
    protected Segment2D doorLine;     //过门线（门内侧）
    protected Rectangle2D doorZone;   //门前预判区域


    public DoorTurn(Box2D box2D) {
        super(box2D);
        door = box2D;
        doorLine = new Segment2D((Point2D) door.getStartPoint(), new Point2D(door.getXmax(), door.getYmin()));
        doorZone = doorLine.flatten(0.4);
    }

    @Override
    public DoorTurn clone() {
        return new DoorTurn((Box2D) door.clone());
    }


    @Override
    public PhysicalEntity getView() {
        return new DoubleShape2D(door.clone(), doorZone.clone());
    }

    @Override
    public void affect(Agent target) {
        target.rotate(model.interactionMoment(this, target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {

        List<Agent> judgedAgent = new ArrayList<>();
        List<Agent> unjudgedAgent = new ArrayList<>();
        List<Segment2D> blockedLine = new ArrayList<>();
        for (Agent agent : affectableAgents) {
            if ((agent.getPhysicalEntity().intersects(doorLine)) && (((BaseAgent) agent).DoorTurnUnjudged))
                unjudgedAgent.add(agent);
            judgedAgent.add(agent);
        }

        //Iterable<Agent> agents = judgedAgent;
        for (Agent agent : judgedAgent) {

        }


        if (!judgedAgent.isEmpty()) {
            Iterator<Agent> agentIterator = judgedAgent.iterator();
            while (agentIterator.hasNext()) {
                affect(agentIterator.next());
                blockedLine.add(((Ellipse2D) agentIterator.next().getPhysicalEntity()).getProjection(doorLine));
            }
            double expectedAngle;
            Segment2D agentProjection;
            Segment2D comparedProjection = new Segment2D(new Point2D(0, 0), new Point2D(1.0e-7, 0));  //???
            List<Segment2D> restLine = doorLine.remove(blockedLine);
            Iterator<Segment2D> segmentIterator = restLine.iterator();
            agentIterator = unjudgedAgent.iterator();
            while (agentIterator.hasNext()) {
                ((BaseAgent) agentIterator.next()).DoorTurnUnjudged = false;
                agentProjection = ((Ellipse2D) agentIterator.next().getPhysicalEntity()).getProjection(doorLine);
                while (segmentIterator.hasNext()) {
                    if ((agentProjection.intersect(segmentIterator.next())) && (comparedProjection.getLenth() < segmentIterator.next().getLenth()))
                        comparedProjection = segmentIterator.next();
                }
                if (agentProjection.getLenth() > comparedProjection.getLenth()) {
                    expectedAngle = Math.acos(comparedProjection.getLenth() / (2 * ((Ellipse2D) agentIterator.next().getPhysicalEntity()).getA()));
                    ((BaseAgent) agentIterator.next()).setExpectedAngle(expectedAngle);
                    affect(agentIterator.next());
                }
            }
        } else {
            Iterator<Agent> agentIterator = unjudgedAgent.iterator();
            while (agentIterator.hasNext()) {    //不需要施加affect时放着不管？？？
                ((BaseAgent) agentIterator.next()).DoorTurnUnjudged = false;
            }
        }
    }

}

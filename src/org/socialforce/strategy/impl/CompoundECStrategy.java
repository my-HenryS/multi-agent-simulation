package org.socialforce.strategy.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Segment2D;
import org.socialforce.model.Agent;
import org.socialforce.model.impl.Entity;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;

import java.util.*;

/**
 * Created by sunjh1999 on 2017/1/28.
 * BEST STRATEGY
 */
public class CompoundECStrategy extends ECStrategy implements DynamicStrategy {
    LinkedList<Gate> gates = new LinkedList<>();
    int regionNum;
    LinkedList<Tree<String>> paths = new LinkedList<>();
    Graph<String> graph = new Graph<>();  //图的邻接表
    Fields fields = new Fields();
    char gateName = 'A';



    public CompoundECStrategy(Scene scene, PathFinder pathFinder){
        super(scene, pathFinder);
        pathFinder.clearCache();
        gates.addLast(new Gate(new Segment2D(new Point2D(-0.5,0.5), new Point2D(-0.5,2.5)), "A", 1.36));
        gates.addLast(new Gate(new Segment2D(new Point2D(3.5,0.5), new Point2D(3.5,2.5)), "B", 1.36));
        gates.addLast(new Gate(new Segment2D(new Point2D(4,7), new Point2D(5,7)), "C", 1));
        gates.addLast(new Gate(new Circle2D(new Point2D(-3,1.5), 0.1), "D"));
        gates.addLast(new Gate(new Segment2D(new Point2D(19.5,2.7), new Point2D(21.5,2.7)), "E", 1.36));
        gates.addLast(new Gate(new Segment2D(new Point2D(19.5,-0.6), new Point2D(21.5,-0.6)), "I", 1.36));
        gates.addLast(new Gate(new Segment2D(new Point2D(18,4), new Point2D(18.5,3)), "K", 1.12));
        gates.addLast(new Gate(new Segment2D(new Point2D(25.4,18.5), new Point2D(25.4,20.5)), "F", 1.36));
        gates.addLast(new Gate(new Segment2D(new Point2D(28.7,18.5), new Point2D(28.7,20.5)), "J", 1.36));
        gates.addLast(new Gate(new Circle2D(new Point2D(20.5,-2.5), 0.1), "G"));
        gates.addLast(new Gate(new Circle2D(new Point2D(30.5,19.5), 0.1), "H"));
        for(Gate gate:gates){
            gate.setScene(scene);
            scene.getStaticEntities().add(gate);
        }
        graph.combine("A", "B");  //twins
        graph.combine("C", "B");
        graph.combine("C", "E");
        graph.combine("C", "F");
        graph.combine("D", "A");  //toGoal
        graph.combine("E", "B");
        graph.combine("F", "B");
        graph.combine("E", "F");
        graph.combine("K", "B");
        graph.combine("K", "C");
        graph.combine("K", "E");
        graph.combine("K", "F");
        graph.combine("E", "I");  //twins
        graph.combine("G", "I");  //toGoal
        graph.combine("F", "J");  //twins
        graph.combine("H", "J");  //toGoal
        initMaps();
        setPaths("D");
        setPaths("G");
        setPaths("H");
        //System.out.println(paths.toString());
    }

    public void addGate(PhysicalEntity physicalEntity){
        gates.addLast(new Gate(physicalEntity, ""+gateName));
    }

    public void initMaps(){
        for(Gate gate:gates){
            if (gate.getName().equals("B")){
                gate.isExit();
            }
            Scene newScene = prepareScene(gate);
            pathFinder.addSituation(newScene, gate.getPhysicalEntity().getReferencePoint());
            fields.addMap(((AStarPath)pathFinder.plan_for(gate.getPhysicalEntity().getReferencePoint())).map, gate.getName());
            for(String target:graph.find(gate.getName())){
                Gate t = getGate(target);
                newScene = prepareScene(gate,t);
                pathFinder.addSituation(newScene, gate.getPhysicalEntity().getReferencePoint());
                fields.addMap(((AStarPath)pathFinder.constraint_plan_for(gate.getPhysicalEntity().getReferencePoint(), t.getPhysicalEntity().getReferencePoint())).map, t.getName(), gate.getName());
            }
        }
    }

    private Gate getGate(String name){
        for(Gate gate:gates){
            if(gate.getName().equals(name)) return gate;
        }
        return null;
    }

    private Scene prepareScene(Gate toAvoid){
        Scene newScene = scene.cloneWithStatics();
        gates.stream().filter(t -> t.isExit() && !t.equals(toAvoid)).forEach(t -> newScene.getStaticEntities().add(new Wall(((Segment2D) t.getPhysicalEntity()).flatten(0.6))));
        return newScene;
    }

    private Scene prepareScene(Gate toAvoid1, Gate toAvoid2){
        Scene newScene = scene.cloneWithStatics();
        gates.stream().filter(t -> t.isExit() && !t.equals(toAvoid1) && !t.equals(toAvoid2)).forEach(t -> newScene.getStaticEntities().add(new Wall(((Segment2D) t.getPhysicalEntity()).flatten(0.6))));
        return newScene;
    }

    @Override
    public void dynamicDecision() {
        pathDecision();
    }

    @Override
    public void pathDecision() {
        Agent agent;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            CompoundAStarPath designed_path = null;
            LinkedList<String> names = fields.nearbyNodes(agent.getPhysicalEntity().getReferencePoint());
            double [] front_num = new double[gates.size()];
            int temp = 0;
            for(Gate gate:gates){
                front_num[temp++] = fronts(agent, gate.getPhysicalEntity().getReferencePoint());
            }
            double t = Double.POSITIVE_INFINITY;
            for(Tree<String> path:paths){
                LinkedList<Node<String>> nodes = new LinkedList<>();
                for(String name:names){
                    nodes.addAll(path.findNodesByData(name));
                }
                for(int i = 0; i < nodes.size(); i++){
                    double new_t = 0;
                    CompoundAStarPath new_path = new CompoundAStarPath();
                    Node<String> node = nodes.get(i), lastnode;
                    new_path.addMap(fields.findMap(node.getData()));
                    Gate gate = getGate(node.getData());
                    if(gate.isExit()) new_t += front_num[gates.indexOf(gate)]/EC(gate.getWidth(), ((SimpleForceModel)agent.getModel()).getExpectedSpeed());
                    while(!node.isRoot()){
                        lastnode = node;
                        node = node.getParent();
                        gate = getGate(node.getData());
                        new_path.addMap(fields.findMap(lastnode.getData(), node.getData()));
                        if(gate.isExit()) new_t += front_num[gates.indexOf(gate)]/EC(gate.getWidth(), ((SimpleForceModel)agent.getModel()).getExpectedSpeed());
                    }
                    new_t += new_path.length(agent.getPhysicalEntity().getReferencePoint()) / ((SimpleForceModel)agent.getModel()).getExpectedSpeed();
                    if(new_t < t){
                        t = new_t;
                        designed_path = new_path;
                    }
                }
            }
            agent.setPath(designed_path);
        }
    }

    public void setPaths(String target){
        Tree<String> path = new Tree<>();
        Stack<Node<String>> candidates = new Stack<>();
        Node<String> root = new Node<>(target);
        candidates.push(root);
        path.setRoot(root);   //设置目标点
        while(!candidates.empty()){
            Node<String> candidate = candidates.pop();
            LinkedList<Node<String>> onPath = path.findPathByNode(candidate); //找路径上存在过的点
            for(String name: graph.find(candidate.getData())){
                boolean succ = true;
                for(Node<String> nodeOnPath: onPath){
                    if(nodeOnPath.getData().equals(name)){
                        succ = false;
                        break;
                    }
                }
                if(succ){
                    Node<String> newNode = new Node<>(name);
                    path.addNode(newNode, candidate);
                    candidates.push(newNode);
                }
            }
        }
        paths.addLast(path);
    }

    //TODO 不再extends entity
    private class Gate extends Entity{
        double width = 0;
        public Gate(PhysicalEntity physicalEntity, String name){
            super(physicalEntity);
            setName(name);
        }
        public Gate(PhysicalEntity physicalEntity, String name, double width){
            super(physicalEntity);
            setName(name);
            this.width = width;
        }

        public boolean isExit(){ return physicalEntity instanceof Segment2D;}

        @Override
        public double getMass() {
            return 0;
        }

        @Override
        public Gate clone() {
            return new Gate(physicalEntity.clone(), name, width);
        }

        public double getWidth(){ return width;}

        @Override
        public boolean onAdded(Scene scene) {
            return true;
        }

        @Override
        public void onStep(Scene scene) {

        }
    }

    private class Fields{
        private LinkedList<AStarPathFinder.Maps> mapSet = new LinkedList<>();
        private LinkedList<String> startPoints, endPoints;
        public Fields(){
            startPoints = new LinkedList<>();
            endPoints = new LinkedList<>();
        }
        public void addMap(AStarPathFinder.Maps map, String end){
            mapSet.addLast(map);
            startPoints.addLast("");  //没有起始门
            endPoints.addLast(end);
        }

        public AStarPathFinder.Maps findMap(String end){
            for(int i = 0; i < mapSet.size(); i++){
                if(endPoints.get(i).equals(end) && startPoints.get(i).equals("")) return mapSet.get(i);
            }
            return null;
        }

        public void addMap(AStarPathFinder.Maps map, String start, String end){
            mapSet.addLast(map);
            startPoints.addLast(start);
            endPoints.addLast(end);
        }

        public AStarPathFinder.Maps findMap(String start, String end){
            for(int i = 0; i < mapSet.size(); i++){
                if(endPoints.get(i).equals(end) && startPoints.get(i).equals(start)) return mapSet.get(i);
            }
            return null;
        }

        public LinkedList<String> nearbyNodes(Point start){
            LinkedList<String> nodes = new LinkedList<>();
            for(int i = 0; i < mapSet.size(); i++){
                if(startPoints.get(i).equals("") && mapSet.get(i).hasPrevious(start)){
                    if(!nodes.contains(endPoints.get(i)))
                            nodes.addLast(endPoints.get(i));
                }
            }
            return nodes;
        }
    }

    public class Node<T> {
        private T data = null;
        private Node<T> parent = null;
        private LinkedList<Node<T>> childs = new LinkedList<>();

        public Node(T data){  //根节点
            this.data = data;
            this.parent = this;
        }

        public Node(T data, Node<T> parent){
            this.data = data;
            this.parent = parent;
            parent.setChild(this);
        }

        public T getData(){
            return this.data;
        }

        public void setParent(Node<T> parent){
            this.parent = parent;
            parent.setChild(this);
        }

        public Node<T> getParent(){
            return this.parent;
        }

        public boolean isRoot(){ return this.parent == this;}

        private void setChild(Node<T> child){
            this.childs.addLast(child);
        }

        public LinkedList<Node<T>> getChildren(){
            return this.childs;
        }

        public boolean isLeaf(){ return this.childs.size() == 0; }
    }

    public class Tree<T>{    //实际上只是对Nodes提供方法约束 同时储存了所有路径 根Node本身就可以表示一棵树
        LinkedList<Node<T>> nodes = new LinkedList<>();
        public Tree(){}
        public Tree(Node<T> node){
            nodes.addLast(node);
        }

        public void setRoot(Node<T>node){
            nodes.add(0,node);
        }

        public Node<T> getRoot(){ return nodes.getFirst();}

        public boolean addNode(Node<T> node, Node<T> parent){
            for(Node<T> aNode:nodes){
                if(aNode == parent){
                    node.setParent(aNode);
                    nodes.addLast(node);
                    return true;
                }
            }
            return false;
        }

        public LinkedList<Node<T>> findPathByNode(Node<T> node){
            if(!nodes.contains(node)) return null;
            LinkedList<Node<T>> Path = new LinkedList<>();
            Path.addLast(node);
            while(!node.isRoot()){
                node = node.getParent();
                Path.addLast(node);
            }
            return Path;
        }

        public LinkedList<Node<T>> findNodesByData(T data){
            LinkedList<Node<T>> nodeWithData = new LinkedList<>();
            nodes.stream().filter(aNode -> aNode.getData().equals(data)).forEach(nodeWithData::addLast);
            return nodeWithData;
        }

        public String toString(){
            LinkedList<Node<T>> leaves = new LinkedList<>();
            String output = "";
            nodes.stream().filter(Node::isLeaf).forEach(leaves::addLast);
            for(Node<T> leaf:leaves){
                Node<T> present = leaf;
                output += present.getData().toString();
                while(!present.isRoot()){
                    present = present.getParent();
                    output +="->"+ present.getData().toString();
                }
                output += "\n";
            }
            return output;
        }
    }

    public class Graph<T> extends HashMap<T, List<T>> {  //储存图的邻接表

        private static final long serialVersionUID = 1L;

        public void combine(T key, T value) {
            if (!super.containsKey(key)) {
                super.put(key, new Vector<T>());
            }
            List<T> list = get(key);
            if (list.contains(value)) return;
            list.add(value);
            /*value作为key再来一次*/
            combine(value, key);
        }

        public List<T> find(T key) {
            return super.get( key ) ;
        }

    }

    public String toString(){
        String output = "";
        for(Tree<String> path: paths){
            output += path.toString();
        }
        return output;
    }

    @Override
    public int fronts(Agent agent, Point goal){
        int front_num = 0;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            Agent target_agent = (Agent) iter.next();
            if((target_agent.getPath() == null || target_agent.getPath().getCurrentGoal().equals(goal)) && agent.getPhysicalEntity().getDistance(goal) > target_agent.getPhysicalEntity().getDistance(goal)){
                front_num += 1;
            }
        }
        return front_num;
    }
}

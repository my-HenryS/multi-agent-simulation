package org.socialforce.strategy.impl;

import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Segment2D;
import org.socialforce.strategy.DynamicStrategy;

import java.util.*;

/**
 * Created by sunjh1999 on 2017/1/28.
 * BEST STRATEGY
 */
public class ComplexECStrategy implements DynamicStrategy {
    LinkedList<Gate> gates = new LinkedList<>();
    int regionNum;
    LinkedList<Tree<String>> paths = new LinkedList<>();
    Graph<String> graph = new Graph<>();  //图的邻接表

    public ComplexECStrategy(){
        gates.addLast(new Gate(new Segment2D(new Point2D(1,1), new Point2D(2,2)), "A"));
        gates.addLast(new Gate(new Segment2D(new Point2D(20,1), new Point2D(20,2)), "B"));
        gates.addLast(new Gate(new Segment2D(new Point2D(20,1), new Point2D(20,2)), "C"));
        gates.addLast(new Gate(new Segment2D(new Point2D(20,1), new Point2D(20,2)), "D"));
        graph.combine("A", "B");
        graph.combine("B", "C");
        graph.combine("D", "C");
        graph.combine("A", "D");
        setPaths("A");
        for(Tree<String> path: paths){
            System.out.println(path.toString());
        }
    }

    @Override
    public void dynamicDecision() {

    }

    @Override
    public void pathDecision() {

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

    private class Gate{
        Segment2D gate;
        String name;
        public Gate(Segment2D segment, String name){
            gate = segment;
            this.name = name;
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

    public static void main(String[] args){
        ComplexECStrategy complexECStrategy = new ComplexECStrategy();
    }


}

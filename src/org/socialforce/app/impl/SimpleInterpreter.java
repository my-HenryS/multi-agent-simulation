package org.socialforce.app.impl;

import org.socialforce.app.Interpreter;
import org.socialforce.container.impl.LinkListPool;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.StandardSceneLoader;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by sunjh1999 on 2017/1/15.
 * 这是一个解释器
 */
public class SimpleInterpreter implements Interpreter {
    File file;
    BufferedReader reader = null;
    String string = "";
    private String token = "";   //当前读入单词
    int f = 0; //指针 指向string的当前字符
    SceneLoader loader;
    protected static String[] reserved = {"Walls", "Wall", "Scene", "Circle"};

    @Override
    public void loadFile(File file) {
        this.file = file;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                string = string + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public String getContent(){
        return string;
    }

    @Override
    public SceneLoader setLoader() {
        Scene scene;
        Wall[] walls;
        getsym();
        if(token.equals("Scene")){
            getsym();
            if(!token.equals(":")) return null;
            getsym();
            scene = scene();
            walls = wall();
            loader = new StandardSceneLoader(scene, walls);
        }
        return loader;
    }


    protected boolean isReserved(String token){
        for(String s : reserved){
            if(token.equals(s)) return true;
        }
        return false;
    }

    protected boolean isNum(char ch){
        return (ch>='0' && ch<='9') || ch == '.';
    }

    protected boolean isChar(char ch){
        return (ch>='a' && ch<='z') || (ch>='A' && ch<='Z') || ch == '_' || ch == '-' ;
    }

    protected boolean isNumber(char ch){
        return (ch>='0' && ch<='9') || ch == '-' || ch == '.' ;
    }


    public void getsym(){
        token = "";
        char pchar = string.charAt(f++);
        while(pchar == ' ' || pchar == '\n' || pchar == '\t' || pchar == '\0' || pchar == ','){
            pchar = string.charAt(f++);
        }
        if(isNumber(pchar)){
            token = token + pchar;
            pchar = string.charAt(f++);
            while(isNum(pchar)){
                token = token + pchar;
                pchar = string.charAt(f++);
            }
            f--;
            return;
        }
        if(isChar(pchar)){
            while(isChar(pchar)){
                token = token + pchar;
                pchar = string.charAt(f++);
            }
            f--;
            if(isReserved(token)) return;
            else return;
        }
        if(pchar == ':'){
            token = token + pchar;
            return;
        }
        if(pchar == ';'){
            token = token + pchar;
            return;
        }
    }

    public Scene scene(){
        String sceneName = token;
        Class sceneClass = null;
        Constructor sceneConstructor = null;
        try {
            sceneClass = Class.forName("org.socialforce.scene.impl."+sceneName);
        } catch (ClassNotFoundException e) {
            return null;
        }
        try {
            sceneConstructor=sceneClass.getDeclaredConstructor(Box.class);
            sceneConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            return null;
        }
        getsym();
        if(!token.equals(":")) return null;
        getsym();
        Scene scene = null;
        try {
            scene = (Scene) sceneConstructor.newInstance(box2d());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public Wall[] wall(){
        LinkListPool<Wall> wallpool = new LinkListPool<>();
        Wall [] walls;
        if(!token.equals("Walls")) return null;
        getsym();
        if(!token.equals(":")) return null;
        getsym();
        while(token.equals("Wall")){
            getsym();
            Wall wall;
            if(!token.equals(":")) return null;
            getsym();
            if(token.equals("Box")){
                wall = new Wall(box2d());
            }
            else if(token.equals("Circle")){
                wall = new Wall(circle2d());
            }
            else wall = null;
            wallpool.addLast(wall);
        }
        walls = new Wall[wallpool.size()];
        for(int i = 0; i< wallpool.size(); i++){
            walls[i] = wallpool.get(i);
        }
        return walls;
    }

    public Box2D box2d(){
        double [] parav = new double[4];
        Box2D box;
        if(!token.equals("Box")) return null;
        getsym();
        if(!token.equals(":")) return null;
        getsym();
        for(int i = 0; i < 4; i++){
            parav[i] = Double.parseDouble(token);
            getsym();
        }

        if(token.equals("point-offset")){
            box = new Box2D(parav[0], parav[1], parav[2], parav[3]);
            getsym();
        }
        else if(token.equals("point-point")){
            box = new Box2D(new Point2D(parav[0], parav[1]), new Point2D(parav[2], parav[3]));
            getsym();
        }
        else if(token.equals(";")){  //缺省则使用point-point定义法
            box = new Box2D(new Point2D(parav[0], parav[1]), new Point2D(parav[2], parav[3]));
        }
        else return null;
        if(!token.equals(";")) return null;
        getsym();
        return box;
    }

    public Circle2D circle2d(){
        double [] parav = new double[3];
        Circle2D circle;
        if(!token.equals("Circle")) return null;
        getsym();
        if(!token.equals(":")) return null;
        getsym();
        for(int i = 0; i < 3; i++){
            parav[i] = Double.parseDouble(token);
            getsym();
        }

        if(token.equals("center-radius")){
            circle = new Circle2D(new Point2D(parav[0], parav[1]), parav[2]);
            getsym();
        }
        else if(token.equals(";")){  //缺省则使用center-radius定义法
            circle = new Circle2D(new Point2D(parav[0], parav[1]), parav[2]);
        }
        else return null;
        if(!token.equals(";")) return null;
        getsym();
        return circle;
    }
}

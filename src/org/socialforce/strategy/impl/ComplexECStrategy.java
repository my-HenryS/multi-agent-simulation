package org.socialforce.strategy.impl;

import org.socialforce.geom.impl.Segment2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by sunjh1999 on 2017/1/28.
 * BEST STRATEGY
 */
public class ComplexECStrategy {
    int [][] mapOfRegion;
    int regionNum;
    TMap<Integer,Integer> relationship = new TMap<>();
    private class Gate{
        Segment2D gate;
        public Gate(Segment2D segment){
            gate = segment;
        }
    }
    public class TMap<T, V> extends HashMap<T, List<V>> {

        private static final long serialVersionUID = 1L;

        public List<V> Put(T key, V value) {
        /* 判断该建是否已经存在 吗如果不存在 则放入一个新的Vector对象 */
            if (!super.containsKey(key)) {
                super.put(key, new Vector<V>());
            }
        /* 这里获取 key对应的List*/
            List<V> list = super.get(key);
         /* 将当前值，放入到 key对应的List中 */
            list.add(value);
        /* 返回当前 key对于的List对象*/
            return super.get(list);
        }

        @Override
        public List<V> get(Object key) {
            return super.get( key ) ;
        }

    }




}

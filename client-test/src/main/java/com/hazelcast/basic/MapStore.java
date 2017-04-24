package com.hazelcast.basic;
import com.hazelcast.core.Hazelcast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bijoypaul on 24/04/17.
 */
public class MapStore {

    private static final MapType DEFAULT =  MapType.DISTRIBUTED;

    public static Map<Integer, Integer> getMap(MapType type){
        Map<Integer, Integer> map = null;

        switch (type){
            case BASIC:
                        map = new HashMap<>();
                        break;
            case CONCURRENT:
                         map = new ConcurrentHashMap<>();
                         break;
            case DISTRIBUTED:
                         map = Hazelcast.newHazelcastInstance().getMap("testMap");
                         break;

        }
        return map;
    }

    public static void add(){
        Map<Integer, Integer> map = getMap(DEFAULT);
        Random random = new Random();
        for (int i = 0; i < 10; i++){
            Integer data = random.nextInt();
            System.out.println("Adding -> "+i+ " : "+data);
            map.put(i, data);

        }
    }

    public static void read(){
        Map<Integer, Integer> map = getMap(DEFAULT);
        for (Map.Entry<Integer, Integer> entry :map.entrySet()){
            System.out.println(entry.getKey()+ " : "+entry.getValue());
        }
    }

}

enum MapType{
    BASIC,
    CONCURRENT,
    DISTRIBUTED
}
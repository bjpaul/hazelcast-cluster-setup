package com.hazelcast.basic;

import java.util.Map;

/**
 * Created by bijoypaul on 24/04/17.
 */
public class UserActivity {

    private final Integer id;
    private final Map<Integer, Integer> map;

    public UserActivity(Integer id, MapType type){
        this.id = id;
        map = MapStore.getMap(type);
    }

    public void start() throws InterruptedException {
        Thread t1 = like();
        Thread t2 = dislike();

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Completed....");
        likeCount();
    }

    public void likeCount(){
        System.out.println("Like count for id "+id+" is : "+map.get(id));
    }

    private Thread like(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    map.put(id, map.get(id) + 1);
                }
            }
        });
        return thread;
    }

    private Thread dislike(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    map.put(id, map.get(id) - 1);
                }
            }
        });
        return thread;
    }


}

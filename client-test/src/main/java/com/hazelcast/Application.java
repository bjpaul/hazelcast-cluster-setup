package com.hazelcast;

import com.hazelcast.basic.MapStore;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.ConnectionConfig;
import com.hazelcast.connection.Client;
import com.hazelcast.connection.Member;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.partition.PartitionAware;

import java.util.Map;

/**
 * Created by bijoypaul on 23/04/17.
 */
public class Application {

    public static void main(String[] args) {
        String action = System.getProperty("start") != null? System.getProperty("start"):"client";
        Map<Integer, Integer> map;

        switch (action){
            case "basic":
                            HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
                            map = hazelcastInstance.getMap("testMap");
                            map.put(1,1);
                            System.out.println("============>  "+map.get(1));
                            break;

            case "basic-add":
                            MapStore.add();
                            break;

            case "basic-read":
                            MapStore.read();
                            break;

            case "basic-member":
                            Hazelcast.newHazelcastInstance(ConnectionConfig.MEMBER);
                            break;

            case "basic-client":
                            HazelcastClient.newHazelcastClient(ConnectionConfig.CLIENT);
                            break;

            case "client-wr": // client with retry connection attempt
                            HazelcastClient.newHazelcastClient(ConnectionConfig.CLIENT_WITH_RETRY_CONN_ATTEMPT);
                            break;

            case "client": // client with life cycle
                            Client.start();
                            break;

            case "member-pa": // cluster partition aware
                            PartitionAware.listen();
                            break;

        }

    }
}

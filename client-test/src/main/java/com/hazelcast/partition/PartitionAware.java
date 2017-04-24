package com.hazelcast.partition;

import com.hazelcast.config.ConnectionConfig;
import com.hazelcast.connection.Member;
import com.hazelcast.core.*;
import com.hazelcast.nio.Address;

import java.util.Map;
import java.util.UUID;

/**
 * Created by bijoypaul on 23/04/17.
 */
public class PartitionAware implements MigrationListener {

    public static void listen(){
        Member.start();
        Member.INSTANCE.getPartitionService().addMigrationListener(new PartitionAware(Member.INSTANCE));

        Map<Integer, String> map = Member.INSTANCE.getMap("test");
        map.put(1, UUID.randomUUID().toString());

        System.out.println("-----------------------------");
        StringBuilder sb = new StringBuilder();
        Partition partition = Member.INSTANCE.getPartitionService().getPartition(1);
        Address address = partition.getOwner().getAddress();
        sb.append("{ [key -> ");
        sb.append(1);
        sb.append("] : [value -> ");
        sb.append(map.get(1));
        sb.append("] : [partition id -> ");
        sb.append(partition.getPartitionId());
        sb.append("] : [owner -> <");
        sb.append(" Instance ");
        sb.append(address.getPort() % 10);
        sb.append(">]");
        sb.append(" }");
        System.out.println(sb);
    }

    PartitionAware(HazelcastInstance hazelcastInstance){
        Member.INSTANCE = hazelcastInstance;
    }

    @Override
    public void migrationStarted(MigrationEvent migrationEvent) {
        System.out.println("migrationStarted -> "+migrationEvent);
    }

    @Override
    public void migrationCompleted(MigrationEvent migrationEvent) {
        System.out.println("migrationCompleted -> "+migrationEvent);
        for(Partition partition: Member.INSTANCE.getPartitionService().getPartitions()){
            System.out.println("partition id : "+partition.getPartitionId()+", owner : "+partition.getOwner());
        }
    }

    @Override
    public void migrationFailed(MigrationEvent migrationEvent) {
        System.out.println("migrationFailed"+migrationEvent);
    }
}

package com.hazelcast.connection;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by bijoypaul on 23/04/17.
 */
public class ConnectionFactory {

    private static final String CLUSTER_GROUP_NAME = "sandbox";
    private static final String CLUSTER_GROUP_PASSWORD = "test";
    private static final List<String> ADDRESS_LIST = new ArrayList<>(Arrays.asList("localhost"));

    public static HazelcastInstance newMember(){
        Config memberConfig = new Config();

        // setting network configuration
        NetworkConfig networkConfig = memberConfig.getNetworkConfig();
        JoinConfig join = networkConfig.getJoin();

        // Disabling multi-cast auto discovery
        join.getMulticastConfig().setEnabled( false );

        // setting TCP/IP configuration
        join.getTcpIpConfig()
                .setEnabled(true)
                .setMembers(ADDRESS_LIST);

        // setting cluster group information
        memberConfig.getGroupConfig()
                .setName(CLUSTER_GROUP_NAME)
                .setPassword(CLUSTER_GROUP_PASSWORD);

        setInstanceName(memberConfig);

        return Hazelcast.newHazelcastInstance(memberConfig);
    }

    public static HazelcastInstance newClient(){
        ClientConfig clientConfig = new ClientConfig();

        // setting network configuration
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.setAddresses(ADDRESS_LIST);

        // setting cluster group information
        clientConfig.getGroupConfig()
                .setName(CLUSTER_GROUP_NAME)
                .setPassword(CLUSTER_GROUP_PASSWORD);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    private static void setInstanceName(Config config ){

        String hostname = null;
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            hostname += " - "+addr.getHostAddress();
        }
        catch (UnknownHostException ex){}

        if(hostname != null){
            config.setInstanceName(hostname);
        }
    }

}

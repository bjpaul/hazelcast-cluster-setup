package com.hazelcast.config;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bijoypaul on 23/04/17.
 */
public class ConnectionConfig {

    private static final String CLUSTER_GROUP_NAME = "sandbox";
    private static final String CLUSTER_GROUP_PASSWORD = "test";
    private static final List<String> ADDRESS_LIST = new ArrayList<>(Arrays.asList("localhost"));
    public static final Config MEMBER = memberConfig();
    public static final Config MEMBER_WITH_MANCENTER = manCenterConfig();
    public static final ClientConfig CLIENT = clientConfig();
    public static final ClientConfig CLIENT_WITH_RETRY_CONN_ATTEMPT = clientConfigRetryAttempt();

    private static Config memberConfig(){
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
        return memberConfig;
    }

    private static ClientConfig clientConfig(){
        ClientConfig clientConfig = new ClientConfig();

        // setting network configuration
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.setAddresses(ADDRESS_LIST);

        // setting cluster group information
        clientConfig.getGroupConfig()
                .setName(CLUSTER_GROUP_NAME)
                .setPassword(CLUSTER_GROUP_PASSWORD);
        return clientConfig;
    }

    private static Config manCenterConfig(){
        Config config = memberConfig();
        config.getManagementCenterConfig().setEnabled(true);
        config.getManagementCenterConfig().setUrl("http://localhost:8080/mancenter");
        return config;
    }


    private static ClientConfig clientConfigRetryAttempt(){
        ClientConfig clientConfig = clientConfig();
        clientConfig.getNetworkConfig()
                .setConnectionAttemptLimit(5) // by default 2
                .setConnectionAttemptPeriod(4000) // by default 3000 ms
                .setConnectionTimeout(6000) // by default 5000 ms
                .setSmartRouting(true); // by default true
        return clientConfig;
    }



}


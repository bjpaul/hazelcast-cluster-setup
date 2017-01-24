package com.hazelcast.client;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;

public class Application {

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig()
                .setName("sandbox")      // passing the cluster group username
                .setPassword("test");    // passing the cluster group password
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.addAddress("localhost");   // passing the server address
//        networkConfig.addAddress("host2");   // passing the server address
//        networkConfig.addAddress("host3");   // passing the server address
		HazelcastClient.newHazelcastClient(clientConfig);
	}
}

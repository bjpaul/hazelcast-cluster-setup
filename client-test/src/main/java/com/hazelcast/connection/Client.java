package com.hazelcast.connection;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.ConnectionConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.core.LifecycleEvent.LifecycleState;
import com.hazelcast.core.LifecycleListener;


/**
 * Created by bijoypaul on 24/04/17.
 */
public class Client {

    public static HazelcastInstance INSTANCE;

    public static void start(){
        while (INSTANCE == null || !INSTANCE.getLifecycleService().isRunning()){
            try {
                INSTANCE = HazelcastClient.newHazelcastClient(ConnectionConfig.CLIENT_WITH_RETRY_CONN_ATTEMPT);
                INSTANCE.getLifecycleService().addLifecycleListener(new ClientLifeCycleListener());
            }catch (Exception ex){
                System.out.println("Exception "+ex.getMessage());
            }
        }
    }
    // NOTE : Try to run the above code with out any exception handling

    private static class ClientLifeCycleListener implements LifecycleListener{

        @Override
        public void stateChanged(LifecycleEvent event) {
            System.out.println("Client state changed to -> "+event.getState());
            LifecycleState state = event.getState();
            if(state.equals(LifecycleState.SHUTTING_DOWN) || state.equals(LifecycleState.SHUTDOWN)){
                start();
            }
        }
    }
}



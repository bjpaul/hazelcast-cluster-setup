package com.hazelcast.connection;

import com.hazelcast.config.ConnectionConfig;
import com.hazelcast.core.*;

/**
 * Created by bijoypaul on 24/04/17.
 */
public class Member {

    public static HazelcastInstance INSTANCE ;

    public static void start(){
        INSTANCE = Hazelcast.newHazelcastInstance(ConnectionConfig.MEMBER_WITH_MANCENTER);
        INSTANCE.getLifecycleService().addLifecycleListener(new MemberLifeCycleListener());
    }


    private static class MemberLifeCycleListener implements LifecycleListener {

        @Override
        public void stateChanged(LifecycleEvent event) {
            System.out.println("Member state changed to -> "+event.getState());
        }
    }
}

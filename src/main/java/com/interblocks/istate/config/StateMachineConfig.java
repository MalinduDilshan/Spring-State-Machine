package com.interblocks.istate.config;

import com.interblocks.istate.events.Events;
import com.interblocks.istate.states.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(
            StateMachineConfigurationConfigurer
                    <States, Events> config) throws Exception {

        config.withConfiguration()
                .autoStartup(false)
                .machineId("iWallet");
    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.REGISTER)
                .state(States.LINKCARD)
                .end(States.LOGIN)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.REGISTER).target(States.LINKCARD)
                .event(Events.LINKCARD)
                .and()
                .withExternal()
                .source(States.LINKCARD).target(States.LOGIN)
                .event(Events.LOGIN)
                .and()
                .withExternal()
                .source(States.REGISTER).target(States.LOGIN)
                .event(Events.LOGIN);
    }
}


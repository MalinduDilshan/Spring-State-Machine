package com.interblocks.istate.config;

import com.interblocks.istate.events.Events;
import com.interblocks.istate.states.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class MyStateMachineBuilder {

    @Bean
    @Scope(scopeName="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
    public StateMachine<States, Events> buildMachine() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.REGISTER)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
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

        return builder.build();
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("User Registration State change from " + from.getId() + " to " + to.getId());
            }
        };
    }
}

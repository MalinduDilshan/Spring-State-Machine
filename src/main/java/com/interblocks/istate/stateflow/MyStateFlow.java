package com.interblocks.istate.stateflow;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine
public class MyStateFlow {

    @OnTransition(target = "REGISTER")
    public void register() {
        System.out.println("Register User");
    }

    @OnTransition(target = "LINKCARD")
    public void linkCard() {
        System.out.println("Link E Card");
    }

    @OnTransition(target = "LOGIN")
    public void login() {
        System.out.println("User Login");
    }
}


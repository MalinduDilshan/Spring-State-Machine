package com.interblocks.istate.controller;

import com.interblocks.istate.entity.UserState;
import com.interblocks.istate.events.Events;
import com.interblocks.istate.repository.UserStateRepository;
import com.interblocks.istate.states.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/state")
public class MyController {

    @Value("#{'${states}'.split(',')}")
    private List<String> workflow;

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private UserStateRepository userStateRepository;

    @GetMapping("/workflow")
    public String getWorkflow() {
        workflow.stream().forEach(s -> {
            System.out.println("Work Flow :"+s);
        });
        return workflow.toString();
    }

    @GetMapping("/register")
    public String getStart() {
        stateMachine.start();
        System.out.println("Current State :" + stateMachine.getState().getId());
        return stateMachine.getState().getId().toString();
    }

    @GetMapping("/link/{id}")
    public String linkCard(@PathVariable Long id) {
        stateMachine.sendEvent(Events.LINKCARD);
        System.out.println("Current State :" + stateMachine.getState().getId());
        return stateMachine.getState().getId().toString();
    }

    @GetMapping("/login/{id}")
    public String login(@PathVariable Long id) {
        stateMachine.sendEvent(Events.LOGIN);
        System.out.println("Current State :" + stateMachine.getState().getId());
        return stateMachine.getState().getId().toString();
    }

    @GetMapping("/linkLogin/{id}")
    public String fullFlow(@PathVariable Long id) {

        stateMachine.start();
        System.out.println("Current State :" + stateMachine.getState().getId());

        stateMachine.sendEvent(Events.LINKCARD);
        System.out.println("Current State :" + stateMachine.getState().getId());

        stateMachine.sendEvent(Events.LOGIN);
        System.out.println("Current State :" + stateMachine.getState().getId());

        stateMachine.stop();
        return stateMachine.getState().getId().toString();
    }

}

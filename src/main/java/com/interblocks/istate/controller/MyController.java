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

    @GetMapping("/start")
    public String getStart() {

        stateMachine.start();
        System.out.println("Current State :" + stateMachine.getState().getId());
        return save(null).getUserId().toString();
    }

    @GetMapping("/get/{id}")
    public String getState(@PathVariable Long id) {

        UserState userState = userStateRepository.findById(id).get();
        System.out.println("Restore State :"+userState.toString());
        return userState.toString();
    }

    @GetMapping("/link/{id}")
    public String linkCard(@PathVariable Long id) {

        UserState userState = userStateRepository.findById(id).get();
        userState = save(userState);
        stateMachine.sendEvent(Events.LINKCARD);
        System.out.println("Current State :" + userState.getState());
        return userState.toString();
    }

    @GetMapping("/login/{id}")
    public String login(@PathVariable Long id) {

        UserState userState = userStateRepository.findById(id).get();
        userState = save(userState);
        stateMachine.sendEvent(Events.LOGIN);
        System.out.println("Current State :" + userState.getState());
        return userState.toString();
    }

    @GetMapping("/linkLogin/{id}")
    public String fullFlow(@PathVariable Long id) {

        UserState userState = userStateRepository.findById(id).get();
        UserState previousStateUser = save(userState);

        if(previousStateUser.getState().equals(States.REGISTER)){
            stateMachine.sendEvent(Events.LINKCARD);
            previousStateUser = save(userState);
            System.out.println("Current State :" + previousStateUser.getState());
        }
        else if(previousStateUser.getState().equals(States.LINKCARD)){
            stateMachine.sendEvent(Events.LOGIN);
            previousStateUser = save(userState);
            System.out.println("Current State :" + previousStateUser.getState());
        }
        else{
            System.out.println("Current State (END) :" + previousStateUser.getState());
        }
        return userState.toString();
    }


    private UserState save(UserState userState){

        UserState savedUserState = null;
       if(userState != null){
//           stateMachine.stop();
//           stateMachine.start();
           //Restore the previous state
           savedUserState = userStateRepository.findById(userState.getUserId()).get();
           savedUserState.setState(stateMachine.getState().getId().toString());
           savedUserState.setUuid(stateMachine.getUuid().toString());
           savedUserState.setModifiedDate(LocalDate.now());
           return userStateRepository.save(savedUserState);
       }
       else{
           stateMachine.stop();
           stateMachine.start();
           savedUserState = new UserState();
           savedUserState.setState(stateMachine.getState().getId().toString());
           savedUserState.setUuid(stateMachine.getUuid().toString());
           savedUserState.setModifiedDate(LocalDate.now());
           return userStateRepository.save(savedUserState);
       }
    }

}

package com.interblocks.istate.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "userstate")
@Data
public class UserState {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "state")
    private String state;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "modifiededate")
    private LocalDate modifiedDate;

}

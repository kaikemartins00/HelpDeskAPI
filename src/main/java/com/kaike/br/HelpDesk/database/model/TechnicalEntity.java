package com.kaike.br.HelpDesk.database.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "technical")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String specialty;

    @Getter
    @OneToMany(mappedBy = "technical")
    private Set<CalledEntity> called = new HashSet<>();

}

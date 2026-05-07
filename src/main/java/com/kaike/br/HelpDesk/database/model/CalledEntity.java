package com.kaike.br.HelpDesk.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "called")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private CalledPriority priority;
    @Enumerated(EnumType.STRING)
    private CalledStatus status;
    private Instant openingDate;
    @PrePersist
    public void prePersist() {
        if (this.openingDate == null) {
            this.openingDate = Instant.now();
        }
        if (this.status == null) {
            this.status = CalledStatus.OPEN;
        }
    }
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technical_id")
    private TechnicalEntity technical;
}

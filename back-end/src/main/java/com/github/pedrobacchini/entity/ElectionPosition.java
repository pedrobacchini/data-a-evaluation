package com.github.pedrobacchini.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "election_positions")
@NoArgsConstructor //For Hibernate
@EqualsAndHashCode(of = {"uuid"})
public class ElectionPosition implements Serializable {

    private static final long serialVersionUID = -6857868890175340871L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    public ElectionPosition(String name) { this.name = name; }
}

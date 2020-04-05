package com.github.pedrobacchini.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    @NotNull
    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false )
    @JsonIgnore
    @JoinColumn(name = "election_uuid")
    private Election election;

    @OneToMany(mappedBy = "electionPosition", fetch = FetchType.EAGER)
    private List<Candidate> candidates = new ArrayList<>();

    public ElectionPosition(UUID uuid) { this.uuid = uuid; }

    public ElectionPosition(String name) { this.name = name; }

    public ElectionPosition(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}

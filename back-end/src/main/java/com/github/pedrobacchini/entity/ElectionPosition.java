package com.github.pedrobacchini.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.json.View;
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
@Setter
@ToString
@Table(name = "election_positions")
@NoArgsConstructor //For Hibernate
@EqualsAndHashCode(of = {"uuid"})
public class ElectionPosition implements Serializable {

    private static final long serialVersionUID = -6857868890175340871L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JsonView({View.Election.class, View.Candidate.class})
    private UUID uuid;

    @NotNull
    @Column(nullable = false, length = 100)
    @JsonView({View.Election.class, View.Candidate.class})
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "election_uuid")
    @JsonView(View.Candidate.class)
    private Election election;

    @JsonView(View.Election.class)
    @OneToMany(mappedBy = "electionPosition", fetch = FetchType.EAGER)
    private final List<Candidate> candidates = new ArrayList<>();

    public ElectionPosition(UUID uuid) { this.uuid = uuid; }

    public ElectionPosition(String name) { this.name = name; }

    public ElectionPosition(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}

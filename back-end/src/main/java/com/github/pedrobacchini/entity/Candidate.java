package com.github.pedrobacchini.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "candidates")
@NoArgsConstructor //For Hibernate
@EqualsAndHashCode(of = {"uuid"})
public class Candidate implements Serializable {

    private static final long serialVersionUID = 4955187352980432776L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @JsonIgnore
    @Setter
    @ManyToOne
    @JoinColumn(name = "election_position_uuid")
    private ElectionPosition electionPosition;

    public Candidate(String name, ElectionPosition electionPosition) {
        this.name = name;
        this.electionPosition = electionPosition;
    }
}

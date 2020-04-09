package com.github.pedrobacchini.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.BackEndApplication;
import com.github.pedrobacchini.config.BackEndProperty;
import com.github.pedrobacchini.json.View;
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
    @JsonView({View.Candidate.class, View.Election.class})
    private UUID uuid;

    @Setter
    @Column(nullable = false, length = 100)
    @JsonView({View.Candidate.class, View.Election.class})
    private String name;

    @Setter
    @ManyToOne
    @JsonView(View.Candidate.class)
    @JoinColumn(name = "election_position_uuid")
    private ElectionPosition electionPosition;

    public Candidate(String name, ElectionPosition electionPosition) {
        this.name = name;
        this.electionPosition = electionPosition;
    }

    @JsonView({View.Candidate.class, View.Election.class})
    public String getPicture() {
        BackEndProperty backEndProperty = BackEndApplication.getBean(BackEndProperty.class);
        return backEndProperty.getImage().getCandidate().getBaseUrl() + uuid + ".jpg";
    }
}

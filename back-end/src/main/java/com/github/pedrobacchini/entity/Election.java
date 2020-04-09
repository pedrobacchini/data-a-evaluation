package com.github.pedrobacchini.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.json.View;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@ToString
@Table(name = "elections")
@NoArgsConstructor //For Hibernate
@EqualsAndHashCode(of = {"uuid"})
public class Election implements Serializable {

    private static final long serialVersionUID = -3822373709321158227L;

    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JsonView({View.Election.class, View.Candidate.class})
    private UUID uuid;

    @Getter
    @Setter
    @Column(nullable = false, length = 100)
    @JsonView({View.Election.class, View.Candidate.class})
    private String name;

    @Getter
    @Setter
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false, name = "start_date")
    @JsonView({View.Election.class, View.Candidate.class})
    private LocalDate startDate;

    @Getter
    @Setter
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false, name = "finish_date")
    @JsonView({View.Election.class, View.Candidate.class})
    private LocalDate finishDate;

    @JsonView(View.Election.class)
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<ElectionPosition> electionPositions = new ArrayList<>();

    public Election(String name, LocalDate startDate, LocalDate finishDate) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public void addElectionPosition(ElectionPosition electionPosition) {
        this.electionPositions.add(electionPosition);
        electionPosition.setElection(this);
    }

    public void addAllElectionPositions(List<ElectionPosition> electionPositions) {
        this.electionPositions.addAll(electionPositions);
        electionPositions.forEach(electionPosition -> electionPosition.setElection(this));
    }

    public List<ElectionPosition> getElectionPositions() { return Collections.unmodifiableList(this.electionPositions); }

    public void updateData(Election election) {
        BeanUtils.copyProperties(election, this);
        List<ElectionPosition> removedItems = new ArrayList<>(this.electionPositions);
        election.getElectionPositions().forEach(itemUpdated -> {
            Optional<ElectionPosition> optionalItem = removedItems.stream()
                    .filter(item -> item.getUuid().equals(itemUpdated.getUuid()))
                    .findAny();
            if (optionalItem.isPresent()) {
                ElectionPosition itemFound = optionalItem.get();
                BeanUtils.copyProperties(itemUpdated, itemFound);
                removedItems.remove(itemFound);
            }
        });
        this.electionPositions.removeAll(removedItems);
        List<ElectionPosition> newItems = election.getElectionPositions()
                .stream().filter(item -> item.getUuid() == null)
                .collect(Collectors.toList());
        this.electionPositions.addAll(newItems);
        this.electionPositions.forEach(item -> item.setElection(this));
    }
}

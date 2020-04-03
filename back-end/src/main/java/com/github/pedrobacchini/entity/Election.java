package com.github.pedrobacchini.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@ToString
@NoArgsConstructor //For Hibernate
@EqualsAndHashCode(of = {"uuid"})
public class Election implements Serializable {

    private static final long serialVersionUID = -3822373709321158227L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Setter
    @Column(nullable = false, name = "finish_date")
    private LocalDate finishDate;

    public Election(String name, LocalDate startDate, LocalDate finishDate) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
}

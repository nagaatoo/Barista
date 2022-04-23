package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "unit_news")
@Getter
@Setter
public class UnitNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_news_generator")
    @SequenceGenerator(name = "unit_news_generator", sequenceName = "unit_news_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

}
package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rang")
@Getter
@Setter
public class RangEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rang_generator")
    @SequenceGenerator(name = "rang_generator", sequenceName = "rang_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;
}

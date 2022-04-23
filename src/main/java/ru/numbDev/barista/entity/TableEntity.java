package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "unit_table")
@Getter
@Setter
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_table_generator")
    @SequenceGenerator(name = "unit_table_generator", sequenceName = "unit_table_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "count")
    private Integer count;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;
}

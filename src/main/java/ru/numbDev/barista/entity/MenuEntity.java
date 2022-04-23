package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_generator")
    @SequenceGenerator(name = "menu_generator", sequenceName = "menu_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

    @OneToMany(mappedBy = "menu")
    @Fetch(FetchMode.SUBSELECT)
    private List<DishEntity> dish = new ArrayList<>();

}

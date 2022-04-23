package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish")
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_generator")
    @SequenceGenerator(name = "dish_generator", sequenceName = "dish_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "cost")
    private Float cost;

    @OneToMany(mappedBy = "dish")
    @Fetch(FetchMode.SUBSELECT)
    private List<FileMetaEntity> fileMeta = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private MenuEntity menu;
}

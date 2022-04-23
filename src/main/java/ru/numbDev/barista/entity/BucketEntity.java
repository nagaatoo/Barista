package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bucket")
@Getter
@Setter
public class BucketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bucket_generator")
    @SequenceGenerator(name = "bucket_generator", sequenceName = "bucket_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "count", nullable = false, columnDefinition = "1")
    private Integer count;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private UserEntity client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private DishEntity dish;

}

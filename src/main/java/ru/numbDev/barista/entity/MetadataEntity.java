package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "metadata")
@Getter
@Setter
public class MetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_generator")
    @SequenceGenerator(name = "metadata_generator", sequenceName = "metadata_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "description")
    private String description;

}

package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file_meta")
@Getter
@Setter
public class FileMetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_file_generator")
    @SequenceGenerator(name = "metadata_file_generator", sequenceName = "file_meta_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private MetadataEntity metadata;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_news_id", referencedColumnName = "id")
    private UnitNewsEntity news;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private DishEntity dish;
}

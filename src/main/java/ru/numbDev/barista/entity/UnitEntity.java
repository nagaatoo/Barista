package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit")
@Getter
@Setter
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_generator")
    @SequenceGenerator(name = "unit_generator", sequenceName = "unit_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_unit")
    private String ownerUnit;

    @Column(name = "ogrn")
    private String ogrn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private WalletEntity wallet;

    @OneToMany(mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<RangEntity> rang = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meta_id", referencedColumnName = "id")
    private MetadataEntity metadata;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id")
    private CoordinateEntity coordinate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<FileMetaEntity> pics = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderClientEntity> ordersClients = new ArrayList<>();

    @OneToMany(mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<UnitNewsEntity> unitNews = new ArrayList<>();

    @OneToMany(mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<TableEntity> tables = new ArrayList<>();

    @OneToMany(mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    @Fetch(FetchMode.SUBSELECT)
    private List<MenuEntity> menus = new ArrayList<>();
}

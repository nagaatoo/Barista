package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "coordinate")
@Getter
@Setter
public class CoordinateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordinate_generator")
    @SequenceGenerator(name = "coordinate_generator", sequenceName = "coordinate_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "fias_code")
    private String fiasCode;
}

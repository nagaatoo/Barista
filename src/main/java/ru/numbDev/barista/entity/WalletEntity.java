package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Getter
@Setter
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_generator")
    @SequenceGenerator(name = "wallet_generator", sequenceName = "wallet_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @OneToOne(mappedBy = "wallet")
    private UnitEntity unit;

}

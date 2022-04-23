package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment_info")
@Getter
@Setter
public class PaymentInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_info_generator")
    @SequenceGenerator(name = "payment_info_generator", sequenceName = "payment_info_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "is_payment", nullable = false, columnDefinition = "boolean default false")
    private boolean isPayment;

    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
}

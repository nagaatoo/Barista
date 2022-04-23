package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.numbDev.barista.OrderStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_client")
@Getter
@Setter
public class OrderClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_client_generator")
    @SequenceGenerator(name = "order_client_generator", sequenceName = "order_client_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "is_done", nullable = false)
    private Boolean isDone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "to_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private UserEntity client;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentInfoEntity paymentInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderClient", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderClientDishEntity> clientDishes = new ArrayList<>();
}

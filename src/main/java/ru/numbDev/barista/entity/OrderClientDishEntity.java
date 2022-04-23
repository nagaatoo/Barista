package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_client_dish")
@Getter
@Setter
public class OrderClientDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_client_basket_generator")
    @SequenceGenerator(name = "order_client_basket_generator", sequenceName = "order_basket_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderClientEntity orderClient;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private DishEntity dish;
}

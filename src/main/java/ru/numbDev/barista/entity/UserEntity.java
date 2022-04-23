package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.numbDev.barista.pojo.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "fio")
    private String fio;

    @Column(name = "created")
    private Date created;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "isDisabled")
    private Boolean isDisabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderClientEntity> ordersClients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Fetch(FetchMode.SUBSELECT)
    private List<BucketEntity> bucket = new ArrayList<>();

}

package ru.numbDev.barista.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.numbDev.barista.pojo.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class RoleEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
//    @SequenceGenerator(name = "role_generator", sequenceName = "role_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private Role code;

    @Column(name = "descr")
    private String descr;

}

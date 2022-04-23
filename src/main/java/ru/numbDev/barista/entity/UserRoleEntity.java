package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_generator")
    @SequenceGenerator(name = "user_role_generator", sequenceName = "user_role_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "unit_id")
    private Long unitId;
}


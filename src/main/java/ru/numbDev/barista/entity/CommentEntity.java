package ru.numbDev.barista.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "comment_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;
}

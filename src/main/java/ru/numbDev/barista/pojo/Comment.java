package ru.numbDev.barista.pojo;

import lombok.Data;
import ru.numbDev.barista.entity.CommentEntity;

@Data
public class Comment {

    private Long unitId;
    private int rating;
    private String comment;

    public Comment(CommentEntity entity) {

    }
}

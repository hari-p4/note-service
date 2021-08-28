package com.disqo.noteservice.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Haridas Parekh
 * Entity class for Notes
 */
@Entity
@Table(name = "notes")
@Getter
@Setter
@ToString
public class NotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "note")
    private String note;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}

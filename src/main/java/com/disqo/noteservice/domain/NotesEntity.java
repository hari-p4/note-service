package com.disqo.noteservice.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    /**
     * title cannot be blank and null with max size of note is 50 characters
     */
    @NonNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * max size of note is 1000 characters
     */
    @Size(max = 1000)
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

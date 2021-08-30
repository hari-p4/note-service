package com.disqo.noteservice.domain;

import com.disqo.noteservice.dto.NotesResponseDto;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

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
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "last_update_time")
    @UpdateTimestamp
    private Timestamp lastUpdateTime;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * Construct a NotesResponseDto for this entity
     *
     * @return notesResponseDto for users
     */
    public NotesResponseDto toDto() {

        NotesResponseDto notesResponseDto = new NotesResponseDto();
        notesResponseDto.setNoteId(id);
        notesResponseDto.setTitle(title);
        return notesResponseDto;

    }

}

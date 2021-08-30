package com.disqo.noteservice.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto class to create request for CRUD operations on Notes.
 *
 * @author Haridas Parekh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NotesRequestDto {

    @NonNull
    @NotBlank
    @Size(max = 50)
    private String title;

    @Size(max = 1000)
    @Column(name = "note")
    private String note;
}

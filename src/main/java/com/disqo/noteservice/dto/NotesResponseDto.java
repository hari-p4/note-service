package com.disqo.noteservice.dto;

import lombok.*;

/**
 * Dto class to populate response after CRUD operations on Notes.
 *
 * @author Haridas Parekh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NotesResponseDto {

    private Integer noteId;

    private String title;

    private String successMessage;

    private String errorMessage;
}

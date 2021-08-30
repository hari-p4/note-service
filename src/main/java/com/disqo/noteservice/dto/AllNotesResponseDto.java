package com.disqo.noteservice.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * Dto class to return All Notes.
 *
 * @author Haridas Parekh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AllNotesResponseDto {

    private Integer noteId;

    private String title;

    private String note;

    private Timestamp createdTime;

    private Timestamp lastUpdatedTime;
}

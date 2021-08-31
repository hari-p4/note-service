package com.disqo.noteservice.service;

import com.disqo.noteservice.dto.AllNotesResponseDto;
import com.disqo.noteservice.dto.NotesRequestDto;
import com.disqo.noteservice.dto.NotesResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * Service interface to process and CRUD notes.
 *
 * @author Haridas Parekh
 */
public interface NotesService {

    /**
     * Create note
     *
     * @param notesRequestDto
     * @param user
     * @return
     */
    ResponseEntity<NotesResponseDto> createNote(NotesRequestDto notesRequestDto, User user);

    /**
     * Get all Notes
     *
     * @param user
     * @return
     */
    ResponseEntity<List<AllNotesResponseDto>> getAllNotes(User user);

    /**
     * Delete note by Id
     *
     * @param noteId
     * @param user
     * @return
     */
    ResponseEntity<NotesResponseDto> deleteNoteById(Integer noteId, User user);
}

package com.disqo.noteservice.service;

import com.disqo.noteservice.domain.NotesEntity;
import com.disqo.noteservice.dto.AllNotesResponseDto;
import com.disqo.noteservice.dto.NotesRequestDto;
import com.disqo.noteservice.dto.NotesResponseDto;
import com.disqo.noteservice.repositories.NotesRepository;
import com.disqo.noteservice.repositories.UserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation to process and CRUD notes.
 *
 * @author Haridas Parekh
 */
@Service
@Transactional
@CommonsLog
public class NotesServiceImpl implements NotesService {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;

    private static final String SUCCESS_MESSAGE = "Note saved/updated successfully";
    private static final String DELETE_SUCCESS_MESSAGE = "Note successfully deleted";
    private static final String DELETE_FAILURE_MESSAGE = "Could not find a Note / User not allowed to delete";

    /**
     * Creates and updates existing note based on user
     *
     * @param notesRequestDto note request got from user.
     * @param user            current logged in user trying to add/update note.
     * @return response
     */
    @Override
    public ResponseEntity<NotesResponseDto> createNote(NotesRequestDto notesRequestDto, User user) {
        NotesEntity note = getNote(notesRequestDto);
        if (ObjectUtils.isEmpty(note)) {
            log.info("Adding a new Note...");
            note = new NotesEntity();
        } else {
            log.info("Updating the note...");
        }
        log.info("Note saved successfully...");
        NotesResponseDto response = populateAndSaveNoteData(note, notesRequestDto, user).toDto();
        response.setSuccessMessage(SUCCESS_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get list of notes restricted to current logged in user
     *
     * @param user current logged in user
     * @return list of Notes belongs to user
     */
    @Override
    public ResponseEntity<List<AllNotesResponseDto>> getAllNotes(User user) {
        return new ResponseEntity<>(notesRepository.findByUserId(getCurrentLoggedInUserId(user))
                .stream().map(note -> new AllNotesResponseDto(note.getId(), note.getTitle(), note.getNote(), note.getCreateTime(), note.getLastUpdateTime()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Delete not by given Id once it was found from db and
     * it's userId matches with current logged in userId and return error message otherwise
     *
     * @param noteId id given by user
     * @param user   current logged in user
     * @return
     */
    @Override
    public ResponseEntity<NotesResponseDto> deleteNoteById(Integer noteId, User user) {
        Optional<NotesEntity> noteForDelete = notesRepository.findById(noteId);
        if (noteForDelete.isPresent() && noteForDelete.get().getUserId().equals(getCurrentLoggedInUserId(user))) {
            notesRepository.delete(noteForDelete.get());
            return new ResponseEntity<>(generateNotesResponseDto(noteId, DELETE_SUCCESS_MESSAGE, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(generateNotesResponseDto(noteId, DELETE_FAILURE_MESSAGE, false), HttpStatus.OK);
        }
    }

    /**
     * Gets userId from users table
     *
     * @param user
     * @return
     */
    private Integer getCurrentLoggedInUserId(User user) {
        return userRepository.findByEmail(user.getUsername()).getId();
    }

    /**
     * Populates the data request to entity and save in database
     *
     * @param note
     * @param notesRequestDto
     * @param user
     * @return
     */
    private NotesEntity populateAndSaveNoteData(NotesEntity note, NotesRequestDto notesRequestDto, User user) {
        note.setTitle(notesRequestDto.getTitle());
        note.setNote(notesRequestDto.getNote());
        note.setUserId(getCurrentLoggedInUserId(user));
        return notesRepository.save(note);
    }

    /**
     * Gets note by it's title
     *
     * @param noteRequest
     * @return
     */
    private NotesEntity getNote(NotesRequestDto noteRequest) {
        return notesRepository.findByTitle(noteRequest.getTitle());
    }

    /**
     * Generates a response dto after deleting the note
     *
     * @param noteId
     * @param message
     * @param isSuccessful
     * @return
     */
    private NotesResponseDto generateNotesResponseDto(Integer noteId, String message, boolean isSuccessful) {
        if (isSuccessful) {
            return new NotesResponseDto(noteId, null, message, null);
        }
        return new NotesResponseDto(noteId, null, null, message);
    }
}

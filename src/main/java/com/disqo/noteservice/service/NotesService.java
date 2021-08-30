package com.disqo.noteservice.service;

import com.disqo.noteservice.domain.NotesEntity;
import com.disqo.noteservice.dto.AllNotesResponseDto;
import com.disqo.noteservice.dto.NotesRequestDto;
import com.disqo.noteservice.dto.NotesResponseDto;
import com.disqo.noteservice.repositories.NotesRepository;
import com.disqo.noteservice.repositories.UserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to process and CRUD notes.
 *
 * @author Haridas Parekh
 */
@Service
@Transactional
@CommonsLog
public class NotesService {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;

    private static final String SUCCESS_MESSAGE = "Note saved/updated successfully";

    /**
     * Add or Update note based on user
     *
     * @param notesRequestDto note request got from user.
     * @param user            current logged in user trying to add/update note.
     * @return response
     */
    public NotesResponseDto addOrUpdateNotes(NotesRequestDto notesRequestDto, User user) {
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
        return response;
    }

    /**
     * Get list of notes restricted to current logged in user
     *
     * @param user current logged in user
     * @return list of Notes belongs to user
     */
    public List<AllNotesResponseDto> getUserNotes(User user) {
        return notesRepository.findByUserId(getCurrentLoggedInUserId(user))
                .stream().map(note -> new AllNotesResponseDto(note.getId(), note.getTitle(), note.getNote(), note.getCreateTime(), note.getLastUpdateTime()))
                .collect(Collectors.toList());
    }

    private Integer getCurrentLoggedInUserId(User user) {
        return userRepository.findByEmail(user.getUsername()).getId();
    }

    private NotesEntity populateAndSaveNoteData(NotesEntity note, NotesRequestDto notesRequestDto, User user) {
        note.setTitle(notesRequestDto.getTitle());
        note.setNote(notesRequestDto.getNote());
        note.setUserId(getCurrentLoggedInUserId(user));
        return notesRepository.save(note);
    }

    private NotesEntity getNote(NotesRequestDto noteRequest) {
        return notesRepository.findByTitle(noteRequest.getTitle());
    }
}

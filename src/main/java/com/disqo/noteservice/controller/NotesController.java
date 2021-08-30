package com.disqo.noteservice.controller;

import com.disqo.noteservice.dto.AllNotesResponseDto;
import com.disqo.noteservice.dto.NotesRequestDto;
import com.disqo.noteservice.dto.NotesResponseDto;
import com.disqo.noteservice.service.NotesService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class to handle CRUD operations on Notes.
 *
 * @author Haridas Parekh
 */
@RestController
@RequestMapping("/api/v1.0/notes")
@CommonsLog
public class NotesController {

    @Autowired
    NotesService notesService;

    private static final String ERROR_MESSAGE = "Something went wrong while saving or update the Note!!";

    @PostMapping
    @ResponseBody
    public NotesResponseDto createNote(@Valid @RequestBody NotesRequestDto noteRequest, BindingResult bindingResult, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (bindingResult.hasErrors()) {
            return new NotesResponseDto(null, null, null, ERROR_MESSAGE);
        }
        return notesService.addOrUpdateNotes(noteRequest, user);
    }

    @GetMapping
    public List<AllNotesResponseDto> getNoteById(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return notesService.getUserNotes(user);
    }
}

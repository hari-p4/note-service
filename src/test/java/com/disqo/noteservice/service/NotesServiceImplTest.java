package com.disqo.noteservice.service;

import com.disqo.noteservice.domain.NotesEntity;
import com.disqo.noteservice.domain.UserEntity;
import com.disqo.noteservice.dto.AllNotesResponseDto;
import com.disqo.noteservice.dto.NotesRequestDto;
import com.disqo.noteservice.dto.NotesResponseDto;
import com.disqo.noteservice.repositories.NotesRepository;
import com.disqo.noteservice.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotesServiceImplTest {

    @InjectMocks
    NotesServiceImpl notesServiceImpl;

    @Mock
    NotesRepository notesRepository;

    @Mock
    UserRepository userRepository;

    private static final String DELETE_SUCCESS_MESSAGE = "Note successfully deleted";
    private static final String DELETE_FAILURE_MESSAGE = "Could not find a Note / User not allowed to delete";

    @Test
    public void testAddNote() {
        when(notesRepository.findByTitle(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        when(notesRepository.save(any(NotesEntity.class))).thenReturn(prepareNotesEntity());
        ResponseEntity<NotesResponseDto> notesResponseEntity = notesServiceImpl.createNote(prepareNoteRequest(), prepareUser());
        Assert.assertNotNull(notesResponseEntity);
        Assert.assertEquals(prepareNotesEntity().getTitle(), Objects.requireNonNull(notesResponseEntity.getBody()).getTitle());
        verify(notesRepository, times(1)).save(any(NotesEntity.class));
    }

    @Test
    public void testUpdateNote() {
        when(notesRepository.findByTitle(anyString())).thenReturn(prepareNotesEntity());
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        NotesEntity updateNote = prepareNotesEntity();
        updateNote.setTitle("updatedTitle");
        when(notesRepository.save(any(NotesEntity.class))).thenReturn(updateNote);
        ResponseEntity<NotesResponseDto> notesResponseEntity = notesServiceImpl.createNote(prepareNoteRequest(), prepareUser());
        Assert.assertNotNull(notesResponseEntity);
        Assert.assertEquals(updateNote.getTitle(), Objects.requireNonNull(notesResponseEntity.getBody()).getTitle());
        verify(notesRepository, times(1)).save(any(NotesEntity.class));
    }

    @Test
    public void testGetAllNotes() {
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        when(notesRepository.findByUserId(anyInt())).thenReturn(prepareNoteEntities());
        ResponseEntity<List<AllNotesResponseDto>> allNotes = notesServiceImpl.getAllNotes(prepareUser());
        Assert.assertNotNull(allNotes);
        Assert.assertEquals(2, Objects.requireNonNull(allNotes.getBody()).size());

    }

    @Test
    public void testDeleteNote() {
        when(notesRepository.findById(anyInt())).thenReturn(Optional.of(prepareNotesEntity()));
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        ResponseEntity<NotesResponseDto> notesResponseDto = notesServiceImpl.deleteNoteById(prepareNotesEntity().getId(), prepareUser());
        Assert.assertNotNull(notesResponseDto);
        Assert.assertNull(Objects.requireNonNull(notesResponseDto.getBody()).getErrorMessage());
        Assert.assertEquals(DELETE_SUCCESS_MESSAGE, Objects.requireNonNull(notesResponseDto.getBody()).getSuccessMessage());
        verify(notesRepository, times(1)).delete(any(NotesEntity.class));
    }

    @Test
    public void testUnAuthorizedDeleteNote() {
        when(notesRepository.findById(anyInt())).thenReturn(Optional.of(prepareNotesEntity()));
        UserEntity userEntity = prepareUserEntity();
        userEntity.setId(1);
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        ResponseEntity<NotesResponseDto> notesResponseDto = notesServiceImpl.deleteNoteById(prepareNotesEntity().getId(), prepareUser());
        Assert.assertNotNull(notesResponseDto);
        Assert.assertNull(Objects.requireNonNull(notesResponseDto.getBody()).getSuccessMessage());
        Assert.assertEquals(DELETE_FAILURE_MESSAGE, Objects.requireNonNull(notesResponseDto.getBody()).getErrorMessage());
        verify(notesRepository, times(0)).delete(any(NotesEntity.class));
    }

    private NotesEntity prepareNotesEntity() {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setId(2);
        notesEntity.setTitle("myTitle");
        notesEntity.setNote("First test note");
        notesEntity.setUserId(prepareUserEntity().getId());
        return notesEntity;
    }

    private UserEntity prepareUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        userEntity.setEmail("test@user.com");
        userEntity.setPassword("testPassword");
        return userEntity;
    }

    private List<NotesEntity> prepareNoteEntities() {
        List<NotesEntity> response = new ArrayList<>();
        response.add(prepareNotesEntity());
        NotesEntity note = prepareNotesEntity();
        note.setTitle("UpdatedTitle");
        response.add(note);
        return response;
    }

    private NotesRequestDto prepareNoteRequest() {
        return new NotesRequestDto("DummyTitle", "dummyNote");
    }

    private User prepareUser() {
        return new User("username", "password", new ArrayList<>());
    }
}
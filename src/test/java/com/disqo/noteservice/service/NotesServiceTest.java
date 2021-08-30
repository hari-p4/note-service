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
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotesServiceTest {

    @InjectMocks
    NotesService notesService;

    @Mock
    NotesRepository notesRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void testAddNote() {
        when(notesRepository.findByTitle(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        when(notesRepository.save(any(NotesEntity.class))).thenReturn(prepareNotesEntity());
        NotesResponseDto notesResponseDto = notesService.addOrUpdateNotes(prepareNoteRequest(), prepareUser());
        Assert.assertNotNull(notesResponseDto);
        Assert.assertEquals(prepareNotesEntity().getTitle(), notesResponseDto.getTitle());
        verify(notesRepository, times(1)).save(any(NotesEntity.class));
    }

    @Test
    public void testUpdateNote() {
        when(notesRepository.findByTitle(anyString())).thenReturn(prepareNotesEntity());
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        NotesEntity updateNote = prepareNotesEntity();
        updateNote.setTitle("updatedTitle");
        when(notesRepository.save(any(NotesEntity.class))).thenReturn(updateNote);
        NotesResponseDto notesResponseDto = notesService.addOrUpdateNotes(prepareNoteRequest(), prepareUser());
        Assert.assertNotNull(notesResponseDto);
        Assert.assertEquals(updateNote.getTitle(), notesResponseDto.getTitle());
        verify(notesRepository, times(1)).save(any(NotesEntity.class));
    }

    @Test
    public void testGetAllNotes() {
        when(userRepository.findByEmail(anyString())).thenReturn(prepareUserEntity());
        when(notesRepository.findByUserId(anyInt())).thenReturn(prepareNoteEntities());
        List<AllNotesResponseDto> allNotes = notesService.getUserNotes(prepareUser());
        Assert.assertNotNull(allNotes);
        Assert.assertEquals(2, allNotes.size());

    }


    private NotesEntity prepareNotesEntity() {
        NotesEntity notesEntity = new NotesEntity();
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

    private NotesResponseDto prepareNoteResponse() {
        return new NotesResponseDto(1, "dummyTitle", null, null);
    }

    private NotesRequestDto prepareNoteRequest() {
        return new NotesRequestDto("DummyTitle", "dummyNote");
    }

    private User prepareUser() {
        return new User("username", "password", new ArrayList<>());
    }
}
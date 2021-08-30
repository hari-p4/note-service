package com.disqo.noteservice.repositories;

import com.disqo.noteservice.domain.NotesEntity;
import com.disqo.noteservice.domain.UserEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

/**
 * Data Jpa Test class for NoteRepository testing basic CRUD ops with validations.
 *
 * @author Haridas Parekh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotesRepositoryTest {

    @Autowired
    NotesRepository notesRepository;

    private NotesEntity notesEntity;

    private static final String MORETHAN_1000_CHAR = "H9OuogkNQyaCrB65Rz5b67HxUep3pD2SILielws5GbODAIHSGaoW8u7fXYtBr2jFTYf6thQA0RJKcA8WVraXb6qd66W5Vi1MHPUBxwvhcghDmp8iSm2HZ28BA4iefQIjDtUXpz5WawnPjUO4lS2w2OiM073HK92y8iCoQiwh0FnELht9vm1I3gplrkF95ccSRCT2fk9EF0qJjbWAv7QYnnmJOjmYJzvhikVVIT7HKBN0En4yIlZHeXNSmHctNaRQnzUiReAcRNA86FV5NL7AlNrHEAZyCkR84SseccTTCqVcNodX25C46i8yvPjbj1nB4nuiuUl1eqnEINgHsZxrm6umW79SGpnTIb0OA5j9Py33TStwv1asCk0OzYCIFLKOmjRBQdyCZ2n0xnwSOBYTaCsbefmZ3XJWJP6mXPMSWUdRwKngWiyxJHx6m1PuDxPZQf3fFmqdvtohqZFYEqZRSiI1UERdgPMjZsdqoSTxKDL7TXOWsIQcJ0VsVXvIWKmgT8XPysNZF5Oz8ThTYgkU0jgqRlSWgHMyklytbBjR2Ojk4Bu9pXAZOytAMmJj9waSegTmh5F8kBE5dzhFqpf8N1OT45ktKiyD7rrs3xBa9imbnSFRrF8pBsRNdxgR1X2R5pOu07VxLdn4kZWJy2BBKKk0Z5jSiLKVz1LYgCik0Fw9aO1lOgVbPNnnJVbDtISXL11O9liDv9ZMpeKTpAmunpwQvngRfgdByQcFt40qQW3JmIoRzl6DTt0or1J2CBjvQZwXldtmZM6nwcOvXmoFVtbO5rw23QqA5gASnE7Po5IGKcygwZHfH6tjU0FgMsCPuJpV140Mj2v2M76YIXOnJScgMoVKIP3HJRb1xMYbz4TDeXCD0P02pEXPsvVn6IKNYSIB8UVbj3dZtPga0zkHG9jyBcU88nsRMGyyehx9c6znj6FceRokAbGxA8u16aMxl6RviV17EaxSpo5g2CWPF73lhm796pLcsUTwYwjYu";
    private static final String MORETHAN_50_CHAR = "H9OuogkNQyaCrB65Rz5b67HxUep3pD2SILielws5GbODAIHSGaoW8u7fXYtBr2jFTYf6thQA0RJKcA8WVraXb6qd66W5Vi1MHPUBxwvhcghDmp";

    @Before
    public void setUp() {
        UserEntity userEntity = prepareUserEntity();
        prepareNotesEntity();
    }

    @Test
    public void testSaveNote() {
        NotesEntity savedNote = notesRepository.save(notesEntity);
        notesRepository.flush();
        Assert.assertNotNull(savedNote);
        Assert.assertEquals(notesEntity.getTitle(), savedNote.getTitle());
    }

    @Test
    public void testUpdateNote() {
        NotesEntity savedNote = notesRepository.save(notesEntity);
        NotesEntity updateNote = notesRepository.findByTitle(savedNote.getTitle());
        updateNote.setNote("Second test note");
        updateNote = notesRepository.save(updateNote);
        notesRepository.flush();
        Assert.assertNotNull(updateNote);
        Assert.assertEquals("Second test note", updateNote.getNote());
        Assert.assertEquals(notesEntity.getTitle(), updateNote.getTitle());
    }

    @Test
    public void testDeleteNote() {
        NotesEntity savedNote = notesRepository.save(notesEntity);
        Assert.assertNotNull(notesRepository.findByTitle(savedNote.getTitle()));
        notesRepository.delete(notesRepository.findByTitle(savedNote.getTitle()));
        notesRepository.flush();
        Assert.assertNull(notesRepository.findByTitle(savedNote.getTitle()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTitleMaxSize() {
        NotesEntity titleMaxSize = notesEntity;
        notesRepository.save(titleMaxSize);
        titleMaxSize.setTitle(MORETHAN_50_CHAR);
        notesRepository.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNoteMaxSize() {
        NotesEntity noteMaxSize = notesEntity;
        noteMaxSize.setNote(MORETHAN_1000_CHAR);
        notesRepository.save(noteMaxSize);
        notesRepository.flush();
    }

    @Test
    public void testFindByTitle() {
        NotesEntity savedNote = notesRepository.save(notesEntity);
        notesRepository.flush();
        NotesEntity noteByTitle = notesRepository.findByTitle(savedNote.getTitle());
        Assert.assertNotNull(savedNote);
        Assert.assertEquals(notesEntity.getTitle(), noteByTitle.getTitle());
    }

    private void prepareNotesEntity() {
        notesEntity = new NotesEntity();
        notesEntity.setTitle("myTitle");
        notesEntity.setNote("First test note");
        notesEntity.setUserId(prepareUserEntity().getId());
    }

    private UserEntity prepareUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        userEntity.setEmail("test@user.com");
        userEntity.setPassword("testPassword");
        return userEntity;
    }
}
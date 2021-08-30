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
import java.util.ArrayList;
import java.util.List;

/**
 * Data Jpa Test class for UserRepositoryTest testing validations.
 * @author Haridas Parekh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        UserEntity userEntity = prepareUserEntity();
        prepareNotesEntity(userEntity);
    }

    @Test
    public void testSaveUser() {
        UserEntity savedUser = userRepository.save(prepareUserEntity());
        userRepository.flush();
        Assert.assertNotNull(savedUser);
        Assert.assertEquals(prepareUserEntity().getEmail(), savedUser.getEmail());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUserEmailNotBlank() {
        UserEntity user = prepareUserEntity();
        user.setEmail("");
        userRepository.save(user);
        userRepository.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testInvalidEmail() {
        UserEntity user = prepareUserEntity();
        user.setEmail("someString");
        userRepository.save(user);
        userRepository.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPasswordNotBlank() {
        UserEntity user = prepareUserEntity();
        user.setPassword("");
        userRepository.save(user);
        userRepository.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPasswordNotLessThanRequiredChars() {
        UserEntity user = prepareUserEntity();
        user.setPassword("abc");
        userRepository.save(user);
        userRepository.flush();
    }

    private NotesEntity prepareNotesEntity(UserEntity userEntity) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setTitle("myTitle");
        notesEntity.setNote("First test note");
        notesEntity.setUserEntity(userEntity);
        return notesEntity;
    }

    @Test
    public void testFindByEmail() {
        UserEntity savedUser = userRepository.save(prepareUserEntity());
        userRepository.flush();
        Assert.assertNotNull(userRepository.findByEmail(savedUser.getEmail()));
        Assert.assertEquals(prepareUserEntity().getEmail(), savedUser.getEmail());
    }

    private UserEntity prepareUserEntity() {
        List<NotesEntity> notesEntities = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        userEntity.setEmail("test@user.com");
        userEntity.setPassword("testPassword");
        notesEntities.add(prepareNotesEntity(userEntity));
        userEntity.setNotesEntities(notesEntities);
        return userEntity;
    }
}
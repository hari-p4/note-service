package com.disqo.noteservice.repositories;

import com.disqo.noteservice.domain.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Haridas Parekh
 * Repository interface for NotesEntity which allowes finding by Title
 */
@Repository
public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {

    NotesEntity findByTitle(String title);

}

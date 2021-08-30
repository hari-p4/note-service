package com.disqo.noteservice.repositories;

import com.disqo.noteservice.domain.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for NotesEntity which allows finding by Title
 *
 * @author Haridas Parekh
 */
@Repository
public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {

    NotesEntity findByTitle(String title);

    List<NotesEntity> findByUserId(Integer userId);

}

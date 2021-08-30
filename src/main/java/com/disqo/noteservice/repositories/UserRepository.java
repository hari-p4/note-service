package com.disqo.noteservice.repositories;

import com.disqo.noteservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Repository interface for UserEntity which allows finding by Email
 * @author Haridas Parekh
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

}

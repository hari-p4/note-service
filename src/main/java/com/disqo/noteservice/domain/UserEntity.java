package com.disqo.noteservice.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Haridas Parekh
 * Entity class for Users
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * Email field should not be blank, must be a valid address and unique
     */
    @NonNull
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * password must be of 8 characters and not be blank
     */
    @NotBlank(message = "Password  cannot be blank")
    @Size(min = 8, message = "Password must be of at least 8 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotesEntity> notesEntities = new ArrayList<>();

}

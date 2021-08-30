package com.disqo.noteservice.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
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
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "last_update_time")
    @UpdateTimestamp
    private Timestamp lastUpdateTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "notes_user_id_fkey"))
    private List<NotesEntity> notesEntities = new ArrayList<>();

}

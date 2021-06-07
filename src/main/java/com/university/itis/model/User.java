package com.university.itis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_confirmed", nullable = false)
    private Boolean isEmailConfirmed;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}

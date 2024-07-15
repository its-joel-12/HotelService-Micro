package com.joel.user.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "micro_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name")
//    @NotEmpty
//    @NotBlank
    private String userName;

    @Column(name = "email", unique = true)
//    @Email
    private String userEmail;

    @Column(name = "about")
//    @NotEmpty
//    @NotBlank
    private String userAbout;

    @Transient
    private List<Rating> ratings;
}

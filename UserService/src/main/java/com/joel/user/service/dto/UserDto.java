package com.joel.user.service.dto;

import com.joel.user.service.model.Rating;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;

    @NotEmpty
    @NotBlank
    private String userName;

    @NotEmpty
    @NotBlank
    @Email
    private String userEmail;

    @NotEmpty
    @NotBlank
    private String userAbout;

    private List<Rating> ratings;
}

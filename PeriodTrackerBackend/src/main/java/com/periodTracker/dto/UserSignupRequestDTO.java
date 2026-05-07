package com.periodTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {
@NotBlank(message = "Username cannot be empty")
private String username;
@NotBlank(message = "Password cannot be empty")
private String password;
@NotNull(message = "Age is required")
private Integer age;
@NotNull(message = "Height cannot be empty")
private Integer height;
@NotNull(message = "Weight cannot be empty")
private Integer weight;
}

package com.ra.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ra.constants.Gender;
import com.ra.constants.Status;
import com.ra.model.entity.Roles;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private final String type = "Bearer";
    private Long expired;
    private String userName;
    private String fullName;
    private Gender gender;
    private java.sql.Date dob;
    private String email;
    private String avatar;
    private String password;
    private String phoneNumber;
    private String address;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Set<Roles> roles;
}

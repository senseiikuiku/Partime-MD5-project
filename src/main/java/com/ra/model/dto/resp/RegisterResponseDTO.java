package com.ra.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ra.constants.Gender;
import com.ra.model.entity.Roles;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterResponseDTO {
    private String fullName;
    private String email;
    private String userName;
    private String address;
    private String phoneNumber;
    private String avatar;
    private Gender gender;
    private java.sql.Date dob;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Set<Roles> roles;
}

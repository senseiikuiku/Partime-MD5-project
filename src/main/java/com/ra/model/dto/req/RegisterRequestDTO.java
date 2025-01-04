package com.ra.model.dto.req;

import com.ra.constants.Gender;
import com.ra.constants.Status;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegisterRequestDTO {
    private String fullName;
    private String userName;
    private Gender gender;
    private java.sql.Date dob;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Status status;
}

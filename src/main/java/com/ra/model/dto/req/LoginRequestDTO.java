package com.ra.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRequestDTO {
    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "email không đúng định dạng")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}

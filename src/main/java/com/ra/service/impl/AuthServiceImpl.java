package com.ra.service.impl;

import com.ra.constants.Gender;
import com.ra.constants.Status;
import com.ra.model.dto.req.LoginRequestDTO;
import com.ra.model.dto.req.RegisterRequestDTO;
import com.ra.model.dto.resp.LoginResponseDTO;
import com.ra.model.dto.resp.RegisterResponseDTO;
import com.ra.model.entity.Roles;
import com.ra.model.entity.Users;
import com.ra.repository.RolesRepository;
import com.ra.repository.UsersRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.principle.MyUserDetails;
import com.ra.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Value("86400000")
    private Long EXPIRED;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication auth;
        auth = authenticationProvider.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        return LoginResponseDTO.builder()
                .userName(userDetails.getUsername())
                .fullName(userDetails.getUser().getFullName())
                .gender(userDetails.getUser().getGender())
                .dob(userDetails.getUser().getDob())
                .email(userDetails.getUser().getEmail())
                .avatar(userDetails.getUser().getAvatar())
                .password(userDetails.getPassword())
                .phoneNumber(userDetails.getUser().getPhoneNumber())
                .address(userDetails.getUser().getAddress())
                .status(userDetails.getUser().getStatus())
                .accessToken(jwtProvider.generateToken(userDetails))
                .roles(userDetails.getUser().getRoles())
                .build();
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        // Lấy danh sách roles và thêm role mặc định "User"
        Set<Roles> roles = new HashSet<>();
        Roles role = rolesRepository.findRoleByRoleName("User");
        if (role == null) {
            throw new IllegalArgumentException("Role 'User' not found in the database.");
        }
        roles.add(role);

        // Kiểm tra và xác thực Gender từ DTO
        Gender gender;
        if (registerRequestDTO.getGender() == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        } else {
            gender = registerRequestDTO.getGender(); // Đảm bảo DTO đã chuyển đúng kiểu
        }
        // Kiểm tra và tạo username duy nhất
        String baseUserName = registerRequestDTO.getUserName() != null ? registerRequestDTO.getUserName() : "customer_user";
        String uniqueUserName = baseUserName;
        int counter = 1;
        while (usersRepository.existsByUserName(uniqueUserName)) {
            uniqueUserName = baseUserName + counter;
            counter++;
        }
        // Tạo đối tượng Users từ thông tin DTO
        Users userEntity = Users.builder()
                .fullName(registerRequestDTO.getFullName())
                .gender(gender)
                .dob(registerRequestDTO.getDob())
                .email(registerRequestDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword())) // Mã hóa mật khẩu
                .phoneNumber(registerRequestDTO.getPhoneNumber())
                .address(registerRequestDTO.getAddress())
                .status(Status.active) // Gán trạng thái mặc định là "active"
                .userName(uniqueUserName)
                .avatar("avatar10.jpg")
                .roles(roles) // Gán danh sách roles
                .build();

        // Lưu thông tin user vào cơ sở dữ liệu
        Users savedUser = usersRepository.save(userEntity);

        // Trả về phản hồi sau khi đăng ký thành công
        return RegisterResponseDTO.builder()
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail()) // Đảm bảo lấy giá trị email đúng
                .userName(savedUser.getUserName())
                .address(savedUser.getAddress())
                .phoneNumber(savedUser.getPhoneNumber())
                .avatar(savedUser.getAvatar())
                .gender(savedUser.getGender())
                .roles(savedUser.getRoles())
                .dob(savedUser.getDob())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

    }

}

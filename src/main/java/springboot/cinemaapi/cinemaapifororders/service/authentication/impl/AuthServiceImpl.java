package springboot.cinemaapi.cinemaapifororders.service.authentication.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.Role;
import springboot.cinemaapi.cinemaapifororders.entity.User;
import springboot.cinemaapi.cinemaapifororders.payload.dto.LoginDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.RegisterDto;
import springboot.cinemaapi.cinemaapifororders.repository.RoleRepository;
import springboot.cinemaapi.cinemaapifororders.repository.UserRepository;
import springboot.cinemaapi.cinemaapifororders.security.JwtTokenProvider;
import springboot.cinemaapi.cinemaapifororders.service.authentication.AuthService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;

    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("Username is already in use");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered Successfully";
    }
}

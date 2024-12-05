package springboot.cinemaapi.cinemaapifororders.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.domain.model.user.Role;
import springboot.cinemaapi.cinemaapifororders.domain.model.user.User;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.LoginDto;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.RegisterDto;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.RoleRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.UserRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.security.JwtTokenProvider;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.AuthService;

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

        if(userRepository.existsByPhoneNumber(registerDto.getPhoneNumber())){
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setPhoneNumber(registerDto.getPhoneNumber());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace(); // Log full exception for debugging
            throw e; // Rethrow to maintain behavior
        }
        System.out.println("User saved successfully.");

        return "User Registered Successfully";
    }
}

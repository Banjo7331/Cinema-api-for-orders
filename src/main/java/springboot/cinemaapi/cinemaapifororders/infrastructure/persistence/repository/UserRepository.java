package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
}

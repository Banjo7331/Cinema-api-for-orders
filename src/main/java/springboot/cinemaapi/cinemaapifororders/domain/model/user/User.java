package springboot.cinemaapi.cinemaapifororders.domain.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column
    private String username;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$",
            message = "Invalid phone number format")
    @Column
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    @Column
    private String email;

    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}

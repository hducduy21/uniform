package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.entities.enums.EGender;
import nashtech.rookie.uniform.entities.enums.ERole;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private LocalDateTime birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;

    private LocalDateTime lastLogin;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    private Boolean locked = false;
    private Boolean enabled = true;
}

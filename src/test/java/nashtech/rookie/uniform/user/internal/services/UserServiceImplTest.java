package nashtech.rookie.uniform.user.internal.services;

import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.user.internal.dtos.request.UserFilter;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.entities.enums.EGender;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;
import nashtech.rookie.uniform.user.internal.mappers.UserMapper;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import nashtech.rookie.uniform.user.internal.repositories.UserSpecificationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserSpecificationBuilder userSpecificationBuilder;

    @Mock
    private Logger logger; // Mock logger for log.info

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDetailResponse userDetailResponse;
    private UserFilter userFilter;
    private PageRequest pageable;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        user = User.builder()
                .id(userId)
                .email("test@example.com")
                .phoneNumber("1234567890")
                .password("encodedPassword")
                .firstName("John")
                .lastName("Doe")
                .gender(EGender.MALE)
                .birthday(LocalDate.of(1990, 1, 1))
                .role(ERole.USER)
                .createdAt(LocalDateTime.now())
                .locked(false)
                .enabled(true)
                .build();

        userDetailResponse = UserDetailResponse.builder()
                .id(userId)
                .email("test@example.com")
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .gender(EGender.MALE)
                .birthday(LocalDate.of(1990, 1, 1))
                .role(ERole.USER)
                .createdAt(user.getCreatedAt())
                .locked(false)
                .enabled(true)
                .build();

        userFilter = new UserFilter();
        pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
    }

    @Test
    void getAllUser_ValidRequest_ReturnsPagedUsers() {
        // Arrange
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        when(userSpecificationBuilder.build(userFilter)).thenReturn(mock(Specification.class));
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userPage);
        when(userMapper.userToUserDetailResponse(user)).thenReturn(userDetailResponse);

        // Act
        Page<UserDetailResponse> result = userService.getAllUser(pageable, userFilter);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("test@example.com", result.getContent().get(0).getEmail());
        assertEquals(ERole.USER, userFilter.getRole()); // Verify role set to USER
        verify(userSpecificationBuilder, times(1)).build(userFilter);
        verify(userRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(userMapper, times(1)).userToUserDetailResponse(user);
    }

    @Test
    void getAllUser_EmptyPage_ReturnsEmptyPage() {
        // Arrange
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList());

        when(userSpecificationBuilder.build(userFilter)).thenReturn(mock(Specification.class));
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(emptyPage);

        // Act
        Page<UserDetailResponse> result = userService.getAllUser(pageable, userFilter);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        assertEquals(ERole.USER, userFilter.getRole()); // Verify role set to USER
        verify(userSpecificationBuilder, times(1)).build(userFilter);
        verify(userRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(userMapper, never()).userToUserDetailResponse(any(User.class));
    }

}
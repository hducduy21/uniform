package nashtech.rookie.uniform.user.internal.mappers;

import nashtech.rookie.uniform.user.dto.UserInfoDto;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserUpdateRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.entities.enums.EGender;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    private UserRegisterRequest createRegisterRequest(String gender, LocalDate birthday) {

        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setEmail("user@gmail.com");
        request.setPhoneNumber("0123456789");
        request.setPassword("password123");
        request.setConfirmPassword("password123");
        request.setGender(gender);
        request.setBirthday(birthday);
        return request;
    }

    private User createUser(EGender gender, LocalDate birthday, LocalDateTime createdAt, LocalDateTime lastLogin) {
        return User.builder()
                .id(UUID.randomUUID())
                .email("user@gmail.com")
                .phoneNumber("0123456789")
                .password("password123")
                .firstName("First")
                .lastName("Last")
                .gender(gender)
                .birthday(birthday)
                .role(ERole.USER)
                .createdAt(createdAt)
                .lastLogin(lastLogin)
                .locked(false)
                .enabled(true)
                .build();
    }

    private void assertUserDetailsMatch(User user, UserDetailResponse response) {
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getGender(), response.getGender());
        assertEquals(user.getBirthday(), response.getBirthday());
        assertEquals(user.getRole(), response.getRole());
        assertEquals(user.getCreatedAt(), response.getCreatedAt());
        assertEquals(user.getLastLogin(), response.getLastLogin());
        assertEquals(user.getLocked(), response.getLocked());
        assertEquals(user.getEnabled(), response.getEnabled());
    }

    @Test
    void shouldMapValidRegisterRequestToUser() {
        UserRegisterRequest request = createRegisterRequest("MALE", LocalDate.of(1990, 1, 1));

        User user = userMapper.userRegisterRequestToUser(request);

        assertNotNull(user);
        assertEquals("First", user.getFirstName());
        assertEquals("Last", user.getLastName());
        assertEquals("user@gmail.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertEquals(EGender.MALE, user.getGender());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthday());
        assertEquals(ERole.USER, user.getRole());
        assertNotNull(user.getCreatedAt());
        assertFalse(user.getLocked());
        assertTrue(user.getEnabled());
    }

    @Test
    void shouldMapNullFieldsInRegisterRequest() {
        UserRegisterRequest request = createRegisterRequest(null, null);

        User user = userMapper.userRegisterRequestToUser(request);

        assertNotNull(user);
        assertEquals("First", user.getFirstName());
        assertEquals("Last", user.getLastName());
        assertEquals("user@gmail.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertNull(user.getGender());
        assertNull(user.getBirthday());
        assertEquals(ERole.USER, user.getRole());
        assertFalse(user.getLocked());
        assertTrue(user.getEnabled());
    }

    @Test
    void shouldMapUserToUserDetailResponse() {
        User user = createUser(EGender.MALE, LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now());

        UserDetailResponse response = userMapper.userToUserDetailResponse(user);

        assertUserDetailsMatch(user, response);
    }

    @Test
    void shouldMapUserToUserDetailResponseWithNulls() {
        User user = createUser(null, null, LocalDateTime.now(), null);

        UserDetailResponse response = userMapper.userToUserDetailResponse(user);

        assertUserDetailsMatch(user, response);
    }

    @Test
    void shouldMapUserToUserInfoDto() {
        User user = createUser(EGender.MALE, LocalDate.of(1990, 1, 1), null, null);

        UserInfoDto dto = userMapper.userToUserInfoDto(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals("MALE", dto.getGender());
        assertEquals(user.getBirthday(), dto.getBirthday());
        assertEquals("USER", dto.getRole());
        assertFalse(dto.isLocked());
        assertTrue(dto.isEnabled());
    }

    @Test
    void shouldMapUserToUserInfoDtoWithNulls() {
        User user = createUser(null, null, null, null);

        UserInfoDto dto = userMapper.userToUserInfoDto(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertNull(dto.getGender());
        assertNull(dto.getBirthday());
        assertEquals("USER", dto.getRole());
        assertFalse(dto.isLocked());
        assertTrue(dto.isEnabled());
    }

    @Test
    void shouldUpdateUserWithNonNullFields() {
        User user = createUser(EGender.FEMALE, LocalDate.of(1980, 1, 1), null, null);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("new@gmail.com");
        request.setPhoneNumber("0123456789");
        request.setFirstName("NewFirst");
        request.setLastName("NewLast");
        request.setGender("MALE");
        request.setBirthday(LocalDate.of(1990, 1, 1));

        userMapper.userUpdateRequestToUser(user, request);

        assertEquals("new@gmail.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("NewFirst", user.getFirstName());
        assertEquals("NewLast", user.getLastName());
        assertEquals(EGender.MALE, user.getGender());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthday());
    }

    @Test
    void shouldNotUpdateUserWithNullFields() {
        User user = createUser(EGender.FEMALE, LocalDate.of(1980, 1, 1), null, null);

        UserUpdateRequest request = new UserUpdateRequest();

        userMapper.userUpdateRequestToUser(user, request);

        assertEquals("user@gmail.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("First", user.getFirstName());
        assertEquals("Last", user.getLastName());
        assertEquals(EGender.FEMALE, user.getGender());
        assertEquals(LocalDate.of(1980, 1, 1), user.getBirthday());
    }

}
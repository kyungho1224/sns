package kr.amaranth.sns.service;

import kr.amaranth.sns.exception.SnsApplicationException;
import kr.amaranth.sns.fixture.UserEntityFixture;
import kr.amaranth.sns.model.entity.UserEntity;
import kr.amaranth.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @DisplayName("회원가입 성공 테스트")
    @Test
    public void 회원가입이_정상적으로_동작하는_경우() {
        // Given
        String userName = "userName";
        String password = "password";

        // When
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
//        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password));

        // Then
        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @DisplayName("회원가입 실패 테스트")
    @Test
    public void 회원가입시_userName으로_회원가입한_유저가_이미_있는_경우() {
        // Given
        String userName = "userName";
        String password = "password";

        // When
        UserEntity fixture = UserEntityFixture.get(userName, password);
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    public void 로그인이_정상적으로_동작하는_경우() {
        // Given
        String userName = "userName";
        String password = "password";

        // When
        UserEntity fixture = UserEntityFixture.get(userName, password);
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        // Then
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    public void 로그인시_userName으로_회원가입한_유저가_없는_경우() {
        // Given
        String userName = "userName";
        String password = "password";

        // When
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    public void 로그인시_패스워드가_틀린_경우() {
        // Given
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        // When
        UserEntity fixture = UserEntityFixture.get(userName, password);
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, wrongPassword));
    }

}
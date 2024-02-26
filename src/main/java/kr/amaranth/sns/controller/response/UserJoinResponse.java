package kr.amaranth.sns.controller.response;

import kr.amaranth.sns.model.User;
import kr.amaranth.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user) {
        return UserJoinResponse.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .role(user.getRole())
            .build();
    }

}

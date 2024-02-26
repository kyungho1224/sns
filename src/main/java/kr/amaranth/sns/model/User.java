package kr.amaranth.sns.model;

import kr.amaranth.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Builder
@Getter
public class User {

    private Long id;
    private String userName;
    private String password;
    private UserRole role;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity) {
        return User.builder()
            .id(entity.getId())
            .userName(entity.getUserName())
            .password(entity.getPassword())
            .role(entity.getRole())
            .registeredAt(entity.getRegisteredAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

}

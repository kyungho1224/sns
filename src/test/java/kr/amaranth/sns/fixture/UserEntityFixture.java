package kr.amaranth.sns.fixture;

import kr.amaranth.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password) {
        return UserEntity.builder()
            .id(1L)
            .userName(userName)
            .password(password)
            .build();
    }

}

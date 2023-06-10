package com.nttdata.usercrud.util;

import com.nttdata.usercrud.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@Builder
public class DataTest {

    public static Optional<UserEntity> saverUser01() {
        return Optional.of(new UserEntity(1, "User 01", "user01@test.com", 20));
    }

    public static Optional<UserEntity> saverUser02() {
        return Optional.of(new UserEntity(2, "User 02", "user02@test.com", 5));
    }
}

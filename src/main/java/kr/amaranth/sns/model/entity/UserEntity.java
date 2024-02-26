package kr.amaranth.sns.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table
@Getter
@Builder
public class UserEntity {

    @Id
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

}

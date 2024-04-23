package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDto implements Serializable  {

    private String email;
    private String password;
    private RoleName role;

    public CreateUserDto(String email, String password, RoleName role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

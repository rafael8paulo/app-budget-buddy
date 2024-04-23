package br.com.rpx.budgetbuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDto implements Serializable {
    private String email;
    private String password;

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

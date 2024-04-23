package br.com.rpx.budgetbuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RecoveryJwtTokenDto  implements Serializable {

    private String token;

    public RecoveryJwtTokenDto(String token) {
        this.token = token;
    }
}

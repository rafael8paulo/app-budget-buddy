package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class RecoveryUserDto implements Serializable {

    private Long id;
    private String email;
    private List<Role> roles;

    public RecoveryUserDto(Long id, String email, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}

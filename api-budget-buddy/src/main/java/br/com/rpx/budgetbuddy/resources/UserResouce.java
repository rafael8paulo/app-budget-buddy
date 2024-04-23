package br.com.rpx.budgetbuddy.resources;

import br.com.rpx.budgetbuddy.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResouce {
    private UserService userService;

    public UserResouce(UserService userService) {
        this.userService = userService;
    }

}
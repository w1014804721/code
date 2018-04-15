package top.wangjingxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wangjingxin.base.Result;
import top.wangjingxin.model.dto.UserDTO;
import top.wangjingxin.service.UserService;

@RequestMapping(value = "/api/v1/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("exist")
    public Result exist(String mail) {
        return userService.exist(mail);
    }

    @PostMapping("register")
    public Result register(UserDTO user) {
        return userService.register(user);
    }
}

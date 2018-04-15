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

    @PostMapping("login")
    public Result login(String mail,String password) {
        return userService.login(mail,password);
    }

    @GetMapping("verification")
    public Result verification(String token) {
        return userService.verification(token);
    }

    @GetMapping("info")
    public Result info(String id) {
        return userService.info(id);
    }

    @PostMapping("check")
    public Result check() {
        return userService.check();
    }
}

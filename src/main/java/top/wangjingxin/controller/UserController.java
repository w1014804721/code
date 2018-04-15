package top.wangjingxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wangjingxin.service.UserService;

@RequestMapping(value = "/api/v1/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

}

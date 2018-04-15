package top.wangjingxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wangjingxin.base.Result;
import top.wangjingxin.service.IntegralService;

@RequestMapping(value = "/api/v1/integral")
@RestController
public class IntegralController {
    @Autowired
    IntegralService integralService;

    @GetMapping("day")
    public Result day(String type) {
        return integralService.day(type);
    }

    @GetMapping("all")
    public Result all(String type) {
        return integralService.all(type);
    }

    @GetMapping("check")
    public Result check(String type) {
        return integralService.check(type);
    }
}

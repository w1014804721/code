package top.wangjingxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wangjingxin.base.Result;
import top.wangjingxin.model.dto.AnswerDTO;
import top.wangjingxin.service.AnswerService;
import top.wangjingxin.util.Page;

@RequestMapping(value = "/api/v1/answer")
@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @GetMapping("get")
    public Result get(Page page) {
        return answerService.get(page);
    }

    @GetMapping("getByTitle")
    public Result getByTitle(Page page,String title) {
        return answerService.getByTitle(page,title);
    }

    @GetMapping("getByUser")
    public Result getByUser(Page page,String id) {
        return answerService.getByUser(page,id);
    }

    @GetMapping("get/count")
    public Result getCount() {
        return answerService.getCount();
    }

    @GetMapping("getByTitle/count")
    public Result getByTitleCount(String title) {
        return answerService.getByTitleCount(title);
    }

    @GetMapping("getByUser/count")
    public Result getByUserCount(String id) {
        return answerService.getByUserCount(id);
    }

    @PostMapping("publish")
    public Result publish(AnswerDTO answerDTO) {
        return answerService.publish(answerDTO);
    }

    @PostMapping("query")
    public Result query(String slug) {
        return answerService.query(slug);
    }
}

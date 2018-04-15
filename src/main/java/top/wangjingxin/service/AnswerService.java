package top.wangjingxin.service;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wangjingxin.base.Result;
import top.wangjingxin.dao.AnswerDao;
import top.wangjingxin.model.dto.AnswerDTO;
import top.wangjingxin.model.dto.QuestionDTO;
import top.wangjingxin.model.vo.QuestionVO;
import top.wangjingxin.util.Page;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.Integer.parseInt;
import static top.wangjingxin.cache.ResultCache.getCache;
import static top.wangjingxin.cache.ResultCache.getDataOk;
import static top.wangjingxin.config.AppConfig.LEVEL_MAP;
import static top.wangjingxin.config.AppConfig.MARK_MAP;
import static top.wangjingxin.config.AppConfig.ROOT;
import static top.wangjingxin.util.SessionUtil.user;

@Service
public class AnswerService {
    @Autowired
    AnswerDao answerDao;

    public Result get(Page page) {
        return getDataOk(answerDao.get(page.conversion()));
    }

    public Result getByTitle(Page page, String title) {
        return getDataOk(answerDao.getByTitle(page.conversion(), title));
    }

    public Result getByUser(Page page, String id) {
        return getDataOk(answerDao.getByUser(page.conversion(), id));
    }

    public Result getCount() {
        return getDataOk(answerDao.getCount());
    }

    public Result getByTitleCount(String title) {
        return getDataOk(answerDao.getByTitleCount(title));
    }

    public Result getByUserCount(String id) {
        return getDataOk(answerDao.getByUserCount(id));
    }

    public Result publish(AnswerDTO answerDTO) {
        answerDTO.setUserId(user());
        int count = answerDao.queryCount(answerDTO);
        int insert = answerDao.insert(answerDTO);
        if (count == 0 && insert == 1) {
            answerDao.day(answerDTO.getUserId(), getMark(answerDao.getMark(answerDTO.getNumber())));
            try (FileWriter fw = new FileWriter(ROOT + File.separator + "html" + File.separator + answerDTO.getId() + ".html")) {
                fw.write(new PegDownProcessor(Integer.MAX_VALUE).markdownToHtml(answerDTO.getContent()));
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getCache(insert);
    }

    private int getMark(int mark) {
        return MARK_MAP.get(mark);
    }

    public Result query(String slug) {
        QuestionVO vo = answerDao.query(slug);
        if (vo == null) {
            QuestionDTO dto = getQuestion(slug);
            answerDao.insertQuestion(dto);
            vo = dto.getVO();
        }
        return getDataOk(vo);
    }

    private QuestionDTO getQuestion(String slug) {
        QuestionDTO dto = new QuestionDTO();
        String path = "https://leetcode.com/graphql?variables={\"titleSlug\":\"#####\"}&query=query%20getQuestionDetail(%24titleSlug%3A%20String!)%20%7B%0A%20%20isCurrentUserAuthenticated%0A%20%20question(titleSlug%3A%20%24titleSlug)%20%7B%0A%20%20%20%20questionId%0A%20%20%20%20questionFrontendId%0A%20%20%20%20questionTitle%0A%20%20%20%20translatedTitle%0A%20%20%20%20questionTitleSlug%0A%20%20%20%20content%0A%20%20%20%20translatedContent%0A%20%20%20%20difficulty%0A%20%20%20%20stats%0A%20%20%20%20contributors%0A%20%20%20%20similarQuestions%0A%20%20%20%20discussUrl%0A%20%20%20%20mysqlSchemas%0A%20%20%20%20randomQuestionUrl%0A%20%20%20%20sessionId%0A%20%20%20%20categoryTitle%0A%20%20%20%20submitUrl%0A%20%20%20%20interpretUrl%0A%20%20%20%20codeDefinition%0A%20%20%20%20sampleTestCase%0A%20%20%20%20enableTestMode%0A%20%20%20%20metaData%0A%20%20%20%20enableRunCode%0A%20%20%20%20enableSubmit%0A%20%20%20%20judgerAvailable%0A%20%20%20%20infoVerified%0A%20%20%20%20envInfo%0A%20%20%20%20urlManager%0A%20%20%20%20article%0A%20%20%20%20questionDetailUrl%0A%20%20%20%20discussCategoryId%0A%20%20%20%20discussSolutionCategoryId%0A%20%20%20%20libraryUrl%0A%20%20%20%20companyTags%20%7B%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%20%20translatedName%0A%20%20%20%20%7D%0A%20%20%20%20topicTags%20%7B%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%20%20translatedName%0A%20%20%20%20%7D%0A%20%20%7D%0A%20%20interviewed%20%7B%0A%20%20%20%20interviewedUrl%0A%20%20%20%20companies%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%7D%0A%20%20%20%20timeOptions%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%7D%0A%20%20%20%20stageOptions%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%7D%0A%20%20%7D%0A%20%20subscribeUrl%0A%20%20isPremium%0A%20%20loginUrl%0A%7D%0A&".replace("#####", slug);
        try {
            String body = Jsoup.connect(path)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .body();
            JSONObject object = JSONObject.parseObject(body)
                    .getJSONObject("data").getJSONObject("question");
            dto.setId(parseInt(object.getString("questionId")));
            dto.setSlug(slug);
            dto.setContent(object.getString("content"));
            dto.setTitle(object.getString("questionTitle"));
            dto.setDifficulty(getLevel(object.getString("difficulty")));
            dto.setTags(object.getJSONArray("topicTags").stream().reduce("", (a, b) -> a + "," + ((JSONObject) b).getString("name")).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }

    private Integer getLevel(String difficulty) {
        return LEVEL_MAP.get(difficulty);
    }

    public static void main(String[] args) throws IOException {
        String s = "# 山东大学ING工作室\n" +
                "## 全局约定\n" +
                "    1、如无特殊说明：简单request类型是formdata，参数涉及数组或者其他非简单类型数据的request类型是JSON，response类型是JSON\n" +
                "    2、返回值形式：{status:(请求的状态),message:(一个简单短语解释状态码),data:{返回的数据object}}\n" +
                "       状态码：\n" +
                "        200表示请求成功\n" +
                "        300 表示请求失败\n" +
                "        400 表示没有权限\n" +
                "       所有涉及到分页的URL，统一请求参数：\n" +
                "        page:第几页\n" +
                "        offsets:偏移量\n" +
                "        rows：每页多少个\n" +
                "    3、所有返回有/无的接口，返回值统一是{success:m} m可取值为0，1 其中0代表无1代表有\n" +
                "    4、全局约定中声明好的返回数据，在本文档中具体接口中留空\n" +
                "    5、url统一前缀为 /api/v1\n" +
                "    6、静态资源问题：URL统一前缀为 /resource/\n" +
                "## 接口定义\n" +
                "### 用户相关 /api/v1/user\n" +
                "#### 验证是否注册\n" +
                "    url:exist[get]\n" +
                "    req:\n" +
                "        mail 邮箱\n" +
                "    resp:\n" +
                "\n" +
                "#### 注册\n" +
                "    url:register[post]\n" +
                "    req:\n" +
                "        mail 邮箱\n" +
                "        nickName 昵称\n" +
                "        password 密码\n" +
                "        aims 目标得分\n" +
                "    resp:\n" +
                "#### 登录\n" +
                "    url:login[post]\n" +
                "    req:\n" +
                "        mail 邮箱\n" +
                "        password 密码\n" +
                "    resp:\n" +
                "        id 用户id (登陆成功)\n" +
                "        error0 (没有通过邮箱验证)\n" +
                "        error1 (用户名或密码错误)\n" +
                "#### 用户的基本信息\n" +
                "    url:info[get]\n" +
                "    req:\n" +
                "        id 用户id\n" +
                "    resp:\n" +
                "        nickName 昵称\n" +
                "        aims 目标积分\n" +
                "        check 是否签到\n" +
                "        integral 总积分\n" +
                "        day 今日积分\n" +
                "        continuous 连续签到天数\n" +
                "#### 签到 只有今日得分达到了目标才可以签到\n" +
                "    url:check[post]\n" +
                "    req:\n" +
                "    resp:\n" +
                "### 题解相关 /api/v1/answer\n" +
                "#### 查看所有题解\n" +
                "    url:get[get]\n" +
                "    req:\n" +
                "    resp:\n" +
                "        id 题解编号\n" +
                "        userId 用户id\n" +
                "        nickName 用户昵称\n" +
                "        number 题号\n" +
                "        date 发布时间\n" +
                "        mark 题解评分\n" +
                "        title 题目标题\n" +
                "        slug 题目url\n" +
                "        difficulty 难度\n" +
                "        tags 标签\n" +
                "#### 查看某道题目的题解\n" +
                "    url:getByTitle\n" +
                "    req:\n" +
                "        title 标题\n" +
                "    resp:\n" +
                "        同get接口\n" +
                "#### 查看某人的题解\n" +
                "    url:getByUser\n" +
                "    req:\n" +
                "        userId 用户id\n" +
                "    resp:\n" +
                "        同get接口\n" +
                "#### 以上三个接口查看题解个数，原url后面加上count，参数不传分页，其他不变。返回值只有一个数字\n" +
                "#### 发布题解 注意，同一个用户发布相同题解的题目只算一次积分\n" +
                "    url:publish[post]\n" +
                "    req;\n" +
                "        number 题号\n" +
                "        content 内容\n" +
                "    resp:\n" +
                "#### 获取题目内容\n" +
                "    url:query[get]\n" +
                "    req:\n" +
                "        slug url拼接\n" +
                "    resp:\n" +
                "        id 题号\n" +
                "        title 标题\n" +
                "        content 内容\n" +
                "        difficulty 难度\n" +
                "        tags 标签\n" +
                "### 积分相关 /api/v1/integral\n" +
                "#### 查看当日排名\n" +
                "    url:day[get]\n" +
                "    req:\n" +
                "        type 顺序[desc|asc]\n" +
                "    resp:\n" +
                "        id 用户id\n" +
                "        nickName 用户昵称\n" +
                "        count 积分        \n" +
                "#### 查看整体排名\n" +
                "    url:all[get]\n" +
                "    req:\n" +
                "        type 顺序[desc|asc]\n" +
                "    resp:\n" +
                "        id 用户id\n" +
                "        nickName 用户昵称\n" +
                "        count 积分\n" +
                "#### 查看签到榜\n" +
                "    url:check[desc|asc]\n" +
                "    resp:\n" +
                "        id 用户id\n" +
                "        nickName 用户昵称\n" +
                "        count 天数";
        FileWriter fw = new FileWriter("/home/wjx/desktop/a.html");
        fw.write(new PegDownProcessor(Integer.MAX_VALUE)
                .markdownToHtml(s));
        fw.flush();
        fw.close();
    }
}

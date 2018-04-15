# 山东大学ING工作室
## 全局约定
    1、如无特殊说明：简单request类型是formdata，参数涉及数组或者其他非简单类型数据的request类型是JSON，response类型是JSON
    2、返回值形式：{status:(请求的状态),message:(一个简单短语解释状态码),data:{返回的数据object}}
       状态码：
        200表示请求成功
        300 表示请求失败
        400 表示没有权限
       所有涉及到分页的URL，统一请求参数：
        page:第几页
        offsets:偏移量
        rows：每页多少个
    3、所有返回有/无的接口，返回值统一是{success:m} m可取值为0，1 其中0代表无1代表有
    4、全局约定中声明好的返回数据，在本文档中具体接口中留空
    5、url统一前缀为 /api/v1
    6、静态资源问题：URL统一前缀为 /resource/
## 接口定义
### 用户相关 /api/v1/user
#### 验证是否注册
    url:exist[get]
    req:
        mail 邮箱
    resp:

#### 注册
    url:register[post]
    req:
        mail 邮箱
        nickName 昵称
        password 密码
        aims 目标得分
    resp:
#### 登录
    url:login[post]
    req:
        mail 邮箱
        password 密码
    resp:
        id 用户id (登陆成功)
        error0 (没有通过邮箱验证)
        error1 (用户名或密码错误)
#### 用户的基本信息
    url:info[get]
    req:
        id 用户id
    resp:
        nickName 昵称
        aims 目标积分
        check 是否签到
        integral 总积分
        day 今日积分
        continuous 连续签到天数
#### 签到 只有今日得分达到了目标才可以签到
    url:check[post]
    req:
    resp:
### 题解相关 /api/v1/answer
#### 查看所有题解
    url:get[get]
    req:
    resp:
        id 题解编号
        userId 用户id
        nickName 用户昵称
        number 题号
        date 发布时间
        mark 题解评分
        title 题目标题
        slug 题目url
        difficulty 难度
        tags 标签
#### 查看某道题目的题解
    url:getByTitle
    req:
        title 标题
    resp:
        同get接口
#### 查看某人的题解
    url:getByUser
    req:
        userId 用户id
    resp:
        同get接口
#### 以上三个接口查看题解个数，原url后面加上count，参数不传分页，其他不变。返回值只有一个数字
#### 发布题解 注意，同一个用户发布相同题解的题目只算一次积分
    url:publish[post]
    req;
        number 题号
        content 内容
    resp:
#### 获取题目内容
    url:query[get]
    req:
        slug url拼接
    resp:
        number 题号
        title 标题
        content 内容
        difficulty 难度
        tags 标签
### 积分相关 /api/v1/integral
#### 查看当日排名
    url:day[get]
    req:
        type 顺序[desc|asc]
    resp:
        id 用户id
        nickName 用户昵称
        count 积分        
#### 查看整体排名
    url:all[get]
    req:
        type 顺序[desc|asc]
    resp:
        id 用户id
        nickName 用户昵称
        count 积分
#### 查看签到榜
    url:check[desc|asc]
    resp:
        id 用户id
        nickName 用户昵称
        count 天数
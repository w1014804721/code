### 用户表 user：
    id 用户id
    nickName 用户昵称
    mail 用户邮箱
    certified 是否已认证
    date 注册时间
    password 密码
    integral 总积分
    aims 目标积分
    check 是否签到
    continuous 连续签到天数
#### 当日积分表 integral
    id 用户id
    date 日期
    integral 积分
#### 题解表 answer
    id 题解编号
    userId 用户id
    number 题号
    content 题解内容
    date 发布时间
    mark 题解评分
#### 题目表 question
    id 题目编号
    title 标题
    slug url拼接
    content 内容
    difficulty 难度
    tags 标签
    
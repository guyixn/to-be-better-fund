-- tbb-fund初始化sql
create schema if not exists fund_house collate utf8mb4_general_ci;

-- 基金公司表
create table if not exists fund_house.t_fund_company
(
    id              bigint unsigned zerofill not null comment '主键id' primary key,
    company_code    char(8)                  null comment '公司代码',
    company_name    varchar(100)             null comment '公司名称',
    set_date        date                     null comment '成立日期',
    fund_sum        double                   null comment '全部管理规模(亿元)',
    manager_name    varchar(100)             null comment '总经理',
    company_web_url varchar(255)             null comment '公司url',
    star            double                   null comment '天相评级',
    create_date     datetime                 null comment '创建日期',
    constraint idx_fund_company_code
    unique (company_code)
);

-- 基金排名表
create table if not exists fund_house.t_fund_rank
(
    id              int auto_increment comment 'id主键' primary key,
    fund_code       char(6)      not null comment '基金代码',
    fund_short_name varchar(100) null comment '基金简称',
    fund_date       datetime     null comment '基金日期',
    unit_net        double       null comment '基金净值',
    sum_net         double       null comment '累计净值',
    day_grow_rate   double       null comment '日增长率',
    last_week       double       null comment '上周',
    last1_month     double       null comment '过去1个月',
    last3_month     double       null comment '过去3个月',
    last6_month     double       null comment '过去6个月',
    last1_year      double       null comment '过去1年',
    last2_year      double       null comment '过去2年',
    last3_year      double       null comment '过去3年',
    this_year       double       null comment '今年以来',
    fee             double       null comment '手续费',
    buy_status      char         null comment '购买状态',
    create_date     datetime     null comment '创建日期'
);

create schema if not exists fund_ds0 collate utf8mb4_general_ci;
create schema if not exists fund_ds1 collate utf8mb4_general_ci;
create schema if not exists fund_ds2 collate utf8mb4_general_ci;

--基金历史净值表
create table t_fund_history_net0
(
    id              bigint auto_increment comment '主键id'
        primary key,
    fund_code       char(6)      null comment '基金代码',
    fund_short_name varchar(100) null comment '基金简称',
    fund_date       date         null comment '基金日期',
    unit_net        double       null comment '单位净值',
    sum_net         double       null comment '累计净值',
    day_grow_rate   double       null comment '日增长率',
    buy_status      varchar(10)  null comment '购买状态',
    red_status      varchar(10)  null comment '赎回状态',
    divide          varchar(50)  null comment '分红配送',
    create_date     datetime     null comment '爬取日期',
    constraint idx_fund_history_net
        unique (fund_code, fund_date)
);
CREATE TABLE tUsrUser (
    sUserPhone varchar(11) not null,        -- 用户电话
    sUserName varchar(20) not null,         -- 用户名称
    sPasswd varchar(100) null,              -- 用户密码
    CONSTRAINT PK_tUsrUser PRIMARY KEY (sUserPhone)
);

CREATE TABLE tTallyHead (
    sBillNo varchar(20) not null,           -- 单据号
    sMakerName varchar(20) not null,	    -- 记账人
    sMakerGoods varchar(200) not null,      -- 购买物品
    sMakerPrice numeric(19, 4) not null,    -- 记账金额
    JZDate varchar(20) not null,		    -- 记账日期
    nEatNum int not null,                   -- 几人吃饭
    sRemark varchar(100) null,			    -- 备注
    CONSTRAINT PK_tTallyHead PRIMARY KEY (sBillNo, JZDate)
);

CREATE TABLE tTallyBody (
    sBillNo varchar(20) not null,           -- 单据号
    sUserName varchar(20) not null,         -- 用户名称
    fPrice numeric(19, 4) not null,		    -- 单人金额
    JZDate varchar(20) not null,		    -- 记账日期
    CONSTRAINT PK_tTallyBody PRIMARY KEY (sBillNo, JZDate, sUserName)
);
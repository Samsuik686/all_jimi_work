package cn.concox.app.user.mapper;

import cn.concox.comm.base.rom.BaseSqlMapper;



public interface UserInfoMapper<UserMail> extends BaseSqlMapper<UserMail> {
    public String selectMailByUname(String uName);
}

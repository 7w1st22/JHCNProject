package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.EmailUser;

import java.util.List;

public interface EmailUserMapper {

    // 维护计划通知人
    public List<EmailUser> selectEmailUserList(String rolekey);

}

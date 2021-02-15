package com.aiden.user.service;

import com.aiden.user.entity.UcenterMember;
import com.aiden.user.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-02-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 根据用户的手机号与用户密码进行登录
     * 登录成功后返回jwt token
     *
     * @param member 封装用户的登录信息
     * @return token
     */
    String login(UcenterMember member);

    /**
     * 注册用户
     *
     * @param registerVo 封装注册的数据
     */
    void register(RegisterVo registerVo);

    /**
     * 从请求的token中获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    UcenterMember getUserInfoByToken(HttpServletRequest request);
}

package com.aiden.user.service;

        import com.aiden.user.entity.UcenterMember;
        import com.baomidou.mybatisplus.extension.service.IService;

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
}

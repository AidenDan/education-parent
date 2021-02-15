package com.aiden.user.service.impl;

import com.aiden.commenUtils.JwtUtils;
import com.aiden.commenUtils.MD5;
import com.aiden.exceptionHandler.EduException;
import com.aiden.user.entity.UcenterMember;
import com.aiden.user.mapper.UcenterMemberMapper;
import com.aiden.user.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-02-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 根据用户的手机号与用户密码进行登录
     * 登录成功后返回jwt token
     *
     * @param member 封装用户的登录信息
     * @return token
     */
    @Override
    public String login(UcenterMember member) {
        // 先对登录参数进行校验 不合规就抛异常
        if (StringUtils.isEmpty(member.getMobile()) || StringUtils.isEmpty(member.getPassword())) {
            throw new EduException(20001, "登录失败,用户名和密码不能为空");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", member.getMobile());
        wrapper.eq("password", MD5.encrypt(member.getPassword()));
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new EduException(20001, "该用户不存在");
        }
        // 判断用户是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new EduException(20001, "登录失败,该用户已被禁用");
        }
        // 验证通过以后返回token
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }
}

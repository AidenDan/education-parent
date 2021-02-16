package com.aiden.user.service.impl;

import com.aiden.commenUtils.JwtUtils;
import com.aiden.commenUtils.MD5;
import com.aiden.exceptionHandler.EduException;
import com.aiden.user.entity.UcenterMember;
import com.aiden.user.entity.vo.RegisterVo;
import com.aiden.user.mapper.UcenterMemberMapper;
import com.aiden.user.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-02-15
 */

@Slf4j
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

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

    /**
     * 注册用户
     *
     * @param registerVo 封装注册的数据
     */
    @Override
    public void register(RegisterVo registerVo) {
        if (StringUtils.isEmpty(registerVo.getMobile())
                || StringUtils.isEmpty(registerVo.getCode())
                || StringUtils.isEmpty(registerVo.getPassword())
                || StringUtils.isEmpty(registerVo.getNickname())) {
            throw new EduException(20001, "注册参数不能为空");
        }

        // 比较code输入是否正确
        String code = (String) redisTemplate.opsForValue().get(registerVo.getMobile());
        log.info("手机号为:::{}", registerVo.getMobile());
        if (code == null || !code.equals(registerVo.getCode())) {
            throw new EduException(20001, "验证码输入错误");
        }

        // 判断数据库中是否该手机号是否已经注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", registerVo.getMobile());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new EduException(20001, "该手机号已经被注册");
        }

        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo, ucenterMember);
        ucenterMember.setPassword(MD5.encrypt(registerVo.getPassword()));

        // 给一个默认头像
        ucenterMember.setAvatar("http://skywalker-bucket-01.oss-cn-beijing.aliyuncs.com/2021/01/30/35c2d73e0c564310866cef8d1c76fbd7.png");
        this.save(ucenterMember);
    }

    /**
     * 从请求的token中获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    @Override
    public UcenterMember getUserInfoByToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = baseMapper.selectById(memberId);
        member.setPassword(null);
        return member;
    }

    /**
     * 根据微信id查询用户
     *
     * @param openid 微信id
     * @return 用户
     */
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }
}

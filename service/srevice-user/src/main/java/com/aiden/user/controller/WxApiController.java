package com.aiden.user.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.commenUtils.JwtUtils;
import com.aiden.exceptionHandler.EduException;
import com.aiden.user.entity.UcenterMember;
import com.aiden.user.service.UcenterMemberService;
import com.aiden.user.utils.HttpClientUtils;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-16 13:18:43
 */

@Api(description = "获取二维码接口")
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    UcenterMemberService ucenterMemberService;

    @Value("${wx.open.app_id}")
    private String openAppId;

    @Value("${wx.open.app_secret}")
    private String openAppSecret;

    @Value("${wx.open.redirect_url}")
    private String openRedirectUrl;

    @GetMapping("/getCode")
    @ApiOperation(value = "请求获取微信二维码")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = openRedirectUrl; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new EduException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //一般情况下会使用一个随机数
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟
        //生成qrcodeUrl
        String codeUrl = String.format(
                baseUrl,
                openAppId,
                redirectUrl,
                state);
        return "redirect:" + codeUrl;
    }

    @GetMapping("/callback")
    @ApiOperation(value = "扫描二维码成功后的回调请求，获取扫码人的信息")
    public String callBack(@RequestParam("code") String code, @RequestParam("state") String state) {
        // code 是临时票据
        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                openAppId,
                openAppSecret,
                code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new EduException(20001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = ucenterMemberService.getByOpenid(openid);
        if (member == null) {
            System.out.println("新用户注册");
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new EduException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");
            // 扫码之后自动注册,向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            ucenterMemberService.save(member);
        }
        // 登录成功后根据用户信息获取一个jwtToken给前端
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}

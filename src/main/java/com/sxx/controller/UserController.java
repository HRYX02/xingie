package com.sxx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxx.common.R;
import com.sxx.entity.User;
import com.sxx.service.UserService;
import com.sxx.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-10-20:33
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpSession session, @RequestBody User user){
        // 获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode4String(4).toString();
            log.info("得到的验证码："+code);
            // 调用阿里云提供的短语服务API完成发送
            // SMSUtils.sendMessage("学习瑞吉外卖","SMS_460770006",phone,code);

            // 需要将生成的验证码保存到Session
//            session.setAttribute(phone,code);

            // 将生成的验证码缓存到Redis中并设置有效期5分钟
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);

            return R.success("发送成功");
        }
        return R.error("发送失败");
    }

    /**
     * @description 移动端用户登录
     */
    @PostMapping("/login")
    private R<User> login(HttpSession session, @RequestBody Map user){

        // 获取手机号
        String phone = user.get("phone").toString();
        // 获取验证码
        String code = user.get("code").toString();
        // 从Session中获取保存的验证码
//        String codeInSession = (String)session.getAttribute(phone);

        // 从Redis中获取缓存的验证码
        String codeInSession = (String) redisTemplate.opsForValue().get(phone);

        // 进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInSession != null && codeInSession.equals(code)) {
            // 如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User one = userService.getOne(queryWrapper);
            if (one == null) {
                // 判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                one = new User();
                one.setPhone(phone);
                userService.save(one);
            }
            session.setAttribute("userResult",one.getId());

            //如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(one);
        }
        return R.error("短信发送失败");
    }
}

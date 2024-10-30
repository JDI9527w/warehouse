package com.learn.controller;

import com.google.code.kaptcha.Producer;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.LoginUser;
import com.learn.DTO.Result;
import com.learn.entity.User;
import com.learn.service.UserService;
import com.learn.util.DigestUtil;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {
    @Resource(name = "kaptchaProducer")
    private Producer kaptchaProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/captcha/captchaImage")
    public void kaptchaImage(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            String text = kaptchaProducer.createText();
            BufferedImage image = kaptchaProducer.createImage(text);
            redisTemplate.opsForValue().set(text, "", 60 * 30, TimeUnit.SECONDS);
            response.setContentType("image/jpeg");
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser){
        String key = loginUser.getVerificationCode();
        Boolean keyFlag = redisTemplate.hasKey(key);
        if (!keyFlag){
            return Result.err(403,"验证码错误，请重新输入");
        }
        User user = userService.getUserByCode(loginUser.getUserCode());
        if (user == null){
            return Result.err(403,"用户名或密码错误，请重新输入");
        }
        String md5pwd = DigestUtil.hmacSign(loginUser.getUserPwd());
        if (!md5pwd.equals(user.getUserPwd())){
            return Result.err(403,"用户名或密码错误，请重新输入");
        }
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUserId(user.getUserId());
        currentUser.setUserName(user.getUserName());
        currentUser.setUserCode(user.getUserCode());
        String token = tokenUtils.loginSign(currentUser, loginUser.getUserPwd());
        redisTemplate.delete(loginUser.getVerificationCode());
        return Result.ok("登录成功",token);
    }

    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("Token");
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Boolean delete = redisTemplate.delete(token);
        if (delete) {
            return Result.ok("注销成功");
        }
        return Result.err(403, "注销失败.");
    }

    @GetMapping("/curr-user")
    public Result currUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }
}

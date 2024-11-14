package com.learn.controller;

import com.google.code.kaptcha.Producer;
import com.learn.DTO.LoginUser;
import com.learn.DTO.Result;
import com.learn.entity.User;
import com.learn.service.UserService;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/captcha/captchaImage")
    public void kaptchaImage(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            String text = kaptchaProducer.createText();
            BufferedImage image = kaptchaProducer.createImage(text);
            redisTemplate.opsForValue().set(text, "", 60, TimeUnit.SECONDS);
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
    public Result login(@RequestBody LoginUser loginUser) {
        return userService.login(loginUser);
    }

    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("Token");
        Boolean delete = redisTemplate.delete(token);
        if (delete) {
            return Result.ok("注销成功");
        }
        return Result.err(403, "注销失败.");
    }

    @GetMapping("/curr-user")
    public Result currUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        User user = tokenUtils.getCurrentUser(token);
        return Result.ok(user);
    }
}

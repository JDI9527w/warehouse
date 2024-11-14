package com.learn.config;

import com.learn.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 验证码接口,登录接口,文件上传接口开放访问.
                .antMatchers("/captcha/captchaImage").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/product/img-upload").permitAll()
                // 其余接口配置访问权限,参数见上面注释.
                .antMatchers("/curr-user").authenticated()
                .antMatchers("/auth/*").authenticated()
                .antMatchers("/instore/*").authenticated()
                .antMatchers("/outstore/*").authenticated()
                .antMatchers("/product/*").authenticated()
                .antMatchers("/productCategory/*").authenticated()
                .antMatchers("/purchase/*").authenticated()
                .antMatchers("/role/*").authenticated()
                .antMatchers("/statistics/*").authenticated()
                .antMatchers("/store/*").authenticated()
                .antMatchers("/user/*").authenticated()
                .anyRequest().authenticated();
        // 因为传入json 格式数据,走登录验证接口,所以不需要配置.
        // 如果配置,会不走接口,走默认的权限验证,然后执行实现的 loadUserByUsername 方法 并且要求请求参数是表单格式,否则接收参数为null.
//        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .usernameParameter("userCode")
//                .passwordParameter("userPwd")
//                .successHandler(myAuthenticationSuccessHandler);
//                .defaultSuccessUrl("/home");
//                .successHandler(myAuthenticationSuccessHandler);
        http.logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        // CSRF禁用，因为不使用session
        http.csrf().disable();
        // 关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 添加token验证过滤器,
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 用于密码编码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 用此对象进行用户验证
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

package cn.webapp.configuration;

import cn.webapp.interceptor.JwtAuthenticationEntryPoint;
import cn.webapp.interceptor.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.security.SecureRandom;

/**
 * Created by huangYi on 2018/8/26
 * 安全设置
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 这里处理密码加密和库里比较验证
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //  2018/9/5  新增对/layer /img /js /css 不拦截
        web.ignoring().antMatchers("/druid/**","/plugin/**","/swf/**","/audio/**","/fonts/**","/layer/**","/layui/**","/img/**","/js/**","/css/**","/comm/**", "/swagger/**", "/webjars/**", "/swagger-resources/**", "/swagger-ui.html");
    }
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //关闭csrf验证 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()

                //添加自定义异常入口，处理accessdeine异常
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                //基于token，所以不需要session  如果基于session 则表使用这段代码
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                //对请求进行认证  url认证配置顺序为：1.先配置放行不需要认证的 permitAll() 2.然后配置 需要特定权限的 hasRole() 3.最后配置 anyRequest().authenticated()
                .authorizeRequests()

                //对preflight放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                // 其他请求都需要进行认证,认证通过够才能访问
                .anyRequest().authenticated();

        //添加JwtAuthenticationFilter 将JwtAuthenticationFilter放在UsernamePasswordAuthenticationFilter之前
        //若new JwtAuthenticationFilter()方式 并且JwtAuthenticationFilter未注入容器,只会对为未忽略路径过滤1次，对忽略路径不起作用
        //若new JwtAuthenticationFilter()方式 并且JwtAuthenticationFilter注入容器,会对为未忽略路径过滤2次（容器一次，spring Security一次），对忽略路径过滤1次（容器一次）
        //若authenticationTokenFilterBean()方式,bean注入了容器，对未忽略文件过滤1次，对忽略文件过滤1次（加入Security的自定义过滤器 是注入过容器的,是同一个bean  并且继承OncePerRequestFilter，所以只执行一次）
        //filter注入几次执行几次
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();


    }
}

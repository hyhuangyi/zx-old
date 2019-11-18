package cn.webapp.configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class DruidConfig {

	/*将设置参数的druid的数据源注册到IOC容器中*/

	@ConfigurationProperties(prefix = "spring.datasource.druid")
	@Bean
	public DataSource druid() {
		DruidDataSource druidDataSource = new DruidDataSource();
		// wallFilter  解决批量更新问题
		List<Filter> filters = new ArrayList<>();
		filters.add(wallFilter());
		druidDataSource.setProxyFilters(filters);
		return druidDataSource;
	}

	@Bean
	public WallFilter wallFilter(){
		WallFilter wallFilter=new WallFilter();
		wallFilter.setConfig(wallConfig());
		return wallFilter;

	}
	@Bean
	public WallConfig wallConfig(){
		WallConfig config =new WallConfig();
		config.setMultiStatementAllow(true);//允许一次执行多条语句
		config.setNoneBaseStatementAllow(true);//允许非基本语句的其他语句
		return config;
	}

	/*配置一个管理后台的servlet*/
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String, String> map = new HashMap<String, String>();
		//用户名
		map.put("loginUsername", "zx");
		//密码
		map.put("loginPassword", "123456");
		//IP白名单(没有配置或者为空，则允许所有访问)
		map.put("allow", "");
		//IP黑名单(存在共同时，deny优先于allow)
		map.put("deny", "");
		servletRegistrationBean.setInitParameters(map);
		return servletRegistrationBean;
	}

	/*配置一个web监控的filter*/
	@Bean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		Map<String, String> map = new HashMap<>();
		map.put("exclusions", "*.js,*.css,/druid/*");
		filterRegistrationBean.setInitParameters(map);
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegistrationBean;
	}
}
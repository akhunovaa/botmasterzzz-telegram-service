package com.botmasterzzz.telegram.config;

import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.generic.BotSession;
import com.botmasterzzz.bot.updatesreceivers.DefaultBotSession;
import com.google.gson.Gson;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Properties;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan({"com.botmasterzzz.telegram"})
@Profile("dev")
public class ApplicationConfig implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String BASE_PACKAGES = "com.botmasterzzz.telegram";

    private static final String PROPERTY_NAME_ENTITY_LOCATION = "com.botmasterzzz.telegram.entity";

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Lazy
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("app.db.worker.driver"));
        dataSource.setMaxWaitMillis(60000);
        dataSource.setMinIdle(6);
        dataSource.setUrl(environment.getProperty("app.db.worker.url"));
        dataSource.setUsername(environment.getProperty("app.db.worker.login"));
        dataSource.setPassword(environment.getProperty("app.db.worker.password"));
        return dataSource;
    }

    @Bean
    public Session email() {
        String username = environment.getProperty("mail.user");
        String password = environment.getProperty("mail.password");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.yandex.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.yandex.com");
        prop.put("mail.mime.charset", "utf-8");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan(BASE_PACKAGES);
        servletContext.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(servletContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        servletContext.setInitParameter("spring.profiles.active", "dev");
    }

//    @Bean
//    @DependsOn("dataSource")
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource());
//        liquibase.setChangeLog("classpath:liquibase/db-migrations/changelog.xml");
//        return liquibase;
//    }

//    @DependsOn("liquibase")
    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITY_LOCATION);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }


    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        return multipartResolver;
    }

    @Bean
    public DefaultBotOptions botOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public BotSession botSession() {
        BotSession botSession = new DefaultBotSession();
        botSession.setToken(environment.getProperty("token"));
        return botSession;
    }

}

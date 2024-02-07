package com.optum.payment.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.optum.payment.system.entities.User;
import com.optum.payment.system.global.InstallConstants;
import com.optum.payment.system.services.UserService;
import com.optum.payment.system.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import java.util.Optional;
import java.util.TimeZone;

import static com.optum.payment.system.global.InstallConstants.PASS;
import static com.optum.payment.system.global.InstallConstants.USER;


@SpringBootApplication(exclude = {SqlInitializationAutoConfiguration.class})
public class PaymentSystemApplication implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(PaymentSystemApplication.class.getName());

    private final ApplicationContext applicationContext;
    private final UserService userService;


    @Autowired
    public PaymentSystemApplication(UserService userService, ApplicationContext applicationContext) {
        this.userService = userService;
        this.applicationContext = applicationContext;
    }


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(InstallConstants.DEFAULT_TIMEZONE));

        log.info("Starting application...");
        SpringApplication.run(PaymentSystemApplication.class, args);
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {

        var templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {

        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }


    public void run(String... args) throws JsonProcessingException {
        User user = new User(USER, PASS);

        Optional<User> value = userService.findByUsername(USER);
        if (value.isPresent()) {
            User one = value.get();
            if(log.isDebugEnabled()) {
                log.info("user one = {}", JsonUtils.toJson(one));
            }
        } else {
            userService.save(user);
            log.info("springUser saved...");
        }
    }


}
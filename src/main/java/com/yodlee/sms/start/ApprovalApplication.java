package com.yodlee.sms.start;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan("${base.package}")
@EnableJpaRepositories("${base.package}")
@EnableMongoRepositories("${base.package}")
@EntityScan("${base.package}")
@Retention(RetentionPolicy.RUNTIME)
public @interface ApprovalApplication {

}

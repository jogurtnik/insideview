package uk.co.punishell.insideview.model.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Getter
@Configuration
@PropertySource("classpath:jsh-props.properties")
public class JSHLoginProps {

    private Environment env;

    private String login;
    private String password;

    @Autowired
    public JSHLoginProps(Environment env) {
        this.env = env;
        this.setLogin(this.env);
        this.setPassword(this.env);
    }

    private void setLogin(Environment env) {
        this.login = env.getProperty("jsh-login");
    }

    private void setPassword(Environment env) {
        this.password = env.getProperty("jsh-password");
    }

    @Bean
    @Primary
    public JSHCredentials jshCredentials() {
        return new JSHCredentials(this.login, this.password);
    }
}

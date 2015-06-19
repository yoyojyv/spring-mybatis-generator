package me.yoyojyv.springmybatis.codegen

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.SimpleCommandLinePropertySource

import javax.annotation.PostConstruct

/**
 * Created by yoyojyv on 6/18/15.
 */
@Slf4j
@Configuration
@ComponentScan
@EnableAutoConfiguration
class Application {

    @Autowired
    Environment env

    @PostConstruct
    void initApplication() {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration")
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()))
        }

    }

    /**
     * Main method, used to run the application.
     */
    public static void main(String... args) {

        SpringApplication app = new SpringApplication(Application.class)
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args)
        Environment env = app.run(args).getEnvironment()

        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
            "Local: \t\thttp://127.0.0.1:{}\n\t" +
            "External: \thttp://{}:{}\n----------------------------------------------------------",
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"))
    }
}

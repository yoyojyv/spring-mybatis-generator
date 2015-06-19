package me.yoyojyv.springmybatis.codegen.config

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.MimeMappings
import org.springframework.boot.context.embedded.ServletContextInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

import javax.servlet.ServletContext
import javax.servlet.ServletException

/**
 * Created by yoyojyv on 6/18/15.
 */
@Slf4j
@Configuration
class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    @Autowired
    Environment env

    @Override
    void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
        mappings.add("html", "text/html;charset=utf-8");
        // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
        mappings.add("json", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
    }

    @Override
    void onStartup(ServletContext servletContext) throws ServletException {

        log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));

        log.info("Web application fully configured");
    }
}

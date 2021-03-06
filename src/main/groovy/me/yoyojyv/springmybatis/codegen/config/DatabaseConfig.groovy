package me.yoyojyv.springmybatis.codegen.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.util.logging.Slf4j
import org.springframework.boot.bind.RelaxedPropertyResolver
import org.springframework.context.ApplicationContextException
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

import javax.sql.DataSource

/**
 * Created by yoyojyv on 6/18/15.
 */
@Slf4j
@Configuration
// @EnableTransactionManagement
class DatabaseConfig implements EnvironmentAware {

    Environment env
    RelaxedPropertyResolver propertyResolver

    @Override
    void setEnvironment(Environment env) {
        this.env = env
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.")
    }

    @Bean
    DataSource dataSource() {
        log.debug("Configuring Datasource");
        if (propertyResolver.getProperty("url") == null && propertyResolver.getProperty("databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                "cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()))

            throw new ApplicationContextException("Database connection pool is not configured correctly")
        }

        HikariConfig config = new HikariConfig()
        config.setDataSourceClassName(propertyResolver.getProperty("dataSourceClassName"))
        if (propertyResolver.getProperty("url") == null || "".equals(propertyResolver.getProperty("url"))) {
            config.addDataSourceProperty("databaseName", propertyResolver.getProperty("databaseName"))
            config.addDataSourceProperty("serverName", propertyResolver.getProperty("serverName"))
        } else {
            config.addDataSourceProperty("url", propertyResolver.getProperty("url"))
        }
        config.addDataSourceProperty("user", propertyResolver.getProperty("username"))
        config.addDataSourceProperty("password", propertyResolver.getProperty("password"))

        //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(propertyResolver.getProperty("dataSourceClassName"))) {
            config.addDataSourceProperty("cachePrepStmts", propertyResolver.getProperty("cachePrepStmts", "true"));
            config.addDataSourceProperty("prepStmtCacheSize", propertyResolver.getProperty("prepStmtCacheSize", "250"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", propertyResolver.getProperty("prepStmtCacheSqlLimit", "2048"));
            config.addDataSourceProperty("useServerPrepStmts", propertyResolver.getProperty("useServerPrepStmts", "true"));
        }
        return new HikariDataSource(config)
    }


}

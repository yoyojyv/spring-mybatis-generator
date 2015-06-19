package me.yoyojyv.springmybatis.codegen.repository

import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

import javax.sql.DataSource
/**
 * Created by yoyojyv on 6/19/15.
 */
@Component
class MariaDbRepository {

    @Autowired
    Environment env

    @Autowired
    DataSource dataSource

    def dbHandle

     /**
     * sql instance 를 리턴
     *
     * @return
     */
    def getSql() {
        if (dbHandle) {
            return dbHandle
        } else {
            return getNewSql()
        }
    }

    /**
     * 새로만들어진 sql 을 가져옴
     *
     * @return
     */
    def getNewSql() {

//        def url = "jdbc:mariadb://${env.getProperty('database.mariadb.host')}/${env.getProperty('database.mariadb.defaultDatabase')}"
//
//        HikariConfig config = new HikariConfig()
//        config.setDataSourceClassName(env.getProperty("database.mariadb.dataSourceClassName"))
//        config.addDataSourceProperty("url", url)
//        config.addDataSourceProperty("user", env.getProperty('database.mariadb.username'))
//        config.addDataSourceProperty("password", env.getProperty('database.mariadb.password'))
//
//        def sql = Sql.newInstance(new HikariDataSource(config))

        def sql = Sql.newInstance(dataSource)
        dbHandle = sql
        return sql
    }


    /**
     * 해당 DB 의 모든 테이블 내역을 가져옴
     *
     * @return
     */
    def getAllTables() {

        // return sql.rows("SHOW TABLES")
        def list = []
        sql.eachRow("SHOW TABLES") { row ->
            list << row[0]
        }
        return list
    }

    /**
     * 해당 DB.table 의 모든 컬럼을 가져옴
     *
     * @param tableName
     * @return
     */
    def getAllColumns(tableName) {

        // return sql.rows("DESC " + tableName)
        def list = []
        sql.eachRow("DESC " + tableName) { row ->
            list << [field: row.Field, type: row.Type, allowNull: row.Null == "YES" ? true : false, defaultValue: row.Default, extra: row.Extra]
        }
        return list
    }


//    def dbHandles = [:]
//
//    /**
//     * sql instance 를 리턴
//     *
//     * @param databaseName
//     * @return
//     */
//    def getSql(databaseName) {
//        if (dbHandles?."${databaseName}") {
//            return dbHandles?."${databaseName}"
//        } else {
//            return getNewSql(databaseName)
//        }
//    }
//
//    /**
//     * 새로만들어진 sql 을 가져옴
//     *
//     * @return
//     */
//    def getNewSql(databaseName) {
//
//        def url = "jdbc:mariadb://${env.getProperty('database.mariadb.host')}/${env.getProperty('database.mariadb.defaultDatabase')}"
//
//        HikariConfig config = new HikariConfig()
//        config.setDataSourceClassName(env.getProperty("database.mariadb.dataSourceClassName"))
//        config.addDataSourceProperty("url", url)
//        config.addDataSourceProperty("user", env.getProperty('database.mariadb.username'))
//        config.addDataSourceProperty("password", env.getProperty('database.mariadb.password'))
//
//        def sql = Sql.newInstance(new HikariDataSource(config))
//        dbHandles."${databaseName}" = sql
//        return sql
//    }
//
//    /**
//     * 모든 database 목록을 가져옴
//     * @return
//     */
//    def getAllDatabases() {
//        def defaultDatabase = env.getProperty('database.mariadb.defaultDatabase')
//        def sql = getSql(defaultDatabase)
//
//        // return sql.rows("SHOW DATABASES")
//
//        def list = []
//        sql.eachRow("SHOW DATABASES") { row ->
//            list << row[0]
//        }
//        return list
//    }



}

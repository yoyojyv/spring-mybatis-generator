package me.yoyojyv.springmybatis.codegen.controller

import me.yoyojyv.springmybatis.codegen.gen.GenFileWriter
import me.yoyojyv.springmybatis.codegen.gen.GenerateForm
import me.yoyojyv.springmybatis.codegen.gen.GroovyDomainGen
import me.yoyojyv.springmybatis.codegen.gen.GroovyMybatisMapperGen
import me.yoyojyv.springmybatis.codegen.repository.MariaDbRepository
import org.apache.commons.lang3.text.WordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by yoyojyv on 6/19/15.
 */
@RestController
@RequestMapping("/california")
class CaliforniaResources {

    @Autowired
    MariaDbRepository mariaDbRepository

    @Autowired
    GroovyDomainGen domainGen

    @Autowired
    GroovyMybatisMapperGen mybatisMapperGen

    @Autowired
    GenFileWriter fileWriter


//    @RequestMapping("/databases")
//    def databases() {
//        return mariaDbRepository.allDatabases
//    }

    @RequestMapping("/tables")
    def tables() {
        return mariaDbRepository.getAllTables()
    }

    @RequestMapping("/tables/{tableName}")
    def columns(@PathVariable String tableName) {
        return mariaDbRepository.getAllColumns(tableName)
    }


    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    def generate(@RequestBody GenerateForm f) {

        def packagePrefixToPath = f.packagePrefix.replaceAll(',', '/')

        f?.sourceTypes.each { t ->
            if (t == "domain") {
                String path = "${f.createPath}/core/src/main/java/${packagePrefixToPath}/domain/${WordUtils.capitalize(f.tableName)}.groovy"
                fileWriter.writeFile(path, domainGen.generateCode(f))
            } else if (t == "mybatisMapper") {
                String path = "${f.createPath}/core/src/main/java/${packagePrefixToPath}/repository/mybatis/${WordUtils.capitalize(f.tableName)}Mapper.groovy"
                fileWriter.writeFile(path, mybatisMapperGen.generateCode(f))
            }
        }
        return true
    }




}

package me.yoyojyv.springmybatis.codegen.gen

import me.yoyojyv.springmybatis.codegen.repository.MariaDbRepository
import org.apache.commons.lang3.text.WordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by yoyojyv on 6/19/15.
 */
@Component
class GroovyDomainGen {

    @Autowired
    MariaDbRepository mariaDbRepository

    def generateCode(GenerateForm f) {
        def vars = StringBuilder.newInstance()
        def columns = mariaDbRepository.getAllColumns(f.tableName)
        columns.each { c ->
            vars.append("    " + MariadbColumnConverter.columnTypeToJavaType(c.type) + " " + c.field + "\n")
        }

        def  content =
"""package ${f.packagePrefix}.domain

import groovy.transform.ToString

@ToString(includeFields = true)
class ${WordUtils.capitalize(f.tableName)} {

${vars.toString()}
}

"""

    }

}

package me.yoyojyv.springmybatis.codegen.gen

import me.yoyojyv.springmybatis.codegen.repository.MariaDbRepository
import org.apache.commons.lang3.text.WordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by yoyojyv on 6/19/15.
 */
@Component
class GroovyMybatisMapperGen {

    @Autowired
    MariaDbRepository mariaDbRepository

    def generateCode(f) {

        def domain = WordUtils.capitalize(f.tableName)

        def insertSql = StringBuilder.newInstance()


        def columns = mariaDbRepository.getAllColumns(f.tableName)
        insertSql.append("        INSERT INTO ${domain} (\n")
        columns.eachWithIndex { c, i ->

            if (i % 5 == 0) {
                insertSql.append("            ")
            }

            if (i != 0 && c.field != 'id') {
                if (i < columns.size() - 1)
                    insertSql.append(c.field + ", ")
                else
                    insertSql.append(c.field + "\n")
            }

            if ((i + 1) % 5 == 0)
                insertSql.append("\n")

        }

        insertSql.append("        ) VALUES (\n")
        columns.eachWithIndex { c, i ->

            if (i % 5 == 0) {
                insertSql.append("            ")
            }

            if (c.field != 'id') {
                if (i < columns.size() - 1) {
                    if (c.field == "dateCreated" || c.field == "lastUpdated") {
                        insertSql.append("NOW(), ")
                    } else {
                        insertSql.append("#{" + c.field + "}, ")
                    }
                } else {
                    if (c.field == "dateCreated" || c.field == "lastUpdated") {
                        insertSql.append("NOW()\n")
                    } else {
                        insertSql.append("NOW(), ")
                    }
                }
            }
            if ((i + 1) % 5 == 0)
                insertSql.append("\n")

        }
        insertSql.append("        )")

        def updateSql = StringBuilder.newInstance()
        updateSql.append("        UPDATE ${domain} SET \n")

        columns.eachWithIndex { c, i ->

            if (i % 5 == 0) {
                insertSql.append("            ")
            }

            if (i != 0 && (c.field != 'id' || c.field != dateCreated)) {
                if (i < columns.size() - 1) {
                    if (c.field == "lastUpdated") {
                        updateSql.append("            " + c.field + " = NOW(),\n")
                    } else {
                        updateSql.append("            " + c.field + " = #{" + c.field + "},\n")
                    }
                } else {
                    if (c.field == "lastUpdated") {
                        updateSql.append("            " + c.field + " = NOW()\n")
                    } else {
                        updateSql.append("            " + c.field + " = #{" + c.field + "}\n")
                    }

                }
            }

        }
        updateSql.append("        WHERE id = #{id} \n")


        def content =
            """package ${f.packagePrefix}.repository.mybatis

import ${f.packagePrefix}.domain.${domain}
import ${f.packagePrefix}.repository.Mapper
import org.apache.ibatis.annotations.*

@Mapper
interface ${domain}Mapper {

    // ======================== 기본 CRUD ========================

    @Select("SELECT * FROM ${domain} WHERE id = #{id}")
    def ${domain} findOne(@Param("id") int id)

    @Select("SELECT * FROM ${domain}")
    def List<${domain}> findAll()

    @Insert(\"\"\"
${insertSql.toString()}
        \"\"\")
    @Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true)
    def void insert(${domain} ${WordUtils.uncapitalize(domain)})

    @Update(\"\"\"
${updateSql.toString()}
        \"\"\")
    @Options(flushCache = true)
    def void update(${domain} ${WordUtils.uncapitalize(domain)})

    @Delete("DELETE FROM ${domain} WHERE id = #{id}")
    @Options(flushCache = true)
    def void delete(@Param("id") long id)

}

"""
        return content
    }

}

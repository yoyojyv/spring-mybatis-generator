package me.yoyojyv.springmybatis.codegen.gen

import org.springframework.stereotype.Component

/**
 * Created by yoyojyv on 6/19/15.
 */
@Component
class GenFileWriter {

    def writeFile(filePath, content) {
        File parentDir = new File(filePath).parentFile

        if (!parentDir.exists()) {
            parentDir.mkdirs()
        }

        File f = new File(filePath)
//        f.withWriter('UTF-8') { writer ->
//            writer.write(content)
//        }
        def w = f.newWriter()
        w << content
        w.close()
    }

}

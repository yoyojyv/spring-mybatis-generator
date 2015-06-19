package me.yoyojyv.springmybatis.codegen.gen

import groovy.transform.ToString

/**
 * Created by yoyojyv on 6/19/15.
 */
@ToString(includeFields = true)
class GenerateForm {

    String createType           // 파일 생성 타입
    String createPath           // 파일 생성 경로
    String packagePrefix        // 패키지 prefix
    String tableName            // 테이블
    List<String> sourceTypes // 생성할 소스 타입들

}

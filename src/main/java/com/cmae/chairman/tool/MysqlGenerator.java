package com.cmae.chairman.tool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/cmae", "root", "cmae8877132")
                .globalConfig(builder -> {
                    builder.author("TAMTAM") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("C://Users//chair//IdeaProjects//chairman//src//main//java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.cmae.chairman") // 设置父包名
//                                .moduleName("case") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "C://Users//chair//IdeaProjects//chairman//src//main//java")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder -> {
                    builder.addInclude("job");// 设置需要生成的表名
                    // 实体策略配置
                    builder.entityBuilder().enableLombok().enableTableFieldAnnotation();
                    // Mapper 策略配置
                    builder.mapperBuilder().enableBaseColumnList().enableBaseResultMap();
                    // Service 策略配置
                    builder.serviceBuilder();
                    // Controller 策略配置
                    builder.controllerBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

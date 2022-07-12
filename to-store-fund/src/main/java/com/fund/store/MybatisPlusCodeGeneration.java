package com.fund.store;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author: tbb
 * @Date: 2022/2/13 1:10
 * @Description: MybaitsPlus代码生成器
 */
public class MybatisPlusCodeGeneration {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/fund_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "jianlai")
                .globalConfig(builder -> {
                    builder.author("tbb") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\new\\to-be-better-fund\\to-store-fund\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.fund.store") // 设置父包名
                            .moduleName("eastmoney") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\new\\to-be-better-fund\\to-store-fund\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_em_fund_list"); // 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}

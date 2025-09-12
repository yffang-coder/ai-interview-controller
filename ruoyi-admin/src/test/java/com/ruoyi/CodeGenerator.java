package com.ruoyi;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class CodeGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://10.186.37.153:3306/ai_interview",
                        "hillstone", "hIllstoneUes4Ever")
                .globalConfig(builder -> builder
                        .author("ruoyi")
                        .outputDir("D:\\aiback\\RuoYi-Vue\\ruoyi-main\\src\\main\\java")
                )
                .packageConfig(builder -> builder
                        .parent("com.ruoyi.ai")
                        .entity("domain")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude(
                                "ai_interview_userinfo",
                                "ai_interview_user",
                                "ai_interview_banner",
                                "ai_interview_category_item",
                                "ai_interview_interview_records",
                                "ai_interview_category",
                                "ai_interview_models",
                                "ai_interview_announcements",
                                "ai_interview_satisfaction_survey",
                                "wx_login")
                        .addTablePrefix("ai_interview_")
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

package com.example.config.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.config.BaseConfig;
import com.example.domain.kakeibo.Kakeibo;

@Configuration
public class MybatisCursorBatchConfig extends BaseConfig {

	// SqlSessionFactory(Mybatisで必要)
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/*
	 * MybatisCursorItemReader
	 */
	@Bean
	@StepScope
	public MyBatisCursorItemReader<Kakeibo> mybatisCursorReader() {
		
		// クエリに渡すパラメーター
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("userId", 1L);
		
		return new MyBatisCursorItemReaderBuilder<Kakeibo>() // Builderの生成
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("com.example.repository.kakeibo.KakeiboMapper.findAll")
				.parameterValues(parameterValues) // パラメーター
				.build(); // readerの生成
	}
	
	/*
	 * Stepの生成
	 */
	@Bean
	public Step exportMybatisCursorStep() throws Exception {
		
		return this.stepBuilderFactory.get("ExportMybatisCursorStep")
				.<Kakeibo, Kakeibo>chunk(10)
				.reader(mybatisCursorReader()).listener(readListener)
				.writer(csvWriter()).listener(writeListener)
				.build();
	}
	
	/*
	 * Jobの生成
	 */
	@Bean
	public Job exportMybatisCursorJob() throws Exception {
		
		return this.jobBuilderFactory.get("ExportMybatisCursorJob")
				.incrementer(new RunIdIncrementer())
				.start(exportMybatisCursorStep())
				.build();
	}
}

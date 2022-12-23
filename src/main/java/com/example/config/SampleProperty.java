package com.example.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@PropertySource("classpath:property/sample.properties")
@Getter
@ToString
@Slf4j
public class SampleProperty {

	// sample.propertiesのfile.nameを取得
	@Value("${file.name}")
	private String fileName;
	
	// sample.propertiesのfile.output.directoryを取得
	@Value("${file.output.directory}")
	private String fileOutputDirectory;
	
	/*
	 * ファイルパス生成
	 */
	public String outputPath() {
		
		String outputPath = fileOutputDirectory + File.separator + fileName;
		log.debug("outputPath = {}", outputPath);
		return outputPath;
	}
}

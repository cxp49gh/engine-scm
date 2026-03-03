package com.engine.scm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置属性
 *
 */
@Component
@ConfigurationProperties(prefix = "info.app")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class BuildInfo {

	private String name;

	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
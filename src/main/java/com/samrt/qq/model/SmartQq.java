/**
 * 
 */
package com.samrt.qq.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author chenjf6
 *
 */
@Component
@PropertySource("classpath:/config/smartQQ.properties")
public class SmartQq {
	@Value("${qq.qqName}")
	private String qqName;
	
	@Value("${qq.key}")
	private String key;
	/**
	 * @return the qqName
	 */
	public String getQqName() {
		return qqName;
	}

	/**
	 * @param qqName the qqName to set
	 */
	public void setQqName(String qqName) {
		this.qqName = qqName;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	
}

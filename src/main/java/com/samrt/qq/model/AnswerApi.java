/**
 * 
 */
package com.samrt.qq.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author microzhu
 *
 */
@Component
@PropertySource("classpath:/config/api.properties")
@Order(2)
public class AnswerApi {
	@Value("${itpk.api}")
	private String itpkApi;
	
	@Value("${itpk.key}")
	private String itpkKey;
	
	@Value("${itpk.secret}")
	private String itpkSecret;
	/**
	 * @return the itpkApi
	 */
	public String getItpkApi() {
		return itpkApi;
	}
	/**
	 * @param itpkApi the itpkApi to set
	 */
	public void setItpkApi(String itpkApi) {
		this.itpkApi = itpkApi;
	}
	/**
	 * @return the itpkKey
	 */
	public String getItpkKey() {
		return itpkKey;
	}
	/**
	 * @param itpkKey the itpkKey to set
	 */
	public void setItpkKey(String itpkKey) {
		this.itpkKey = itpkKey;
	}
	/**
	 * @return the itpkSecret
	 */
	public String getItpkSecret() {
		return itpkSecret;
	}
	/**
	 * @param itpkSecret the itpkSecret to set
	 */
	public void setItpkSecret(String itpkSecret) {
		this.itpkSecret = itpkSecret;
	}
	
}

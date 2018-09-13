package com.samrt.qq.constant;
import  com.samrt.qq.interfaces.impl.SmartAnswerItpkImpl;
import  com.samrt.qq.interfaces.impl.SmartAnswerOtherImpl;
/**
 * 
 * @author chenjf6
 *	定义不同的智能聊天
 */
public enum Answer {
	ITPK("itpk",SmartAnswerItpkImpl.class),
	OTHER("other",SmartAnswerOtherImpl.class);
	
	private Answer(String way, Class beanMane) {
		this.way = way;
		this.beanMane = beanMane;
	}
	private String way;
	private Class beanMane;
	/**
	 * @return the way
	 */
	public String getWay() {
		return way;
	}
	/**
	 * @param way the way to set
	 */
	public void setWay(String way) {
		this.way = way;
	}
	/**
	 * @return the beanMane
	 */
	public Class getBeanMane() {
		return beanMane;
	}
	/**
	 * @param beanMane the beanMane to set
	 */
	public void setBeanMane(Class beanMane) {
		this.beanMane = beanMane;
	}
	
}

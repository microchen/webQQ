/**
 * 
 */
package com.samrt.qq.interfaces.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.samrt.qq.interfaces.IDefaultAnswerSV;

/**
 * @author chenjf6
 *
 */
@Service("other")
@Configuration
public class SmartAnswerOtherImpl implements IDefaultAnswerSV {

	/* (non-Javadoc)
	 * @see com.samrt.qq.interfaces.ISmartAnswerSV#queryAnswer(java.lang.String)
	 */
	@Override
	public String queryAnswer(String contest) {
		// TODO Auto-generated method stub
		return null;
	}

}

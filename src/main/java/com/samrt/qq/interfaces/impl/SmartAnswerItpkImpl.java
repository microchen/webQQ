/**
 * 
 */
package com.samrt.qq.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.samrt.qq.interfaces.IDefaultAnswerSV;
import com.samrt.qq.model.AnswerApi;
import com.samrt.qq.utils.HttpRequestUtil;

/**
 * @author chenjf6
 *
 */
@Service("itpk")
@Configuration
@Component
public class SmartAnswerItpkImpl implements IDefaultAnswerSV {
	/* (non-Javadoc)
	 * @see com.samrt.qq.interfaces.ISmartAnswerSV#queryAnswer(java.lang.String)
	 */
	@Override
	public String queryAnswer(String contest) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("question", contest);
		dataMap.put("limit", "8");
		dataMap.put("api_key", "7d871e0dc8f56615b81a82bf8041c4cc");
		dataMap.put("api_secret", "ayaq9zew617t");
		dataMap.put("type", "json");
		return HttpRequestUtil.doGet("","http://i.itpk.cn/api.php","utf-8",dataMap).get("respResult");
	}

}

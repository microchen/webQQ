/**
 * 
 */
package com.samrt.qq.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjf6
 *	用于收集QQ组id,如果用户提交过来的数据不属于组ID就不发送
 */
public class QQGroupIdMap {
	public static Map<Long,Long> groupIdMap = new HashMap<>();
}

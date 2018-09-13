/**
 * 
 */
package com.samrt.qq.interfaces;

import java.util.List;

import com.samrt.qq.model.Group;

/**
 * @author chenjf6
 *
 */
public interface IQQInfoSV {
	/**
	 * 获取用户的群信息
	 * @return
	 */
	List<Group> getUserGroupInfo();

	void sendMsg2Group(Long groupId, String msg);
}

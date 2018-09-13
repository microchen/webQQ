/**
 * 
 */
package com.samrt.qq.interfaces.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.samrt.qq.interfaces.IQQInfoSV;
import com.samrt.qq.model.Group;
import com.samrt.qq.service.QQBaseService;

/**
 * @author chenjf6
 *
 */
@Service
public class QQInfoSVImpl  extends QQBaseService implements IQQInfoSV{

	/* (non-Javadoc)
	 * @see interfaces.QQInfoSV#getUserGroupInfo()
	 */
	@Override
	public List<Group> getUserGroupInfo() {
		// TODO Auto-generated method stub
		return super.getClient().getGroupList();
	}

	/* (non-Javadoc)
	 * @see com.samrt.qq.interfaces.IQQInfoSV#sendMsg2Group(java.lang.Long, java.lang.String)
	 */
	@Override
	public void sendMsg2Group(Long groupId, String msg) {
		// TODO Auto-generated method stub
		super.getClient().sendMessageToGroup(groupId, msg);
	}
	
}

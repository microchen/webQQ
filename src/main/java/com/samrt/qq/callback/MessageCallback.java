/**
 * 
 */
package com.samrt.qq.callback;

import com.samrt.qq.model.DiscussMessage;
import com.samrt.qq.model.GroupMessage;
import com.samrt.qq.model.Message;

/**
 * @author chenjf6
 *
 */
public interface MessageCallback {
	/**
     * 收到私聊消息后的回调
     * @param message
     */
    void onMessage(Message message);

    /**
     * 收到群消息后的回调
     * @param message
     */
    void onGroupMessage(GroupMessage message);

    /**
     * 收到讨论组消息后的回调
     * @param message
     */
    void onDiscussMessage(DiscussMessage message);
}

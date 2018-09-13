/**
 * 
 */
package com.samrt.qq.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.samrt.qq.callback.MessageCallback;
import com.samrt.qq.client.SmartQQClient;
import com.samrt.qq.constant.Answer;
import com.samrt.qq.interfaces.ISmartAnswerSV;
import com.samrt.qq.model.DiscussMessage;
import com.samrt.qq.model.GroupMessage;
import com.samrt.qq.model.Message;
import com.samrt.qq.model.SmartQq;

/**
 * @author chenjf6
 *
 */
@Component
@Order(value=1)
public class QQBaseService implements ApplicationRunner{
	private static final Logger LOGGER = Logger.getLogger(QQBaseService.class);
	@Autowired
	private SmartQq SmartQq;
	private SmartQQClient client;
	private static final String SMART_QQ_INFO = "有问题请找小郭";
	@Autowired
	private ISmartAnswerSV iSmartAnswerSV;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		client = new SmartQQClient(new MessageCallback() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				new Thread(() -> {
                    try {
                        Thread.sleep(500 + RandomUtils.nextInt(1000));
                        final String content = message.getContent();
                        final String key = SmartQq.getKey();
                        System.out.println("content===="+content);
                        if (!StringUtils.startsWith(content, key)) { // 不是管理命令，只是普通的私聊
                            // 让小薇进行自我介绍
                        	client.sendMessageToFriend(message.getUserId(), SMART_QQ_INFO);

                            return;
                        }
						
                    } catch (final Exception e) {
                        LOGGER.error("smartQQ on  message error", e);
                    }
                }).start();
			}

			@Override
			public void onGroupMessage(GroupMessage message) {
				// TODO Auto-generated method stub

                new Thread(() -> {
                    try {
                        Thread.sleep(500 + RandomUtils.nextInt(1000));

                        onQQGroupMessage(message);
                    } catch (final Exception e) {
                    	LOGGER.error("smartQQ on group message error", e);
                    }
                }).start();
            
			}
			
			@Override
			public void onDiscussMessage(DiscussMessage message) {
				// TODO Auto-generated method stub

                new Thread(() -> {
                    try {
                        Thread.sleep(500 + RandomUtils.nextInt(1000));

                        onQQDiscussMessage(message);
                    } catch (final Exception e) {
                    	LOGGER.error("smartQQ on discuss message error", e);
                    }
                }).start();
            
			}
		});
	}
	protected void onQQDiscussMessage(DiscussMessage message) {
		// TODO Auto-generated method stub
		
	}
	protected void onQQGroupMessage(GroupMessage message) {
		// TODO Auto-generated method stub
		//问什么答什么，后面再添加机器人回复功能
		//String content = message.getContent();
		//System.out.println(message.getGroupId()+"group id==="+);
		 //client.sendMessageToGroup(message.getGroupId(), message.getContent());SmartQq.getQqName()
		/**
		if (StringUtils.contains(content, "三人浪漫")
                || (StringUtils.length(content) > 3
                && (StringUtils.contains(content, "?") || StringUtils.contains(content, "？") || StringUtils.contains(content, "问")))) {
			LOGGER.info("content=="+content);
			String msg = answer(content);
			client.sendMessageToGroup(message.getGroupId(), msg);
        }
		**/
	}
	private String answer(String content) {
		// TODO Auto-generated method stub
		//过滤@信息
		String msg = "";
		String userName = "@三人浪漫";//+SmartQq.getKey();
		content = StringUtils.replace(content,userName, "" );
		try {
			msg = iSmartAnswerSV.answer(Answer.ITPK).queryAnswer(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * @return the client
	 */
	public SmartQQClient getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(SmartQQClient client) {
		this.client = client;
	}
	
}

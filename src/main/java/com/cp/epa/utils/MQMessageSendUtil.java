package com.cp.epa.utils;

import javax.jms.JMSException;
import com.zhongpin.mq.base.MQSendMsg;
import com.zhongpin.mq.pojo.MQMessage;
 
/**
 * 
 * 类名：MQMessageSendUtil  <br />
 *
 * 功能：
 *		mq发送消息类
 * @author 李永 <br />
 * 创建时间：2012-11-20 上午10:54:42  <br />
 * @version 2012-11-20
 */
public class MQMessageSendUtil extends MQSendMsg{
	public static MQMessageSendUtil mmsu;
	static{
		mmsu = new MQMessageSendUtil();
	}
	public static void sendMsg(MQMessage mm) throws JMSException{
		mmsu.sendMessage(mm);
	}
	
    //url	
 	@Override
	public String getBroker() {
		return "";
	}

	//队列名
	@Override
	public String getQueueName() {
		return "";
	}
}

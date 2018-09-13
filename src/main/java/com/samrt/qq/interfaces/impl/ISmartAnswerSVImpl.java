package com.samrt.qq.interfaces.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.samrt.qq.constant.Answer;
import com.samrt.qq.interfaces.IDefaultAnswerSV;
import com.samrt.qq.interfaces.ISmartAnswerSV;
@Service
public class ISmartAnswerSVImpl implements ISmartAnswerSV {

	@Override
	public IDefaultAnswerSV answer(Answer answer) {
		// TODO Auto-generated method stub
		ApplicationContext context = new AnnotationConfigApplicationContext(answer.getBeanMane());
		 //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(answer.getClass());
		//return (IDefaultAnswerSV) context.getBean(answer.name());
				return (IDefaultAnswerSV) context.getBean(answer.getWay());
	}

}

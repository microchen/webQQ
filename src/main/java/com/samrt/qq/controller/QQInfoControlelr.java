/**
 * 
 */
package com.samrt.qq.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.samrt.qq.constant.QQGroupIdMap;
import com.samrt.qq.interfaces.IQQInfoSV;
import com.samrt.qq.model.Group;

/**
 * @author chenjf6
 *
 */
@Controller
@RequestMapping(value="/qqinfo")
public class QQInfoControlelr {
	private static final Logger LOGGER = Logger.getLogger(QQInfoControlelr.class);
	@Autowired
	private IQQInfoSV iQQInfoSV;
	@RequestMapping(value="/group")
	@ResponseBody
	public Object getGroupInfo() {
		
		List<Group> list = iQQInfoSV.getUserGroupInfo();
		list.forEach(group->{
			QQGroupIdMap.groupIdMap.put(group.getId(), group.getId());
		});
		return list;
	}
	@RequestMapping(value="/login")
	public void login(HttpServletRequest req,  HttpServletResponse resp) {
		resp.addHeader("Cache-Control", "no-store");

        try (final PrintWriter writer = resp.getWriter()) {
            final String filePath = new File("qrcode.png").getCanonicalPath();
            final byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));
            final StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html><body><img src=\"data:image/png;base64,").
                    append(Base64.getEncoder().encodeToString(data)).append("\"/></body></html>");
            writer.write(htmlBuilder.toString());
            writer.flush();
        } catch (final Exception e) {
            LOGGER.error( "在线显示二维码图片异常", e);
        }
	}
	@RequestMapping(value="/sendMsg2Group")
	@ResponseBody
	public JSONObject sendMsg2Group(HttpServletRequest req) {
		String id = req.getParameter("id");
		String msg = req.getParameter("msg");
		//做一个简单的校验，如果不是用户的群id不发信息
		JSONObject ret =  new JSONObject();
		ret.put("msg", "发送失败");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(msg)) {
			Long groupId = QQGroupIdMap.groupIdMap.get(Long.valueOf(id));
			if(groupId!=null) {
				iQQInfoSV.sendMsg2Group(groupId,msg);
				ret.put("msg", "发送成功");
				ret.put("code", "0");
			}
			
		}
		
		return ret;
	}
}

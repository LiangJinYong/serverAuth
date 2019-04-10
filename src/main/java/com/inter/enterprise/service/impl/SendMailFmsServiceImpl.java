package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.SendMailFmsDao;
import com.inter.enterprise.service.SendMailFmsService;
import com.inter.util.GetTimeUtil;
import com.inter.util.MakeCertificationCodeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class SendMailFmsServiceImpl implements SendMailFmsService {

	@Autowired
	private SendMailFmsDao sendMailFmsDao;

	@Autowired
	@Qualifier("mailSenderFMS")
	private MailSender mailSender;
	
	@Autowired
	private ResultMessageUtil messageUtil;

	public String sendMailFms(String mailJson) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();

		Map<String, Object> mailObj = gson.fromJson(mailJson, Map.class);
		String type = (String) mailObj.get("type");
		String mail = (String) mailObj.get("mail");
		Map<String, String> message = (Map<String, String>) mailObj.get("message");
		String certificationCode = MakeCertificationCodeUtil.makeCertificationCode();
		String time = GetTimeUtil.getTime();

		Map<String, String> param = new HashMap<String, String>();
		param.put("mail", mail);
		param.put("certificationCode", certificationCode);
		param.put("time", time);

		String subject = "";
		StringBuilder body = new StringBuilder();

		if ("join".equals(type)) {
			subject = "�삻鴉ゆ변繹먧펯訝싨낏�냼�뵵瑥�";
			body.append("鴉곦툣�뵵瑥룡낏�냼鴉싧몮藥꿨츑�닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("力뺜볶冶볟릫 : ").append(message.get("corp_name")).append("\n")//
					.append("力뺜볶�걫楹삥뼶凉� : ").append(message.get("corp_phone_num")).append("\n")//
					.append("�뀶�뤈 E-mail : ").append(message.get("corp_mail")).append("\n")//
					.append("�뀶�뤈�쑑�� : ").append(message.get("corp_addr")).append("\n")//
					.append("�뀶�뤈營묈� : ").append(message.get("corp_homepage")).append("\n")//
					.append("力ⓨ냼�뿥�쐿 : ").append(message.get("join_dt"));
		} else if ("approve".equals(type)) {
			subject = "�삻鴉ゆ변繹먧펯訝싨낏�냼若→돶";
			body.append("鴉곦툣�뵵瑥룡낏�냼鴉싧몮藥꿨츑�닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("力뺜볶冶볟릫 : ").append(message.get("corp_name")).append("\n")//
					.append("力뺜볶�걫楹삥뼶凉� : ").append(message.get("corp_phone_num")).append("\n")//
					.append("�뀶�뤈 E-mail : ").append(message.get("corp_mail")).append("\n")//
					.append("�뀶�뤈�쑑�� : ").append(message.get("corp_addr")).append("\n")//
					.append("�뀶�뤈營묈� : ").append(message.get("corp_homepage")).append("\n")//
					.append("力ⓨ냼�뿥�쐿 : ").append(message.get("join_dt")).append("\n")//
					.append("若→돶�뿥�쐿 : ").append(message.get("approve_dt")).append("\n")//
					.append("若→돶雅뷴몮ID : ").append(message.get("approve_id")).append("\n")//
					.append("謠뚩칮�쟻 : ").append(certificationCode);
		} else if ("reapprove".equals(type)) {
			subject = "�삻鴉ゆ변繹먧펯訝싨낏�냼若→돶";
			body.append("鴉곦툣�뵵瑥룡낏�냼鴉싧몮藥꿨츑�닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("力뺜볶冶볟릫 : ").append(message.get("corp_name")).append("\n")//
					.append("力뺜볶�걫楹삥뼶凉� : ").append(message.get("corp_phone_num")).append("\n")//
					.append("�뀶�뤈 E-mail : ").append(message.get("corp_mail")).append("\n")//
					.append("�뀶�뤈�쑑�� : ").append(message.get("corp_addr")).append("\n")//
					.append("�뀶�뤈營묈� : ").append(message.get("corp_homepage")).append("\n")//
					.append("力ⓨ냼�뿥�쐿 : ").append(message.get("join_dt")).append("\n")//
					.append("若→돶�뿥�쐿 : ").append(message.get("approve_dt")).append("\n")//
					.append("若→돶雅뷴몮ID : ").append(message.get("approve_id")).append("\n");
		} else if ("insert".equals(type)) {

			subject = "鴉곦툣�닇�몮力ⓨ냼若뚧닇";
			body.append("鴉곦툣�닇�몮Id�뵟�닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("力뺜볶冶볟릫 : ").append(message.get("corp_name")).append("\n")//
					.append("力뺜볶�걫楹삥뼶凉� : ").append(message.get("corp_phone_num")).append("\n")//
					.append("�뀶�뤈 E-mail : ").append(message.get("corp_mail")).append("\n")//
					.append("鴉곦툣�닇�몮ID �뵟�닇�뿥�쐿 : ").append(message.get("make_id_dt")).append("\n")//
					.append("鴉곦툣�닇�몮ID : ").append(message.get("user_id")).append("\n")//
					.append("鴉곦툣�닇�몮野녺쟻 : ").append(message.get("user_pwd")).append("\n")//
					.append("鴉곦툣�닇�몮冶볟릫 : ").append(message.get("user_name")).append("\n")//
					.append("鴉곦툣�닇�몮�궙嶸� : ").append(message.get("user_email")).append("\n")//
					.append("謠뚩칮�쟻 : ").append(certificationCode);
		} else if ("request".equals(type)) {

			subject = "�젃嶺양뵵瑥룩��瀯녶냵若�";
			body.append("�닇�젃嶺양뵵瑥룟럴若뚧닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("�븚�뱚�릫燁� : ").append(message.get("product_name")).append("\n")//
					.append("�븚�뱚餓ｇ쟻 : ").append(message.get("product_no")).append("\n")//
					.append("�젃嶺얏빊�뇧 : ").append(message.get("order_cnt")).append("\n")//
					.append("�뵵瑥룝볶冶볟릫 : ").append(message.get("requestor_name")).append("\n")//
					.append("�뵵瑥룡뿥�쐿 : ").append(message.get("order_dt")).append("\n")//
					.append("若→돶雅뷴몮ID : ").append(message.get("approve_id")).append("\n")//
					.append("若→돶�뿥�쐿 : ").append(message.get("approve_dt"));
		} else if ("reject".equals(type)) {

			subject = "要녑썮�젃嶺양뵵瑥�";
			body.append("�젃嶺양뵵瑥룩˙要녑썮.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("�븚�뱚�릫燁� : ").append(message.get("product_name")).append("\n")//
					.append("�븚�뱚餓ｇ쟻 : ").append(message.get("product_no")).append("\n")//
					.append("�젃嶺얏빊�뇧 : ").append(message.get("order_cnt")).append("\n")//
					.append("�뵵瑥룝볶冶볟릫 : ").append(message.get("requestor_name")).append("\n")//
					.append("�뵵瑥룡뿥�쐿 : ").append(message.get("order_dt")).append("\n")//
					.append("要녑썮雅뷴몮ID : ").append(message.get("reject_id")).append("\n")//
					.append("要녑썮�뿥�쐿 : ").append(message.get("reject_dt")).append("\n")//
					.append("要녑썮�렅�썱 : ").append(message.get("reject_comment"));

		} else if ("handle".equals(type)) {

			subject = "�젃嶺얍쨪�릤瀯볠옖";
			body.append("�뵵瑥루쉪�젃嶺얍럴鸚꾤릤若뚧닇.\n\n")//
					.append("鴉곦툣�릫燁� : ").append(message.get("biz_name")).append("\n")//
					.append("�븚�뱚�릫燁� : ").append(message.get("product_name")).append("\n")//
					.append("�븚�뱚餓ｇ쟻 : ").append(message.get("product_no")).append("\n")//
					.append("�젃嶺얏빊�뇧 : ").append(message.get("order_cnt")).append("\n")//
					.append("�뵵瑥룝볶冶볟릫 : ").append(message.get("requestor_name")).append("\n")//
					.append("�뵵瑥룡뿥�쐿 : ").append(message.get("order_dt")).append("\n")//
					.append("若뚧닇�뿥�쐿 : ").append(message.get("return_dt"));
		}

		try {
			sendMailFmsDao.insertMailCertificationCode(param);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("lianshukj.fms@lianshukj.com");
			simpleMailMessage.setTo(mail);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(body.toString());
			mailSender.send(simpleMailMessage);
			result.put("result_code", 200);
		} catch (Exception e) {
			result.put("result_code", 500);
			e.printStackTrace();
		}
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

}

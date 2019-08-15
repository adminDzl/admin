package com.wolves.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.wolves.util.chuanglan.ChuangLanSmsUtil;
import com.wolves.util.chuanglan.SmsSendRequest;
import com.wolves.util.chuanglan.SmsSendResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;   

/**
 * 通过短信接口发送短信
 */
public class SmsUtil {

	/**
	 * 华信云短信发送
	 * @throws Exception
	 */
	public static String sendByHuaXin(String tel, String msg) throws Exception {
		org.apache.http.client.HttpClient httpclient = new SSLClient();
		String url = "https://sh2.ipyy.com/sms.aspx";
		String accountName="kl53";
		String password="kl5366";

		HttpPost post = new HttpPost(url);
		post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		List<org.apache.http.NameValuePair> nvps = new ArrayList<org.apache.http.NameValuePair>();
		nvps.add(new BasicNameValuePair("action","send"));
		nvps.add(new BasicNameValuePair("userid", ""));
		nvps.add(new BasicNameValuePair("account", accountName));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("mobile", tel));
		nvps.add(new BasicNameValuePair("content", msg));
		nvps.add(new BasicNameValuePair("sendTime", ""));
		nvps.add(new BasicNameValuePair("extno", ""));

		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		HttpResponse response = httpclient.execute(post);
		String returnstatus = "";
		try {
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			// 将字符转化为XML
			String returnString= EntityUtils.toString(entity, "UTF-8");
			Document doc = DocumentHelper.parseText(returnString);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 获取根节点下的子节点的值
			returnstatus = rootElt.elementText("returnstatus").trim();
			String message = rootElt.elementText("message").trim();
			String remainpoint = rootElt.elementText("remainpoint").trim();
			String taskID = rootElt.elementText("taskID").trim();
			String successCounts = rootElt.elementText("successCounts").trim();

			System.out.println(returnString);
			System.out.println("返回状态为：" + returnstatus);
			System.out.println("返回信息提示：" + message);
			System.out.println("返回余额：" + remainpoint);
			System.out.println("返回任务批次：" + taskID);
			System.out.println("返回成功条数：" + successCounts);
			EntityUtils.consume(entity);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return returnstatus;
	}

	public static String sendByChuanglan(String phone, String msg) throws Exception{
		final String charset = "utf-8";
		String account = "N5467074";
		String password = "kIis9eRGqT6f98";
		String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
		//状态报告
		String report= "true";
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		System.out.println("before request string is: " + requestJson);
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		System.out.println("response after request result is :" + response);
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		System.out.println("response  toString is :" + smsSingleResponse);
		String returnString = "";
		if("0".equals(smsSingleResponse.getCode())){
			returnString = "Success";
		}
		return returnString;
	}

	public static String sendByJiXinTong(String phone, String msg) throws Exception {
		BufferedReader In = null;
		PrintWriter Out = null;
		HttpURLConnection HttpConn = null;

		String data = "id=271289483&pwd=baogang12345&to="+phone+"&content=" + URLEncoder.encode(msg, "gb2312") + "&time=";
		try {
			java.net.URL url=new URL("http://service.winic.org/sys_port/gateway/index.asp");
			HttpConn=(HttpURLConnection)url.openConnection();
			HttpConn.setRequestMethod("POST");
			HttpConn.setDoInput(true);
			HttpConn.setDoOutput(true);

			Out=new PrintWriter(HttpConn.getOutputStream());
			Out.println(data);
			Out.flush();

			if(HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK){
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				In = new BufferedReader(new InputStreamReader(HttpConn.getInputStream()));
				while((tempStr = In.readLine()) != null){
					content.append(tempStr);
				}
				In.close();
				return "Success";
			}
			else
			{
				throw new Exception("HTTP_POST_ERROR_RETURN_STATUS：" + HttpConn.getResponseCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			Out.close();
			HttpConn.disconnect();
		}
		return null;
	}



	public static void main(String[] args) throws Exception{
//		String jixin = sendByJixin("13918147924", "你好test");
//		System.out.println(jixin);
//		sendByHuaXin("13918147924", "你好test");

		//String huaxin = sendByChuanglan("13918147924", "您的验证码是：4625【煦睿科技】");
		sendByJiXinTong("13918147924", "您的验证码是：4625【opark】");
		//sendSms2("13511111111","您的验证码是：1111。请不要把验证码泄露给其他人。");
	}

}


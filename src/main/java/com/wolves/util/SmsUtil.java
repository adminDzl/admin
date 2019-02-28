package com.wolves.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   

/**
 * 通过短信接口发送短信
 */
public class SmsUtil {

	 //短信商 一  http://www.dxton.com/ =====================================================================================
	/**
	 * 给一个人发送单条短信
	 * @param mobile 手机号
	 * @param code  短信内容
	 */
 	public static void sendSms1(String mobile,String code){
 		
	    String account = "", password = "";
		//读取短信1配置
	    String strSMS1 = Tools.readTxtFile(Const.SMS1);
		if(null != strSMS1 && !"".equals(strSMS1)){
			String strS1[] = strSMS1.split(",fh,");
			if(strS1.length == 2){
				account = strS1[0];
				password = strS1[1];
			}
		}
 		String PostData = "";
		try {
			PostData = "account="+account+"&password="+password+"&mobile="+mobile+"&content="+URLEncoder.encode(code,"utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("短信提交失败");
		}
 	     String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
 	     System.out.println(ret);
	}
	
	 public static String SMS(String postData, String postUrl) {
	        try {
	            //发送POST请求
	            URL url = new URL(postUrl);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            conn.setRequestProperty("Connection", "Keep-Alive");
	            conn.setUseCaches(false);
	            conn.setDoOutput(true);

	            conn.setRequestProperty("Content-Length", "" + postData.length());
	            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	            out.write(postData);
	            out.flush();
	            out.close();

	            //获取响应状态
	            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
	                System.out.println("connect failed!");
	                return "";
	            }
	            //获取响应内容体
	            String line, result = "";
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	            while ((line = in.readLine()) != null) {
	                result += line + "\n";
	            }
	            in.close();
	            return result;
	        } catch (IOException e) {
	            e.printStackTrace(System.out);
	        }
	        return "";
	    }

	/**
	 * 
	 * 短信商 二  http://www.ihuyi.com/ =====================================================================================
	 * 
	 */
	private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";

	/**
	 * 给一个人发送单条短信
	 * @param mobile 手机号
	 * @param code  短信内容
	 */
	public static void sendSms2(String mobile,String code){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

	    String content = new String(code);  
	    
	    String account = "", password = "";
		/**
		 * 读取短信2配置
		 */
		String strSMS2 = Tools.readTxtFile(Const.SMS2);
		if(null != strSMS2 && !"".equals(strSMS2)){
			String strS2[] = strSMS2.split(",fh,");
			if(strS2.length == 2){
				account = strS2[0];
				password = strS2[1];
			}
		}
	    
		NameValuePair[] data = {//提交短信
		    new NameValuePair("account", account),
			//密码可以使用明文密码或使用32位MD5加密
		    new NameValuePair("password", password),
		    new NameValuePair("mobile", mobile), 
		    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);
		
		try {
			client.executeMethod(method);
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			if(code == "2"){
				System.out.println("短信提交成功");
			}
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		
	}


	/**
	 * 吉信通短信发送
	 * @throws Exception
	 */
	public static String sendByJixin(String tel, String msg) throws Exception {

		String Data = "id=271289483&pwd=baogang12345&to="+tel+"&content=" + URLEncoder.encode(msg+"【江苏生活通】", "gb2312") + "&time=";
		BufferedReader In = null;
		PrintWriter Out = null;
		HttpURLConnection HttpConn = null;
		try {
			URL url=new URL("http://service.winic.org/sys_port/gateway/index.asp");
			HttpConn=(HttpURLConnection)url.openConnection();
			HttpConn.setRequestMethod("POST");
			HttpConn.setDoInput(true);
			HttpConn.setDoOutput(true);

			Out=new PrintWriter(HttpConn.getOutputStream());
			Out.println(Data);
			Out.flush();

			if(HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK){
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				In = new BufferedReader(new InputStreamReader(HttpConn.getInputStream()));
				while((tempStr = In.readLine()) != null){
					content.append(tempStr);
				}
				In.close();
				return content.toString();
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

	public static void main(String[] args) throws Exception{
//		String jixin = sendByJixin("13918147924", "你好test");
//		System.out.println(jixin);

		String huaxin = sendByHuaXin("13918147924", "您的验证码是：4625【煦睿科技】");
		//sendSms2("13511111111","您的验证码是：1111。请不要把验证码泄露给其他人。");
	}

}


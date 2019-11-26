<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
	function save(){
	//	alert($("#smstip").is(':checked'));
	if($("#smstip").is(':checked')&$("#mailtip").is(':checked'))	{
	//	alert("短信+邮件");
		$("#TP_MSG").val("1");//1.短信+邮件
	}else if($("#smstip").is(':checked')&!$("#mailtip").is(':checked')){
	//	alert("duanxin");
		$("#TP_MSG").val("2");//2.短信
	}else if(!$("#smstip").is(':checked')&$("#mailtip").is(':checked')){
		//alert("youjian");
		$("#TP_MSG").val("3");//3.邮件
	}else{
		//alert("不发");
		$("#TP_MSG").val("4");//4.不用短信或邮件
	}
	//{$("#TP_MSG").val()="1";}else{$("#TP_MSG").val()="2";}
		//alert("xxxx");
		//return false;

			if($("#MSG_TYPE").val()==""){
			$("#MSG_TYPE").tips({
				side:3,
	            msg:'请输入消息类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MSG_TYPE").focus();
			return false;
		}
		if($("#TO_USER").val()==""){
			$("#TO_USER").tips({
				side:3,
	            msg:'请输入通知人群',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TO_USER").focus();
			return false;
		}
		if($("#ALERT_TITLE").val()==""){
			$("#ALERT_TITLE").tips({
				side:3,
	            msg:'请输入提醒标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ALERT_TITLE").focus();
			return false;
		}
		if($("#ALERT_CONTENT").val()==""){
			$("#ALERT_CONTENT").tips({
				side:3,
	            msg:'请输入提醒内容',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ALERT_CONTENT").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="tipmsg/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="TIPMSG_ID" id="TIPMSG_ID" value="${pd.TIPMSG_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">消息类型:</td>
				<td>
					<select name="MSG_TYPE" id="MSG_TYPE" maxlength="32" placeholder="这里输消息类型" title="消息类型" value="${pd.MSG_TYPE}" >
						<option value=''>请选择</option>
						<option value="1" <c:if test="${1 == pd.MSG_TYPE}">selected</c:if> >公告消息</option>
						<option value="2" <c:if test="${2 == pd.MSG_TYPE}">selected</c:if> >进度提醒</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">通知人群:</td>
				<td>
					<select name="TO_USER" id="TO_USER" maxlength="32" placeholder="这里选择通知人" title="通知人员" value="${pd.TO_USER}">
						<option value=''>请选择</option>
						<option value='ALL'>全部</option>
						<c:forEach items="${companys}" varStatus="status" var="item">
							<option value="${item.companyId }" >${item.companyName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">提醒标题:</td>
				<td><input type="text" name="ALERT_TITLE" id="ALERT_TITLE" value="${pd.ALERT_TITLE}" maxlength="32" placeholder="这里输入提醒标题" title="提醒标题"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">提醒内容:</td>
				<td>
					<textarea style="width: 208px;height: 82px;"
							  name="ALERT_CONTENT"
							  id="ALERT_CONTENT"
							  value=""
							  maxlength="200" placeholder="这里输入提醒内容" title="提醒内容">
						${pd.ALERT_CONTENT}
					</textarea>
				</td>
			</tr>
			<tr>
			
				<td style="text-align: center;" colspan="10">
				<input  type="checkbox"   id="mailtip"  value="邮件通知" /><span class="lbl">邮件通知</span>
				<!--  
					<input type="checkbox"    id="smstip"  value="短信通知" /><span class="lbl">短信通知</span>
					-->
				    <input type="text"   id="TP_MSG"  name="TP_MSG"  style="display:none" />
				    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
	</form>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			//日期框
			$('.date-picker').datepicker();
		});
		</script>
</body>
</html>
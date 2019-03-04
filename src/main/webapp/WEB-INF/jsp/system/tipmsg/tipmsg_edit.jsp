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
				<td><input type="number" name="MSG_TYPE" id="MSG_TYPE" value="${pd.MSG_TYPE}" maxlength="32" placeholder="这里输消息类型" title="消息类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">通知人群:</td>
				<td><input type="text" name="TO_USER" id="TO_USER" value="${pd.TO_USER}" maxlength="32" placeholder="这里输入通知人群" title="通知人群"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">提醒标题:</td>
				<td><input type="text" name="ALERT_TITLE" id="ALERT_TITLE" value="${pd.ALERT_TITLE}" maxlength="32" placeholder="这里输入提醒标题" title="提醒标题"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">提醒内容:</td>
				<td><input type="text" name="ALERT_CONTENT" id="ALERT_CONTENT" value="${pd.ALERT_CONTENT}" maxlength="32" placeholder="这里输入提醒内容" title="提醒内容"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
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
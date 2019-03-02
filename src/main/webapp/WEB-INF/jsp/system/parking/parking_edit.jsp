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
			if($("#USER_ID").val()==""){
			$("#USER_ID").tips({
				side:3,
	            msg:'请输入用户ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USER_ID").focus();
			return false;
		}
		if($("#BEGIN_TIME").val()==""){
			$("#BEGIN_TIME").tips({
				side:3,
	            msg:'请输入开始时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BEGIN_TIME").focus();
			return false;
		}
		if($("#END_TIME").val()==""){
			$("#END_TIME").tips({
				side:3,
	            msg:'请输入结束时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#END_TIME").focus();
			return false;
		}
		if($("#TOTAL_HOUR").val()==""){
			$("#TOTAL_HOUR").tips({
				side:3,
	            msg:'请输入总时长',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_HOUR").focus();
			return false;
		}
		if($("#FEE").val()==""){
			$("#FEE").tips({
				side:3,
	            msg:'请输入停车费用',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FEE").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="parking/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PARKING_ID" id="PARKING_ID" value="${pd.PARKING_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">用户ID:</td>
				<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">开始时间:</td>
				<td><input class="span10 date-picker" name="BEGIN_TIME" id="BEGIN_TIME" value="${pd.BEGIN_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始时间" title="开始时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">结束时间:</td>
				<td><input class="span10 date-picker" name="END_TIME" id="END_TIME" value="${pd.END_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束时间" title="结束时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">总时长:</td>
				<td><input type="text" name="TOTAL_HOUR" id="TOTAL_HOUR" value="${pd.TOTAL_HOUR}" maxlength="32" placeholder="这里输入总时长" title="总时长"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">停车费用:</td>
				<td><input type="text" name="FEE" id="FEE" value="${pd.FEE}" maxlength="32" placeholder="这里输入停车费用" title="停车费用"/></td>
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
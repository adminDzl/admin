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
			if($("#PAY_TYPE").val()==""){
			$("#PAY_TYPE").tips({
				side:3,
	            msg:'请输入类型（1-水电物业，2-停车费，3-场地预定费，4-一卡通费用）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAY_TYPE").focus();
			return false;
		}
		if($("#PAY_AMOUNT").val()==""){
			$("#PAY_AMOUNT").tips({
				side:3,
	            msg:'请输入付费金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAY_AMOUNT").focus();
			return false;
		}
		if($("#PAY_TIME").val()==""){
			$("#PAY_TIME").tips({
				side:3,
	            msg:'请输入付费时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAY_TIME").focus();
			return false;
		}
		if($("#RETURN_TIME").val()==""){
			$("#RETURN_TIME").tips({
				side:3,
	            msg:'请输入确认时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RETURN_TIME").focus();
			return false;
		}
		if($("#PAY_STATUS").val()==""){
			$("#PAY_STATUS").tips({
				side:3,
	            msg:'请输入支付状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAY_STATUS").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="payorder/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PAYORDER_ID" id="PAYORDER_ID" value="${pd.PAYORDER_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">类型（1-水电物业，2-停车费，3-场地预定费，4-一卡通费用）:</td>
				<td><input type="number" name="PAY_TYPE" id="PAY_TYPE" value="${pd.PAY_TYPE}" maxlength="32" placeholder="这里输入类型（1-水电物业，2-停车费，3-场地预定费，4-一卡通费用）" title="类型（1-水电物业，2-停车费，3-场地预定费，4-一卡通费用）"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">付费金额:</td>
				<td><input type="text" name="PAY_AMOUNT" id="PAY_AMOUNT" value="${pd.PAY_AMOUNT}" maxlength="32" placeholder="这里输入付费金额" title="付费金额"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">付费时间:</td>
				<td><input class="span10 date-picker" name="PAY_TIME" id="PAY_TIME" value="${pd.PAY_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="付费时间" title="付费时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">确认时间:</td>
				<td><input class="span10 date-picker" name="RETURN_TIME" id="RETURN_TIME" value="${pd.RETURN_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="确认时间" title="确认时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">支付状态:</td>
				<td><input type="number" name="PAY_STATUS" id="PAY_STATUS" value="${pd.PAY_STATUS}" maxlength="32" placeholder="这里输入支付状态" title="支付状态"/></td>
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
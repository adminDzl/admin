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
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
	//保存
	function save(){
			if($("#PLACE_TYPE").val()==""){
			$("#PLACE_TYPE").tips({
				side:3,
	            msg:'请输入场地类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PLACE_TYPE").focus();
			return false;
		}
		if($("#POSITION").val()==""){
			$("#POSITION").tips({
				side:3,
	            msg:'请输入所处位置',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#POSITION").focus();
			return false;
		}
		if($("#IMAGE_URL").val()==""){
			$("#IMAGE_URL").tips({
				side:3,
	            msg:'请输入图片url',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#IMAGE_URL").focus();
			return false;
		}
		if($("#EQUIPMENT").val()==""){
			$("#EQUIPMENT").tips({
				side:3,
	            msg:'请输入设备描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#EQUIPMENT").focus();
			return false;
		}
		if($("#RENT_FEE").val()==""){
			$("#RENT_FEE").tips({
				side:3,
	            msg:'请输入价格',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RENT_FEE").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="yard/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="YARD_ID" id="YARD_ID" value="${pd.YARD_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">场地类型:</td>
				<td><input type="number" name="PLACE_TYPE" id="PLACE_TYPE" value="${pd.PLACE_TYPE}" maxlength="32" placeholder="这里输入场地类型" title="场地类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">所处位置:</td>
				<td><input type="text" name="POSITION" id="POSITION" value="${pd.POSITION}" maxlength="32" placeholder="这里输入所处位置" title="所处位置"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">图片url:</td>
				<td><input type="text" name="IMAGE_URL" id="IMAGE_URL" value="${pd.IMAGE_URL}" maxlength="32" placeholder="这里输入图片url" title="图片url"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">设备描述:</td>
				<td><input type="text" name="EQUIPMENT" id="EQUIPMENT" value="${pd.EQUIPMENT}" maxlength="32" placeholder="这里输入设备描述" title="设备描述"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">价格:</td>
				<td><input type="text" name="RENT_FEE" id="RENT_FEE" value="${pd.RENT_FEE}" maxlength="32" placeholder="这里输入价格" title="价格"/></td>
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
	
	
		<!-- 引入 -->
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
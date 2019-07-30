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
		if($("#COMPANY_ID").val()==""){
			$("#COMPANY_ID").tips({
				side:4,
				msg:'请选择公司',
				bg:'#AE81FF',
				time:2
			});
			$("#COMPANY_ID").focus();
			return false;
		}
		if($("#PAYMENT_TYPE").val()==""){
			$("#PAYMENT_TYPE").tips({
				side:3,
	            msg:'请输入缴费备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAYMENT_TYPE").focus();
			return false;
		}
		if($("#AMOUNT").val()==""){
			$("#AMOUNT").tips({
				side:3,
	            msg:'请输入缴费金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#AMOUNT").focus();
			return false;
		}
		if($("#PAYMENT_DATE").val()==""){
			$("#PAYMENT_DATE").tips({
				side:3,
	            msg:'请输入费用月度',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAYMENT_DATE").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="payment/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PAYMENT_ID" id="PAYMENT_ID" value="${pd.PAYMENT_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">缴费公司:</td>
				<td>
					<select name="COMPANY_ID" id="COMPANY_ID" maxlength="32" placeholder="这里选择公司" title="公司" value="${pd.COMPANY_ID}">
						<option value=''>请选择</option>
						<c:forEach items="${company}" varStatus="status" var="item">
							<option value="${item.companyId }" <c:if test="${item.companyId == pd.COMPANY_ID}">selected</c:if>>${item.companyName }</option>
						</c:forEach>
					</select>
				</td>
				<%--<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入缴费单位id" title="缴费单位id"/></td>--%>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">缴费备注:</td>
				<td><input type="text" name="PAYMENT_TYPE" id="PAYMENT_TYPE" value="${pd.PAYMENT_TYPE}" maxlength="32" placeholder="这里输入缴费备注" title="缴费备注"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">缴费金额:</td>
				<td><input type="text" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}" maxlength="32" placeholder="这里输入缴费金额" title="缴费金额"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">费用月度:</td>
				<td><input class="span10 date-picker" name="PAYMENT_DATE" id="PAYMENT_DATE" value="${pd.PAYMENT_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="费用月度" title="费用月度"/></td>
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
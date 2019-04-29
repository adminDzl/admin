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

		if($("#BUILD_NO").val()==""){
			$("#BUILD_NO").tips({
				side:3,
	            msg:'请选择楼栋',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BUILD_NO").focus();
			return false;
		}
		if($("#FLOOR").val()==""){
			$("#FLOOR").tips({
				side:3,
	            msg:'请输入 楼层',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FLOOR").focus();
			return false;
		}
		if($("#FLOOR_MASTER_NAME").val()==""){
			$("#FLOOR_MASTER_NAME").tips({
				side:3,
	            msg:'请输入层长姓名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FLOOR_MASTER_NAME").focus();
			return false;
		}
		if($("#MASTER_TEL").val()==""){
			$("#MASTER_TEL").tips({
				side:3,
	            msg:'请输入联系方式',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MASTER_TEL").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

</script>
	</head>
<body>
	<form action="floorman/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="FLOORMAN_ID" id="FLOORMAN_ID" value="${pd.FLOORMAN_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">楼栋号:</td>
				<td>
					<select name="BUILD_NO" id="BUILD_NO" maxlength="32" placeholder="这里选择楼栋" title="楼栋" >
						<option value=''>请选择</option>
						<c:forEach items="${buildman}" varStatus="status" var="item">
							<option value="${item.BUILDMAN_ID }" <c:if test="${item.BUILDMAN_ID==pd.BUILD_NO}">selected</c:if>>${item.BUILD_NAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;"> 楼层:</td>
				<td><input type="text" name="FLOOR" id="FLOOR" value="${pd.FLOOR}" maxlength="32" placeholder="这里输入 楼层" title=" 楼层"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">层长姓名:</td>
				<td><input type="text" name="FLOOR_MASTER_NAME" id="FLOOR_MASTER_NAME" value="${pd.FLOOR_MASTER_NAME}" maxlength="32" placeholder="这里输入层长姓名" title="层长姓名"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">联系方式:</td>
				<td><input type="text" name="MASTER_TEL" id="MASTER_TEL" value="${pd.MASTER_TEL}" maxlength="32" placeholder="这里输入联系方式" title="联系方式"/></td>
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
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			$('.date-picker').datepicker();
		});
		</script>
</body>
</html>
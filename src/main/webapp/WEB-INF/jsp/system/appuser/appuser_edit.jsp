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

	$(top.hangge());
	
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	function ismail(mail){
	return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	
	//保存
	function save(){
		// if($("#role_id").val()==""){
		// 	$("#role_id").tips({
		// 		side:3,
	    //         msg:'选择角色',
	    //         bg:'#AE81FF',
	    //         time:2
	    //     });
		// 	$("#role_id").focus();
		// 	return false;
		// }
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}

		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
		}

		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}

		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#SEX").val()==""){
			$("#SEX").tips({
				side:3,
				msg:'选择性别',
				bg:'#AE81FF',
				time:3
			});
			$("#SEX").focus();
			return false;
		}

		if($("#HAS_DATE_RIGHT").val()==""){
			$("#HAS_DATE_RIGHT").tips({
				side:3,
				msg:'选择是否有APP数据查看权限',
				bg:'#AE81FF',
				time:3
			});
			$("#HAS_DATE_RIGHT").focus();
			return false;
		}
//		if($("#START_TIME").val()!= "" && $("#END_TIME").val() != ""){
//			var t1 = $("#START_TIME").val();
//			var t2 = $("#END_TIME").val();
//			t1 = Number(t1.replace('-', '').replace('-', ''));
//			t2 = Number(t2.replace('-', '').replace('-', ''));
//			if(t1-t2>0){
//
//				$("#END_TIME").tips({
//					side:3,
//		            msg:'到期日期必须大于开通日期',
//		            bg:'#AE81FF',
//		            time:3
//		        });
//
//				return false;
//			}
//		}
//		if($("#YEARS").val()==""){
//			$("#YEARS").val(0);
//		}else if(isNaN(Number($("#YEARS").val()))){
//
//			$("#YEARS").tips({
//				side:3,
//	            msg:'输入数字',
//	            bg:'#AE81FF',
//	            time:3
//	        });
//
//			$("#YEARS").focus();
//			$("#YEARS").val(0);
//			return false;
//		}
		
		console.log("1");
		if($("#user_id").val()==""){
            console.log("2");
			hasU();
		}else{
            console.log("3");
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $("#loginname").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>appuser/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $("#EMAIL").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>appuser/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					setTimeout("$('#EMAIL').val('')",2000);
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $("#NUMBER").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>appuser/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					setTimeout("$('#NUMBER').val('')",2000);
				 }
			}
		});
	}
	
	//查询角色
	function getUserRole() {
		var COMPANY_ID = $("#COMPANY_ID").val();//得到第一个下拉列表的值
		if(COMPANY_ID !== null && "" !== COMPANY_ID){
			//通过ajax传入后台，把orderTypeName数据传到后端
			var url = '<%=basePath%>appuser/getUserRole.do';

			$.post(url,{"companyId":COMPANY_ID},function(data){
				var option = '<option value="">请选择角色</option>';
				$.each(data,function(i,n){//循环，i为下标从0开始，n为集合中对应的第i个对象
					option += '<option value="'+n.roleId+'">'+n.roleName+'</option>';
				});
				$("#ROLE_ID").html(option);//将循环拼接的字符串插入第二个下拉列表
			});

		}else {
			$("#ROLE_ID").find("option").remove();
			var option = '<option value="">请选择角色</option>';
			$("#ROLE_ID").html(option);//将循环拼接的字符串插入第二个下拉列表
		}
	}
</script>
	</head>
<body>
	<form action="appuser/${msg }.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		<div id="zhongxin">
		<table>
			
			<%--<c:if test="${pd.ROLE_ID != '1'}">	--%>
			<%--<tr class="info">--%>
				<%--<td>--%>
				<%--<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择等级" style="vertical-align:top;"  title="级别">--%>
				<%--<option value=""></option>--%>
				<%--<c:forEach items="${roleList}" var="role">--%>
					<%--<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>--%>
				<%--</c:forEach>--%>
				<%--</select>--%>
				<%--</td>--%>
				<%--<td><input class="span10 date-picker" name="START_TIME" id="START_TIME" value="${pd.START_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开通日期"  title="开通日期"/></td>--%>
			<%--</tr>--%>
			<%--</c:if>--%>
			<%--<c:if test="${pd.ROLE_ID == '1'}">--%>
			<%--<input name="ROLE_ID" id="role_id" value="1" type="hidden" />--%>
			<%--</c:if>--%>
				
			<tr>
				<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" placeholder="这里输入用户名" title="用户名"/></td>
				<td><input type="text" name="NAME" id="name"  value="${pd.NAME }" placeholder="这里输入姓名" title="姓名" /></td>
				<%--<td><input class="span10 date-picker" name="END_TIME" id="END_TIME" value="${pd.END_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="到期日期" title="到期日期"/></td>--%>
			</tr>
			
			<tr>
				<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.NUMBER }')"/></td>
				<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.EMAIL }')"/></td>
			</tr>
			
			<tr>
				<td><input type="tel" name="PHONE" id="PHONE" value="${pd.PHONE }" placeholder="这里输入手机号" title="手机号"/></td>
				<td><input type="text" name="SFID" id="SFID" value="${pd.SFID }" placeholder="这里输入身份证号" title="身份证号" /></td>
			</tr>
			<%--<tr>--%>
				<%--<td><input type="text" name="NAME" id="name"  value="${pd.NAME }" placeholder="这里输入姓名" title="姓名" /></td>--%>
				<%--<td><input type="number" name="YEARS" id="YEARS" class="input_txt" value="${pd.YEARS }" placeholder="开通年限(请输入数字)" title="开通年限" /></td>--%>
			<%--</tr>--%>
			<tr>
				<td>
					<select name="SEX" id="SEX" maxlength="32" placeholder="这里选择性别" title="性别" value="${pd.SEX}">
						<option value=''>请选择性别</option>
						<option value="男" <c:if test="${'男' == pd.SEX}">selected</c:if>>男</option>
						<option value="女" <c:if test="${'女' == pd.SEX}">selected</c:if>>女</option>
					</select>
				</td>
				<td>
					<select name="HAS_DATE_RIGHT" id="HAS_DATE_RIGHT" maxlength="32" placeholder="是否有APP数据查看权限" title="数据查看" value="${pd.HAS_DATE_RIGHT}">
						<option value=''>请选择是否有APP数据报表查看权限</option>
						<option value="0" <c:if test="${0 == pd.HAS_DATE_RIGHT}">selected</c:if>>否</option>
						<option value="1" <c:if test="${1 == pd.HAS_DATE_RIGHT}">selected</c:if>>是</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" title="备注"/></td>
				<td>
					<select name="STATUS" title="状态">
					<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
					<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >冻结</option>
					<option value="2" <c:if test="${pd.STATUS == '2' }">selected</c:if> >离职</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<select name="COMPANY_ID" id="COMPANY_ID" maxlength="32" placeholder="这里选择公司" title="公司" value="${pd.COMPANY_ID}" onchange="getUserRole();">
						<option value=''>请选择公司</option>
						<c:forEach items="${company}" varStatus="status" var="item">
							<option value="${item.companyId }" <c:if test="${item.companyId == pd.COMPANY_ID}">selected</c:if>>${item.companyName }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<select name="ROLE_ID" id="ROLE_ID" maxlength="32" placeholder="这里选择公司下的角色" title="角色" value="${pd.ROLE_ID}">
						<option value=''>请选择角色</option>
						<c:forEach items="${roles}" varStatus="status" var="item">
							<option value="${item.roleId }" <c:if test="${item.roleId == pd.ROLE_ID}">selected</c:if>>${item.roleName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="center" colspan="2">
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
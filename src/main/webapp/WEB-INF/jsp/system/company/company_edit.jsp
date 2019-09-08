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
			if($("#COMPANY_NAME").val()==""){
			$("#COMPANY_NAME").tips({
				side:3,
	            msg:'请输入企业名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COMPANY_NAME").focus();
			return false;
		}
		if($("#TYPE").val()==""){
			$("#TYPE").tips({
				side:3,
	            msg:'请输入企业类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE").focus();
			return false;
		}
		if($("#STATUS").val()==""){
			$("#STATUS").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STATUS").focus();
			return false;
		}
		if($("#DESCRIPTION").val()==""){
			$("#DESCRIPTION").tips({
				side:3,
	            msg:'请输入描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DESCRIPTION").focus();
			return false;
		}
		if($("#COMPANY_CERTIFY").val()==""){
			$("#COMPANY_CERTIFY").tips({
				side:3,
	            msg:'请输入企业认证',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COMPANY_CERTIFY").focus();
			return false;
		}
		if($("#LOGO").val()==""){
			$("#LOGO").tips({
				side:3,
	            msg:'请输入企业logo',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOGO").focus();
			return false;
		}
		if($("#CREATE_TIME").val()==""){
			$("#CREATE_TIME").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TIME").focus();
			return false;
		}
		if($("#UPDATE_TIME").val()==""){
			$("#UPDATE_TIME").tips({
				side:3,
	            msg:'请输入修改时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_TIME").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//上传logo图片
    function uploadImage(obj){
        var files = obj.files ;
        var formData = new FormData();
        for(var i = 0;i<files.length;i++){
            formData.append("files", files[i]);
        }
        $.ajax({
            url: '<%=basePath%>/app/user/saveFile',
            type: "POST",
            data:formData,
            dataType:'json',//json 返回值类型
            cache:false,         //不设置缓存
            processData: false,  // 不处理数据
            contentType: false,   // 不设置内容类型
            success: function(data){
                if (data.result === 0){
                    var fs = data.data.join(',');
                    $("#LOGO").val(fs)

					bootbox.dialog("上传图片成功",
							[
								{
									"label" : "关闭",
									"class" : "btn-small btn-success",
									"callback": function() {
										//Example.show("great success");
									}
								}
							]
					);
                }
            }
        });
    }
</script>
	</head>
<body>
	<form action="company/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="COMPANY_ID" id="COMPANY_ID" value="${pd.COMPANY_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">企业名:</td>
				<td><input type="text" name="COMPANY_NAME" id="COMPANY_NAME" value="${pd.COMPANY_NAME}" maxlength="32" placeholder="这里输入企业名" title="企业名"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">企业类型:</td>
				<td>
					<select name="TYPE" id="TYPE" maxlength="32" placeholder="这里选择企业类型" title="企业类型" >
						<option value=''>请选择</option>
						<option value="2" <c:if test="${2 == pd.TYPE}">selected</c:if>>入驻公司</option>
						<option value="1" <c:if test="${1 == pd.TYPE}">selected</c:if>>园区公司</option>
						<option value="3" <c:if test="${3 == pd.TYPE}">selected</c:if>>其他公司</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
				<td>
					<select name="STATUS" id="STATUS" maxlength="32" placeholder="这里选择企业状态" title="企业状态" >
						<option value=''>请选择</option>
						<option value="0" <c:if test="${0 == pd.STATUS}">selected</c:if>>待入驻</option>
						<option value="2" <c:if test="${2 == pd.STATUS}">selected</c:if>>拒绝入驻</option>
						<option value="1" <c:if test="${1 == pd.STATUS}">selected</c:if>>已入驻</option>
						<option value="3" <c:if test="${3 == pd.STATUS}">selected</c:if>>已退场</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">描述:</td>
				<td><input type="text" name="DESCRIPTION" id="DESCRIPTION" value="${pd.DESCRIPTION}" maxlength="32" placeholder="这里输入描述" title="描述"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">企业认证:</td>
				<td>
					<input type="text" name="COMPANY_CERTIFY" id="COMPANY_CERTIFY" value="${pd.COMPANY_CERTIFY}" maxlength="32" placeholder="这里输入企业认证" title="企业认证"/>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">企业logo:</td>
				<td>
					<input name="fileImage" id="fileImage" value="${pd.LOGO}" placeholder="这里上传企业logo" title="企业LOGO" type="file" accept="image/*" multiple="multiple" onchange="uploadImage(this)"/>
					<input type="hidden" name="LOGO" id="LOGO" value="${pd.LOGO }"/>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">入驻时间:</td>
				<td><input class="span10 date-picker" name="COME_TIME" id="COME_TIME" value="${pd.COME_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="入住时间" title="入住时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">合约截止时间:</td>
				<td><input class="span10 date-picker" name="AGREEMENT_TIME" id="AGREEMENT_TIME" value="${pd.UPDATE_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="合约截止时间" title="合约截止时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">企业规模:</td>
				<td>
					<input type="text" name="SCALE" id="SCALE" value="${pd.SCALE}" maxlength="32" placeholder="这里输入企业规模" title="企业规模"/>
				</td>
			</tr>
			<c:if test="${msg == 'edit'}">
				<tr>
					<td style="width:70px;text-align: right;padding-top: 13px;">企业创建人:</td>
					<td>
						<select name="ADMIN_USERID" id="ADMIN_USERID" maxlength="32" placeholder="这里选择企业创建人" title="企业创建人" >
							<option value=''>请选择</option>
							<c:forEach items="${user}" varStatus="status" var="item">
								<option value="${item.userId }" <c:if test="${item.userId == pd.ADMIN_USERID}">selected</c:if>>${item.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:if>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
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
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
        if($("#ROOM_NAME").val()==""){
            $("#ROOM_NAME").tips({
                side:3,
                msg:'请输入房间号',
                bg:'#AE81FF',
                time:2
            });
            $("#ROOM_NAME").focus();
            return false;
        }
		if($("#ROOM_NAME").val()==""){
			$("#ROOM_NAME").tips({
				side:3,
	            msg:'请输入房间号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ROOM_NAME").focus();
			return false;
		}
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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//查询楼层
	function getFloorMan() {

		var buildNo = $("#BUILD_NO").val();//得到第一个下拉列表的值
		if(buildNo !== null && "" !== buildNo){
			//通过ajax传入后台，把orderTypeName数据传到后端
			var url = '<%=basePath%>room/getFloorMan.do';

			$.post(url,{"buildNo":buildNo},function(data){
				var option = '<option value="">请选择</option>';
				$.each(data,function(i,n){//循环，i为下标从0开始，n为集合中对应的第i个对象
					option += '<option value="'+n.floormanId+'">'+n.floor+'</option>';
				});
				$("#FLOORMAN_ID").html(option);//将循环拼接的字符串插入第二个下拉列表
			});

		}else {
            $("#FLOORMAN_ID").find("option").remove();
            var option = '<option value="">请选择</option>';
            $("#FLOORMAN_ID").html(option);//将循环拼接的字符串插入第二个下拉列表
		}
	}

</script>
	</head>
<body>
	<form action="room/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ROOM_ID" id="ROOM_ID" value="${pd.ROOM_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">楼宇:</td>
				<td>
					<select name="BUILD_NO" id="BUILD_NO" maxlength="32" placeholder="这里选择楼宇" title="楼宇" value="${pd.build_no}" onchange="getFloorMan();">
						<option value=''>请选择</option>
						<c:forEach items="${buildman}" varStatus="status" var="item">
							<option value="${item.BUILDMAN_ID }" <c:if test="${item.BUILDMAN_ID == pd.build_no}">selected</c:if>>${item.BUILD_NAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">楼体:</td>
				<td>
					<select name="BUILD_BODY" id="BUILD_BODY" maxlength="32" placeholder="这里选择楼体" title="楼体" >
						<option value=''>请选择</option>
						<c:forEach items="${buildbodys}" varStatus="status" var="item">
							<option value="${item.ID }" <c:if test="${item.ID==pd.build_body_id}">selected</c:if>>${item.BODY_NAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">楼层:</td>
				<td>
					<select name="FLOORMAN_ID" id="FLOORMAN_ID" maxlength="32" placeholder="这里选择楼层" title="楼层">
						<option value=''>请选择</option>
						<c:forEach items="${floors}" varStatus="status" var="item">
							<option value="${item.FLOORMAN_ID }" <c:if test="${item.FLOORMAN_ID==pd.FLOORMAN_ID}">selected</c:if>>${item.FLOOR }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">区域:</td>
				<td><input type="text" name="AREA" id="AREA" value="${pd.AREA}" maxlength="32" placeholder="这里输入区域" title="区域"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">入驻公司:</td>
				<td>
					<select name="COMPANY_ID" id="COMPANY_ID" maxlength="32" placeholder="这里选择公司" title="公司" value="${pd.company_id}">
						<option value=''>请选择</option>
						<c:forEach items="${company}" varStatus="status" var="item">
							<option value="${item.companyId }" <c:if test="${item.companyId == pd.company_id}">selected</c:if>>${item.companyName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">房间号:</td>
				<td><input type="text" name="ROOM_NAME" id="ROOM_NAME" value="${pd.ROOM_NAME}" maxlength="32" placeholder="这里输入房间号" title="房间号"/></td>
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
			$("#BUILD_NO").change(function(){
				appendHtml1();
			});
			$("#BUILD_BODY").change(function(){
				appendHtml2();
			});
		});

		function appendHtml1() {
			var buildId = $("#BUILD_NO").val();
			if('' == buildId || null == buildId || undefined == buildId){
				$('#BUILD_BODY').html("<option value=''>请选择</option>");
			} else {
				$.ajax({
					url: "/buildbody/selectByBuildId?buildId=" + buildId,
					type: "GET",
					success: function (data) {
						var arr = data;
						var newHtml = "<option value=''>请选择</option>";
						for ( var i = 0; i <arr.length; i++){
							newHtml = newHtml + "<option value='"+arr[i].ID+"'>"+arr[i].BODY_NAME+"</option>";
						}
						$('#BUILD_BODY').html(newHtml);
					}
				});
			}
		}

		function appendHtml2() {
			var bodyId = $("#BUILD_BODY").val();
			if('' == bodyId || null == bodyId || undefined == bodyId){
				$('#FLOORMAN_ID').html("<option value=''>请选择</option>");
			} else {
				$.ajax({
					url: "/floorman/selectByBodyId?bodyId=" + bodyId,
					type: "GET",
					success: function (data) {
						console.log("data",data)
						var arr = data;
						var newHtml = "<option value=''>请选择</option>";
						for ( var i = 0; i <arr.length; i++){
							newHtml = newHtml + "<option value='"+arr[i].FLOORMAN_ID+"'>"+arr[i].FLOOR+"</option>";
						}
						$('#FLOORMAN_ID').html(newHtml);
					}
				});
			}
		}
		</script>
</body>
</html>
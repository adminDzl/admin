<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<style type="text/css">
			#cover{
				position:absolute;left:0px;top:0px;
				background:rgba(0, 0, 0, 0.4);
				width:100%;  /*宽度设置为100%，这样才能使隐藏背景层覆盖原页面*/
				height:100%;
				filter:alpha(opacity=60);  /*设置透明度为60%*/
				opacity:0.6;  /*非IE浏览器下设置透明度为60%*/
				display:none;
				z-Index:999;
			}
		</style>
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
	            msg:'请上传图片url',
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

    function uploadImage(obj){
		cover.style.display="block";   //显示遮罩层
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
                    $("#IMAGE_URL").val(fs);
					cover.style.display="none";   //隐藏遮罩层

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
	
    function delImage(IMAGE_URL, YARD_ID) {
        var url = "yard/delImage.do?IMAGE_URL=" + IMAGE_URL + "&YARD_ID=" + YARD_ID + "&guid=" + new Date().getTime();
        $.get(url, function (data) {
            if (data === "success") {
                document.location.reload();
            }
        });
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
				<td>
					<select name="PLACE_TYPE" id="PLACE_TYPE" data-placeholder="请选择场地类型" value="${pd.PLACE_TYPE}" title="场地类型">
						<option value="">请选择场地类型</option>
						<option value="1" <c:if test="${pd.PLACE_TYPE == 1}">selected</c:if>>会议室</option>
						<option value="2" <c:if test="${pd.PLACE_TYPE == 2}">selected</c:if>>活动室</option>
						<option value="3" <c:if test="${pd.PLACE_TYPE == 3}">selected</c:if>>健身房</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">所处位置:</td>
				<td><input type="text" name="POSITION" id="POSITION" value="${pd.POSITION}" maxlength="32" placeholder="这里输入所处位置" title="所处位置"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">图片:</td>
				<td>
					<input name="fileImage" id="fileImage" value="${pd.IMAGE_URL}" placeholder="这里上传图片" title="图片url" type="file" accept="image/*" multiple="multiple" onchange="uploadImage(this)"/>
					<input name="IMAGE_URL" id="IMAGE_URL" value="${pd.IMAGE_URL}" style="display: none"/>
					<a type="<c:if test="${pd.IMAGE_URL == null}">hidden</c:if>">图片尺寸345*200，大小不能超过2M (视实际支持上传图片大小决定)</a>
					<c:if test="${pd.IMAGE_URL != '' || pd.IMAGE_URL != null }">
						<c:set value="${fn:split(pd.IMAGE_URL,',') }" var="images"/>
						<c:forEach items="${images }" var="img">
							<a href="${img}" target="_blank" >
								<img src="${img}" width="50" height="100"/>
							</a>
							<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delImage('${img}','${pd.YARD_ID}');"
								   <c:if test="${pd.IMAGE_URL == '' || pd.IMAGE_URL == null }">style="display: none" </c:if>/>
						</c:forEach>
					</c:if>
				</td>
			</tr>

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">设备描述:</td>
				<td><input type="text" name="EQUIPMENT" id="EQUIPMENT" value="${pd.EQUIPMENT}" maxlength="32" placeholder="这里输入设备描述" title="设备描述"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">价格(元/半小时):</td>
				<td><input type="text" name="RENT_FEE" id="RENT_FEE" value="${pd.RENT_FEE}" maxlength="32" placeholder="这里输入价格(元/半小时)" title="价格"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">客服号码:</td>
				<td><input type="text" name="SERVICE_PHONE" id="SERVICE_PHONE" value="${pd.SERVICE_PHONE}" maxlength="32" placeholder="这里输入客服号码" title="客服号码"/></td>
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
		<!-- 通过遮罩层遮住背景 -->
		<div id="cover"></div>
	</form>


		<!-- 引入 -->
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
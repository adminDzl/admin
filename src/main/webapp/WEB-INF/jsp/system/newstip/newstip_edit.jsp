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

		if($("#NEWS_TYPE").val()==""){
			$("#NEWS_TYPE").tips({
				side:3,
	            msg:'请输入新闻类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NEWS_TYPE").focus();
			return false;
		}
		if($("#NEWS_TITLE").val()==""){
			$("#NEWS_TITLE").tips({
				side:3,
	            msg:'请输入新闻标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NEWS_TITLE").focus();
			return false;
		}
		if($("#NEWS_CONTENT").val()==""){
			$("#NEWS_CONTENT").tips({
				side:3,
	            msg:'请输入新闻内容',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NEWS_CONTENT").focus();
			return false;
		}
		if($("#ATTACH_URL").val()==""){
			$("#ATTACH_URL").tips({
				side:3,
	            msg:'请输入附件url',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ATTACH_URL").focus();
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

    $(function(){
        window.setTimeout(setContent,1000);//一秒后再调用赋值方法
    });

    //给ueditor插入值
    function setContent(){
        UE.getEditor('editor').execCommand('insertHtml', $('#testcon').html());
    }

    //上传图片
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
                    $("#HEAD_IMAGE").val(fs);

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

    //删除图片
    function delImage(img, id) {
        var url = "newstip/delImage.do?HEAD_IMAGE=" + img + "&NEWSTIP_ID=" + id + "&guid=" + new Date().getTime();
        $.get(url, function (data) {
            if (data === "success") {
                document.location.reload();
            }
        });
    }
</script>
	</head>
<body>
	<form action="newstip/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="NEWSTIP_ID" id="NEWSTIP_ID" value="${pd.NEWSTIP_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">新闻标题:</td>
				<td>
					<input type="text" name="NEWS_TITLE" id="NEWS_TITLE" value="${pd.NEWS_TITLE}" maxlength="32" placeholder="这里输入新闻标题" title="新闻标题"/>
					<input type="number" name="NEWS_TYPE" id="NEWS_TYPE" value="1" style="display: none"/>
				</td>
			</tr>

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">新闻头图:</td>
				<td>
					<input name="fileImage" id="fileImage" value="${pd.HEAD_IMAGE}" placeholder="这里上传图片" title="图片url" type="file" accept="image/*" multiple="multiple" onchange="uploadImage(this)"/>
					<input name="HEAD_IMAGE" id="HEAD_IMAGE" value="${pd.HEAD_IMAGE}" style="display: none"/>
					<c:if test="${pd.HEAD_IMAGE != '' || pd.HEAD_IMAGE != null }">
						<a href="${pd.HEAD_IMAGE}" target="_blank" >
							<img src="${pd.HEAD_IMAGE}" width="50" height="100"/>
						</a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delImage('${pd.HEAD_IMAGE}','${pd.NEWSTIP_ID}');"
							   <c:if test="${pd.HEAD_IMAGE == '' || pd.HEAD_IMAGE == null }">style="display: none" </c:if>/>
					</c:if>
				</td>
			</tr>

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">新闻内容:</td>
				<td>
                    <textarea id="editor" name="NEWS_CONTENT" type="text/plain" style="width: 690px; height: 430px; margin: 0 auto;" ></textarea >
					<code id="testcon" style="display:none;">
						${pd.NEWS_CONTENT}
					</code>
				</td>
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

		<!-- 编辑框-->
		<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
		<!-- 编辑框-->

		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>
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
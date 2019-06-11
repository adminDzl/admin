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
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();;
        }

        //上传文件
        function uploadFile(obj){
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
                        $("#ATTACH_URL").val(fs)
                    }
                }
            });
        }

	</script>
</head>
<body>
<form action="decorate/${msg }.do" name="Form" id="Form" method="post">
	<input type="hidden" name="NEWSTIP_ID" id="NEWSTIP_ID" value="${pd.NEWSTIP_ID}"/>
	<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">新闻标题:</td>
				<td>
					<input type="text" name="NEWS_TITLE" id="NEWS_TITLE" value="${pd.NEWS_TITLE}" maxlength="32" placeholder="这里输入新闻标题" title="新闻标题"/>
					<input type="number" name="NEWS_TYPE" id="NEWS_TYPE" value="3" style="display: none"/>
				</td>
			</tr>

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">附件url:</td>
				<td>
					<input name="minfile" id="minfile" value="${pd.ATTACH_URL}" placeholder="这里输入附件url" title="附件url" type="file" multiple="multiple" onchange="uploadFile(this)"/>
					<input name="ATTACH_URL" id="ATTACH_URL" value="${pd.ATTACH_URL}" style="display: none"/>
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
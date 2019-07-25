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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">

	//保存
	function save(){
		if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
	            msg:'请输入标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TITLE").focus();
			return false;
		}
        if($("#PATH").val()==""){
            $("#PATH").tips({
                side:3,
                msg:'请选择图片',
                bg:'#AE81FF',
                time:2
            });
            $("#PATH").focus();
            return false;
        }
        if($("#LINK_ID").val()==""){
            $("#LINK_ID").tips({
                side:3,
                msg:'请输入跳转链接',
                bg:'#AE81FF',
                time:2
            });
            $("#LINK_ID").focus();
            return false;
        }
		if($("#BZ").val()==""){
			$("#BZ").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BZ").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	//过滤类型
	function fileType(obj){
		var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
	    	$("#tp").tips({
				side:3,
	            msg:'请上传图片格式的文件',
	            bg:'#AE81FF',
	            time:3
	        });
	    	$("#tp").val('');
	    	document.getElementById("tp").files[0] = '请选择图片';
	    }
	}
	
	//删除图片
	function delP(PATH,PICTURES_ID){
		 if(confirm("确定要删除图片？")){
			var url = "pictures/deltp.do?PATH="+PATH+"&PICTURES_ID="+PICTURES_ID+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}

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
                    $("#PATH").val(fs)
                }
            }
        });
    }
</script>
	</head>
<body>
	<form action="pictures/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PICTURES_ID" id="PICTURES_ID" value="${pd.PICTURES_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<th>标题:</th>
				<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" style="width:95%;" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<th>图片:</th>
				<td>
					<%--<c:if test="${pd == null || pd.PATH == '' || pd.PATH == null }">--%>
					<%--<input type="file" id="tp" name="tp" onchange="fileType(this)"/>--%>
					<%--</c:if>--%>
					<%--<c:if test="${pd != null && pd.PATH != '' && pd.PATH != null }">--%>
						<%--<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.PATH}" target="_blank" ><img src="<%=basePath%>uploadFiles/uploadImgs/${pd.PATH}" width="210"/></a>--%>
						<%--<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.PATH}','${pd.PICTURES_ID }');" />--%>
						<%--<input type="hidden" name="tpz" id="tpz" value="${pd.PATH }"/>--%>
					<%--</c:if>--%>
					<input name="fileImage" id="fileImage" value="${pd.PATH}" placeholder="这里上传图片" title="图片url" type="file" accept="image/*" multiple="multiple" onchange="uploadImage(this)"/>
					<input type="hidden" name="PATH" id="PATH" value="${pd.PATH }"/>
				</td>
			</tr>
			<tr>
				<th>跳转新闻链接:</th>
				<td>
					<select name="LINK_ID" id="LINK_ID" maxlength="32" style="width:95%;" placeholder="这里新闻跳转链接" title="这里新闻跳转链接" >
						<option value=''>请选择</option>
						<c:forEach items="${news}" varStatus="status" var="item">
							<option value="${item.newstipId }" <c:if test="${item.newstipId == pd.LINK_ID}">selected</c:if>>
									${item.newsTitle }
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>备注:</th>
				<td><input type="text" name="BZ" id="BZ" value="${pd.BZ}" maxlength="32" style="width:95%;" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//上传
			$('#tp').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			
		});
		
		</script>
</body>
</html>
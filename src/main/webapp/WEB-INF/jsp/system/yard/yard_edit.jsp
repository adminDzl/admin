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
		<style>
			.butn {
				border-radius: 0px;
				font-weight: 100;
				cursor: pointer;
				display: inline-block;
				padding: 5px;
				font-size: 14px;
				font-family: '微软雅黑'
			}
			.butn-primary {
				color: #fff;
				text-shadow: 0 1px rgba(0,0,0,.1);
				background-image: -webkit-linear-gradient(top,#4d90fe 0,#4787ed 100%);
				background-image: -o-linear-gradient(top,#4d90fe 0,#4787ed 100%);
				background-image: -webkit-gradient(linear,left top,left bottom,from(#4d90fe),to(#4787ed));
				background-image: linear-gradient(to bottom,#4d90fe 0,#4787ed 100%);
				filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff4d90fe', endColorstr='#ff4787ed', GradientType=0);
				filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
				background-repeat: repeat-x;
				border: 1px solid #3079ed;
			}
			/* 多图上传 */
			.z_photo {
				width:94%;
				margin:0 auto;
				overflow: auto;
			}
			.z_photo img {
				width: 4.8rem;
				height:3.15rem;
			}

			.z_addImg {
				position:relative;
				float: left;
				margin:0 0.45rem 0.5rem 0;
			}
			.z_addImg:nth-of-type(3n){
				margin-right:0;
			}
			.z_addImg .imgdel{
				position:absolute;
				top:0.3rem;
				right:0.3rem;
				display:block;
				width:1.0rem;
				height:1.0rem;
				/*background:url('../images/delete.png') no-repeat;*/
				background-size:100% 100%;
			}
			.z_file {
				width: 4.8rem;
				height:3.15rem;
				/*background: url('../images/z_add.png') no-repeat;*/
				background-size: 100% 100%;
				float: left;
			}

			.z_file input::-webkit-file-upload-button {
				width: 4.8rem;
				height:3.15rem;
				border: none;
				position: absolute;
				outline: 0;
				opacity: 0;
			}

			.z_file input#file {
				display: block;
				width: 4.8rem;
				height:3.15rem;
				border: 0;
				vertical-align: middle;
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

    var files=[];
    var that = this;

    function uploadImage() {
        $("#IMAGE_URL").trigger("click");
    }

//    $("#IMAGE_URL").change(function(){
//        //获取所有图片
//        var img = document.getElementById("IMAGE_URL").files;
//        //遍历
//        for (var i = 0; i < img.length; i++) {
//            //得到图片
//            var file = img[i];
//            //把图片存到数组中
//            files[id] = file;
//            id++;
//            //获取图片路径
//            var url = URL.createObjectURL(file);
//            //创建img
//            var box = document.createElement("img");
//            box.setAttribute("src", url);
//            box.className = 'img';
//            //创建div
//            var imgBox = document.createElement("div");
//            imgBox.style.display = 'inline-block';
//            imgBox.className = 'img-item';
//            //创建span
//            var deleteIcon = document.createElement("span");
//            deleteIcon.className = 'delete';
//            deleteIcon.innerText = 'x';
//            //把图片名绑定到data里面
//            deleteIcon.id = img[i].name;
//            //把img和span加入到div中
//            imgBox.appendChild(deleteIcon);
//            imgBox.appendChild(box);
//            //获取id=gallery的div
//            var body = document.getElementsByClassName("gallery")[0];
//            body.appendChild(imgBox);
//            //点击span事件
//            $(deleteIcon).click(function() {
//                //获取data中的图片名
//                var filename = $(this).attr('id');
//                alert(filename);
//                //删除父节点
//                $(this).parent().remove();
//                var fileList = Array.from(files);
//                //遍历数组
//                for (var j = 0; j < fileList.length; j++) {
//                    //通过图片名判断图片在数组中的位置然后删除
//                    alert(fileList[j].name+"111");
//                    if (fileList[j].name == filename) {
//                        fileList.splice(j, 1);
//                        id--;
//                        break;
//                    }
//                }
//                files = fileList;
//            });
//        }
//    })

    function imgChange(obj1, obj2) {
        //获取点击的文本框
        var file = document.getElementById("IMAGE_URL");
        //存放图片的父级元素
        var imgContainer = document.getElementsByClassName(obj1)[0];
        //获取的图片文件
        var fileList = file.files;
        //文本框的父级元素
        var input = document.getElementsByClassName(obj2)[0];
        var imgArr = [];
        //遍历获取到得图片文件
        for (var i = 0; i < fileList.length; i++) {
            var imgUrl = window.URL.createObjectURL(file.files[i]);
            imgArr.push(imgUrl);
            var img = document.createElement("img");
            img.setAttribute("src", imgArr[i]);
            var imgdel = document.createElement("span");
            imgdel.setAttribute("class", "imgdel");
            var imgAdd = document.createElement("div");
            imgAdd.setAttribute("class", "z_addImg");
            imgAdd.appendChild(img);
            imgAdd.appendChild(imgdel);
            imgContainer.prepend(imgAdd);
        };
        imgRemove();
    };

    function imgRemove() {
        var imgList = document.getElementsByClassName("z_addImg");
        var imgdel = document.getElementsByClassName("imgdel");
        for (var j = 0; j < imgList.length; j++) {
            imgList[j].index = j;
            imgList[j].onclick = function() {
                var t = this;
                $.confirm({
                    title: '确认删除？',
                    onOK: function () {
                        t.style.display='none';
                    },
                    onCancel: function () {
                    }
                });
            }
        };
    };

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
				<td style="width:70px;text-align: right;padding-top: 13px;">图片:</td>
				<td>
					<div class="z_photo">
						<div class="z_file">
					<%--<div id="upload" class="butn butn-primary" onclick="uploadImage();">选择图片</div>--%>
					<input name="IMAGE_URL" id="IMAGE_URL" value="${pd.IMAGE_URL}" placeholder="这里上传图片" title="图片url" type="file" accept="image/*" multiple="multiple" onchange="imgChange('z_photo','z_file');"/>
				</td>
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
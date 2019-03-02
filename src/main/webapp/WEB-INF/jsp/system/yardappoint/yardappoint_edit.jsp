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
            if($("#PLACE_ID").val()==""){
                $("#PLACE_ID").tips({
                    side:3,
                    msg:'请输入场地ID',
                    bg:'#AE81FF',
                    time:2
                });
                $("#PLACE_ID").focus();
                return false;
            }
            if($("#APPLY_USER_ID").val()==""){
                $("#APPLY_USER_ID").tips({
                    side:3,
                    msg:'请输入预定人ID',
                    bg:'#AE81FF',
                    time:2
                });
                $("#APPLY_USER_ID").focus();
                return false;
            }
            if($("#PLACE_DATE").val()==""){
                $("#PLACE_DATE").tips({
                    side:3,
                    msg:'请输入预约日期',
                    bg:'#AE81FF',
                    time:2
                });
                $("#PLACE_DATE").focus();
                return false;
            }
            if($("#BEGIN_TIME").val()==""){
                $("#BEGIN_TIME").tips({
                    side:3,
                    msg:'请输入预定开始时间',
                    bg:'#AE81FF',
                    time:2
                });
                $("#BEGIN_TIME").focus();
                return false;
            }
            if($("#END_TIME").val()==""){
                $("#END_TIME").tips({
                    side:3,
                    msg:'请输入预约结束时间',
                    bg:'#AE81FF',
                    time:2
                });
                $("#END_TIME").focus();
                return false;
            }
            if($("#BOOK_DURATION").val()==""){
                $("#BOOK_DURATION").tips({
                    side:3,
                    msg:'请输入预定时长',
                    bg:'#AE81FF',
                    time:2
                });
                $("#BOOK_DURATION").focus();
                return false;
            }
            if($("#BOOK_FEE").val()==""){
                $("#BOOK_FEE").tips({
                    side:3,
                    msg:'请输入预定金额',
                    bg:'#AE81FF',
                    time:2
                });
                $("#BOOK_FEE").focus();
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
            if($("#NOTE").val()==""){
                $("#NOTE").tips({
                    side:3,
                    msg:'请输入备注',
                    bg:'#AE81FF',
                    time:2
                });
                $("#NOTE").focus();
                return false;
            }
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
	</script>
</head>
<body>
<form action="yardappoint/${msg }.do" name="Form" id="Form" method="post">
	<input type="hidden" name="YARDAPPOINT_ID" id="YARDAPPOINT_ID" value="${pd.YARDAPPOINT_ID}"/>
	<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">场地ID:</td>
				<td><input type="text" name="PLACE_ID" id="PLACE_ID" value="${pd.PLACE_ID}" maxlength="32" placeholder="这里输入场地ID" title="场地ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预定人ID:</td>
				<td><input type="text" name="APPLY_USER_ID" id="APPLY_USER_ID" value="${pd.APPLY_USER_ID}" maxlength="32" placeholder="这里输入预定人ID" title="预定人ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预约日期:</td>
				<td><input class="span10 date-picker" name="PLACE_DATE" id="PLACE_DATE" value="${pd.PLACE_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预约日期" title="预约日期"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预定开始时间:</td>
				<td><input class="span10 date-picker" name="BEGIN_TIME" id="BEGIN_TIME" value="${pd.BEGIN_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预定开始时间" title="预定开始时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预约结束时间:</td>
				<td><input class="span10 date-picker" name="END_TIME" id="END_TIME" value="${pd.END_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预约结束时间" title="预约结束时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预定时长:</td>
				<td><input type="number" name="BOOK_DURATION" id="BOOK_DURATION" value="${pd.BOOK_DURATION}" maxlength="32" placeholder="这里输入预定时长" title="预定时长"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">预定金额:</td>
				<td><input type="text" name="BOOK_FEE" id="BOOK_FEE" value="${pd.BOOK_FEE}" maxlength="32" placeholder="这里输入预定金额" title="预定金额"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
				<td><input type="number" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
				<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
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
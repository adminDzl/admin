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
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<form action="usercarbind/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="field1" value="" placeholder="这里输入车主姓名" />
							<i class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
					<td style="vertical-align:top;"> 
					 	<select name="field2" id="field2" data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
							<option value="" disabled selected style="display:none;">请选择状态</option>
							<option value="">全部</option>
							<option value="0">待审核</option>
							<option value="1">绑定成功</option>
							<option value="2">审核拒绝</option>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i class="icon-search"></i></button></td>
					<%--<c:if test="${QX.cha == 1 }">--%>
					<%--<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i class="icon-download-alt"></i></a></td>--%>
					<%--</c:if>--%>
				</tr>
			</table>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">姓名</th>
						<th class="center">车牌号</th>
						<th class="center">绑定状态</th>
						<th class="center">备注</th>
						<th class="center">申请时间</th>
						<th class="center">审核时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.USER_CAR_BIND_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.NAME}</td>
										<td>${var.CAR_NO}</td>
										<td>
											<c:if test="${var.STATUS == '0' }"><span class="label label-important arrowed-in">待审核</span></c:if>
											<c:if test="${var.STATUS == '1' }"><span class="label label-success arrowed">绑定成功</span></c:if>
											<c:if test="${var.STATUS == '2' }"><span class="label label-important arrowed-in">审核拒绝</span></c:if>
										</td>
										<td>${var.REMARK}</td>
										<td>${var.CREATE_TIME}</td>
										<td>${var.UPDATE_TIME}</td>
								<td style="width: 100px;" class="center">
									<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
									</c:if>

									<c:if test="${QX.edit == 1 }">
										<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.USER_CAR_BIND_ID}');"><i class="icon-edit"></i></a>
									</c:if>
									<c:if test="${QX.del == 1 }">
										<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.USER_CAR_BIND_ID}');"><i class='icon-trash'></i></a>
									</c:if>

									<c:if test="${var.STATUS == '0'}">
										<c:if test="${QX.edit == 1 }">
											<a class='btn btn-mini btn-green' title="审核" onclick="audit('${var.USER_CAR_BIND_ID}');"><i class='icon-check'></i></a>
										</c:if>
									</c:if>
								</td>
							</tr>
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</c:if>
					<c:if test="${QX.del == 1 }">
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
					</c:if>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
  </div>
</div>
</div>
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
			$(top.hangge());
			function search(){
				top.jzts();
				$("#Form").submit();
			}
			function add(){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>usercarbind/goAdd.do';
				 diag.Width = 450;
				 diag.Height = 355;
				 diag.CancelEvent = function(){
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location=self.location",100);
						 }else{
							 nextPage(${page.currentPage});
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			function del(Id){
				bootbox.confirm("确定要删除吗?", function(result) {
					if(result) {
						top.jzts();
						var url = "<%=basePath%>usercarbind/delete.do?USER_CAR_BIND_ID="+Id+"&tm="+new Date().getTime();
						$.get(url,function(data){
							nextPage(${page.currentPage});
						});
					}
				});
			}
			function edit(Id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>usercarbind/goEdit.do?USER_CAR_BIND_ID='+Id;
				 diag.Width = 450;
				 diag.Height = 355;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage(${page.currentPage});
					}
					diag.close();
				 };
				 diag.show();
			}
			function audit(Id) {
                top.jzts();
                var diag = new top.Dialog();
                diag.Drag=true;
                diag.Title ="编辑";
                diag.URL = '<%=basePath%>usercarbind/goCheck.do?USER_CAR_BIND_ID='+Id;
                diag.Width = 450;
                diag.Height = 355;
                diag.CancelEvent = function(){ //关闭事件
                    if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                        nextPage(${page.currentPage});
                    }
                    diag.close();
                };
                diag.show();
            }
		</script>
		<script type="text/javascript">
		$(function() {
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			$('.date-picker').datepicker();
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
			});
		});
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
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
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>usercarbind/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		function toExcel(){
			window.location.href='<%=basePath%>usercarbind/excel.do';
		}
		</script>
	</body>
</html>
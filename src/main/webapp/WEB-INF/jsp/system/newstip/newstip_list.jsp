<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="newstip/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="field1" value="" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
					<%--<td style="vertical-align:top;"> --%>
					 	<%--<select class="chzn-select" name="field2" id="field2" data-placeholder="请选择" style="vertical-align:top;width: 120px;">--%>
							<%--<option value=""></option>--%>
							<%--<option value="">全部</option>--%>
							<%--<option value="">1</option>--%>
							<%--<option value="">2</option>--%>
					  	<%--</select>--%>
					<%--</td>--%>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<%--<c:if test="${QX.cha == 1 }">--%>
					<%--<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>--%>
					<%--</c:if>--%>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">信息类型</th>
						<th class="center">信息标题</th>
						<th class="center">信息内容</th>
						<th class="center">状态</th>
						<th class="center">创建时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.NEWSTIP_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>
											<c:if test="${var.NEWS_TYPE == 1}"><span class="label label-success arrowed">信息</span></c:if>
											<c:if test="${var.NEWS_TYPE == 2}"><span class="label label-success arrowed">项目申报</span></c:if>
										</td>
										<td>
											<c:choose>
												<c:when test="${fn:length(var.NEWS_TITLE) >= 30}">
													<span title="${var.NEWS_TITLE}">${fn:substring(var.NEWS_TITLE ,0,30)}……</span>
												</c:when>
												<c:otherwise>
													<span title="${var.NEWS_TITLE }">${var.NEWS_TITLE }</span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${fn:length(var.NEWS_CONTENT) >= 30}">
													<span title="${var.NEWS_CONTENT}">${fn:substring(var.NEWS_CONTENT ,0,30)}……</span>
												</c:when>
												<c:otherwise>
													<span title="${var.NEWS_CONTENT }">${var.NEWS_CONTENT }</span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:if test="${var.STATUS == 0}"><span class="label label-important arrowed">待发布</span></c:if>
											<c:if test="${var.STATUS == 1}"><span class="label label-success arrowed">已发布</span></c:if>
										</td>
										<td>${var.CREATE_TIME}</td>
								<td style="width: 100px;" class="center">
									<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
									</c:if>

									<c:if test="${QX.edit == 1 }">
										<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.NEWSTIP_ID}');"><i class="icon-edit"></i></a>
									</c:if>
									<c:if test="${QX.del == 1 }">
										<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.NEWSTIP_ID}');"><i class='icon-trash'></i></a>
									</c:if>

									<c:if test="${var.STATUS == 0}">
										<c:if test="${QX.edit == 1 }">
											<a class='btn btn-mini btn-green' title="发布" onclick="audit('${var.NEWSTIP_ID}');"><i class='icon-check'></i></a>
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
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		$(top.hangge());
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>newstip/goAdd.do';
			 diag.Width = 800;
			 diag.Height = 655;
			 diag.CancelEvent = function(){ //关闭事件
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
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>newstip/delete.do?NEWSTIP_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>newstip/goEdit.do?NEWSTIP_ID='+Id;
             diag.Width = 800;
             diag.Height = 655;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		//发布
        function audit(Id) {
            bootbox.confirm("确定要发布该新闻吗?", function(result) {
                if(result) {
                    top.jzts();
                    var url = "<%=basePath%>newstip/release.do?NEWSTIP_ID="+Id+"&tm="+new Date().getTime();
                    $.get(url,function(data){
                        nextPage(${page.currentPage});
                    });
                }
            });
        }
		</script>
		<script type="text/javascript">
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			//日期框
			$('.date-picker').datepicker();
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
			});
		});
		//批量操作
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
								url: '<%=basePath%>newstip/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
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
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>newstip/excel.do';
		}
		</script>
		
	</body>
</html>


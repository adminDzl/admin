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
			<form action="payordersum/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<%--<td><input class="span10 date-picker" name="TIME" id="TIME" value="${pd.TIME}" type="text" data-date-format="yyyy" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>--%>
					<%--<td  style="vertical-align:top;">--%>
						<%--<select class="chzn-select" name="TIME" data-placeholder="请选择年份" style="vertical-align:top;width: 120px;">--%>
							<%--<option value=""></option>--%>
							<%--<option value="">全部</option>--%>
							<%--<option value="2019" <c:if test="${pd.TIME == '2019' }">selected</c:if>>2019</option>--%>
							<%--<option value="2020" <c:if test="${pd.TIME == '2020' }">selected</c:if>>2020</option>--%>
							<%--<option value="2021" <c:if test="${pd.TIME == '2021' }">selected</c:if>>2021</option>--%>
							<%--<option value="2022" <c:if test="${pd.TIME == '2022' }">selected</c:if>>2022</option>--%>
							<%--<option value="2023" <c:if test="${pd.TIME == '2023' }">selected</c:if>>2023</option>--%>
							<%--<option value="2024" <c:if test="${pd.TIME == '2024' }">selected</c:if>>2024</option>--%>
							<%--<option value="2025" <c:if test="${pd.TIME == '2025' }">selected</c:if>>2025</option>--%>
							<%--<option value="2026" <c:if test="${pd.TIME == '2026' }">selected</c:if>>2026</option>--%>
							<%--<option value="2027" <c:if test="${pd.TIME == '2027' }">selected</c:if>>2027</option>--%>
							<%--<option value="2028" <c:if test="${pd.TIME == '2028' }">selected</c:if>>2028</option>--%>
							<%--<option value="2029" <c:if test="${pd.TIME == '2029' }">selected</c:if>>2029</option>--%>
							<%--<option value="2030" <c:if test="${pd.TIME == '2030' }">selected</c:if>>2030</option>--%>
							<%--<option value="2031" <c:if test="${pd.TIME == '2031' }">selected</c:if>>2031</option>--%>
							<%--<option value="2032" <c:if test="${pd.TIME == '2032' }">selected</c:if>>2032</option>--%>
							<%--<option value="2033" <c:if test="${pd.TIME == '2033' }">selected</c:if>>2033</option>--%>
							<%--<option value="2034" <c:if test="${pd.TIME == '2034' }">selected</c:if>>2034</option>--%>
							<%--<option value="2035" <c:if test="${pd.TIME == '2035' }">selected</c:if>>2035</option>--%>
						<%--</select>--%>
					<%--</td>--%>
					<%--<td>--%>
						<%--<span class="input-icon">--%>
							<%--<input autocomplete="off" id="nav-search-input" type="text" name="COMPANY_NAME" value="${pd.COMPANY_NAME}" placeholder="公司名搜索" />--%>
							<%--<i class="icon-search"></i>--%>
						<%--</span>--%>
					<%--</td>--%>
					<%--<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i class="icon-search"></i></button></td>--%>
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
						<th class="center">公司名称</th>
						<th class="center">当年共需缴纳</th>
						<th class="center">当年已缴纳</th>
						<th class="center">当年未缴纳</th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.PAYORDER_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${var.COMPANY_NAME}</td>
								<td>${var.T}</td>
								<td>${var.Y}</td>
								<td>${var.D}</td>
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
				<%--<td style="vertical-align:top;">--%>
					<%--<a class="btn btn-small btn-warning" onclick="query();">查看未缴费公司</a>--%>
					<%--<a class="btn btn-small btn-info" onclick="payment();">一键催缴</a>--%>
				<%--</td>--%>
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
								url: '<%=basePath%>payorder/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>payordersum/excel.do';
		}
		</script>
	</body>
</html>
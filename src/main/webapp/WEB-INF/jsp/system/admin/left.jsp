﻿<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
<div id="sidebar">
	<div id="sidebar-shortcuts">
		<div id="sidebar-shortcuts-large">
			<button class="btn btn-small btn-success" title="系统首页" onclick="window.open('<%=basePathl%>main/index');"><i class="icon-home"></i></button>
			<%--<button class="btn btn-small btn-info" title="园区概览" onclick="window.open('<%=basePathl%>static/UI_new/index.html');"><i class="icon-eye-open"></i></button>--%>
			<button class="btn btn-small btn-info" title="接口文档" onclick="window.open('<%=basePathl%>swagger-ui.html');"><i class="icon-certificate"></i></button>
			<button class="btn btn-small btn-warning" title="数据字典" id="adminzidian" onclick="zidian();"><i class="icon-book"></i></button>
			<button class="btn btn-small btn-danger" title="菜单管理" id="adminmenu" onclick="menu();"><i class="icon-folder-open"></i></button>
		</div>
		<div id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>
			<span class="btn btn-info"></span>
			<span class="btn btn-warning"></span>
			<span class="btn btn-danger"></span>
		</div>
	</div>
	<ul class="nav nav-list">
	<c:forEach items="${menuList}" var="menu">
		<c:if test="${menu.hasMenu}">
		<li id="lm${menu.MENU_ID }">
			  <a style="cursor:pointer;" class="dropdown-toggle" >
				<i class="${menu.MENU_ICON == null ? 'icon-desktop' : menu.MENU_ICON}"></i>
				<span>${menu.MENU_NAME }</span>
				<b class="arrow icon-angle-down"></b>
			  </a>
			  <ul class="submenu">
					<c:forEach items="${menu.subMenu}" var="sub">
						<c:if test="${sub.hasMenu}">
						<c:choose>
							<c:when test="${not empty sub.MENU_URL}">
							<li id="z${sub.MENU_ID }">
							<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${sub.MENU_ID }','lm${menu.MENU_ID }','${sub.MENU_NAME }','${sub.MENU_URL }')"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:when>
							<c:otherwise>
							<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:otherwise>
						</c:choose>
						</c:if>
					</c:forEach>
		  		</ul>
		</li>
		</c:if>
	</c:forEach>
	</ul>
	<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>
</div>

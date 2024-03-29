<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include_tag.jsp"%>
<%@ include file="../../common/page_var.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=pageTitle%> - 菜单管理</title>

<%@ include file="../../common/page_head.jsp"%>

<script type="text/javascript">
        
    function add(){
    	$.jBox("iframe:<s:url value='/menu/view.do'/>?menuId=0", {
    	    title: "添加菜单",
    	    width: 700,
    	    height: 380,
    	    buttons: {}
    	});
    }


    function edit(id){
    	$.jBox("iframe:<s:url value='/menu/view.do'/>?menuId="+id, {
    	    title: "修改菜单",
    	    width: 700,
    	    height: 380,
    	    buttons: {}
    	});
    }
    
    function delMenu(id,msg){

    	$.jBox.confirm("确定删除 ["+msg+"] 菜单吗？", "确认操作", function (v, h, f) {
    	    if (v == 'ok'){
    	    	
    	    	$.post("<s:url value='/menu/delMenu.do'/>",{id:id},function(responseText){
    	    		if(responseText==true){
    	    			$.jBox.info("操作成功，请刷新查看结果", "成功信息",{top: '20%'});
    	    			window.location.reload();
    	    		}else{
    	    			$.jBox.error("操作失败", "失败信息");
    	    		}
    	    	});

    	    	
    		}
    	    return true; 
    	},{top: '40%'});
    	
    }
    
    $().ready(function(){
    	pilicat.alternately('list');

    });

    </script>
</head>
<body>


	<div id="body_box">
		<table cellpadding="0" cellspacing="0" border="0" class="icon">
			<tr>
				<td></td>
			</tr>
		</table>

		<table cellpadding="0" cellspacing="0" border="0" class="table">
			<tbody>
				<tr>
					<td valign="top" align="right">
						<form action="" method="get" name="search" id="search">
							<table cellpadding="0" cellspacing="5" border="0">
								<tbody>
									<tr>
										<td><a class="button_4" href="javascript:;"
											onclick="add();">添加新菜单</a></td>
									</tr>
								</tbody>
							</table>
						</form>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="rounded table">
			<form action="" method="get" name="search_form" id="search_form">
				<table width="100%" cellpadding="0" cellspacing="0" border="0"
					class="box_top">
					<tr>
						<td class="title">菜单列表</td>
						<td></td>
					</tr>
				</table>
			</form>

			<form action="" method="post" name="manage" id="manage">
				<table class="list td_align" cellpadding="0" cellspacing="1"
					border="0">
					<tr>
						<td class="field_head" width="30">选择</td>
						<td class="field_head">菜单ID</td>
						<td class="field_head">菜单名称</td>
						<td class="field_head">菜单图片</td>
						<td class="field_head">菜单Url</td>
						<td class="field_head">菜单显示</td>
						<td class="field_head">创建时间</td>
						<td class="field_head">最后更新时间</td>
						<td class="field_head">操作</td>

					</tr>

					<c:if test="${totalCount==0}">
						<tr>
							<td colspan="9" class="field_head">没有相关数据</td>
						</tr>
					</c:if>

					<c:forEach items="${dataList}" var="dataItem" varStatus="rowStatus">

						<c:choose>
							<c:when test="${rowStatus.index%2==0}">
								<tr class="odd">
							</c:when>
							<c:otherwise>
								<tr class="even">
							</c:otherwise>
						</c:choose>

						<td><input type="checkbox" name="cbitem"
							value="${dataItem.id }" /></td>
						<td>${dataItem.id}</td>
						<td>${dataItem.menuName}</td>
						<td>${dataItem.icon}</td>
						<td
							style="text-align: left; word-wrap: break-word; word-break: break-all;">
							${dataItem.url}</td>
						<td><c:choose>
								<c:when test="${dataItem.display==0}">
									<font color='red'>隐藏</font>
								</c:when>
								<c:when test="${dataItem.display==1}">
									<font color='green'>显示</font>
								</c:when>
								<c:otherwise>
									<font color='gray'>未知:${dataItem.display}</font>
								</c:otherwise>
							</c:choose></td>
						<td><fmt:formatDate value="${dataItem.insertTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${dataItem.insertTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="operation"><a href="javascript:;"
							onclick="edit(${dataItem.id});"> <img
								src="<s:url value='/css/images/operation/pencil.png'/>"
								title="修改菜单信息" />
						</a> <a href="javascript:;"
							onclick="delMenu(${dataItem.id},'${dataItem.menuName}');"> <img
								src="<s:url value='/css/images/operation/trashcan_delete.png'/>"
								title="删除菜单" />
						</a></td>

						</tr>
					</c:forEach>

				</table>
				<table class="table top_line">
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td class="all_action"><input name="allChkbox"
										id="allChkbox" type="checkbox"
										onclick="pilicat.select_all(this.form);" /></td>
									<td>&nbsp;全选/取消 &nbsp;</td>
									<td class="operation"></td>

								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>

			<table class="page" cellpadding="0" cellspacing="5">
				<tr>
					<td>
						<div id="pageNav" class="scott">
							<font color="#88af3f">共${totalCount} 条数据， ${totalPage} 页</font>
							${pageNav}
						</div>

					</td>
					<td></td>
				</tr>
			</table>
		</div>


	</div>
</body>
</html>

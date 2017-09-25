<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'company_checkbox')" style="margin-right: 9px;"></th>
			<th>名称</th>
			<th>简称</th>
			<th>电话</th>
			<th>类型</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr ondblclick="company.detail('${result.id}');" pathCode="${result.pathCode}">
				<td class="grid-checkbox">
					<c:if test="${result.childrenNum == 0}">
						<input type="checkbox" name="company_checkbox" />
					</c:if>
					<c:if test="${result.childrenNum != 0}">
						<input type="checkbox" disabled="disabled"/>
						<input type="hidden" name="company_checkbox" />
					</c:if>
					<input type="hidden" name="company_id" value="${result.id}"/>
				</td>
				<td>
					<c:if test="${result.childrenNum == 0}">
						<img src="/static/css/images/defaultLeaf.gif" />
					</c:if>
					<c:if test="${result.childrenNum > 0}">
						<img id="open_${result.id}" rel="close_${result.id}" onclick="company.closeFolder(event)" src="/static/css/images/folderOpen.gif" <c:if test="${childDisplay==0}">style="display:none;"</c:if>/>
						<img id="close_${result.id}" rel="open_${result.id}" onclick="company.openFolder(event)" src="/static/css/images/folderClose.gif" <c:if test="${childDisplay!=0}">style="display:none;"</c:if>/>
					</c:if>
					${result.name}
				</td>
				<td>${result.simpleName}</td>
				<td>${result.tel}</td>
				<td>${typeMap[fn:trim(result.type)].value}</td>
				<td>
					<a href="javascript:company.detail('${result.id}');">查看</a>
					<a href="javascript:company.update('${result.id}');">修改</a>
					<c:if test="${result.childrenNum == 0}">
						<a href="javascript:company.deleteSingle('${result.id}');">删除</a>
					</c:if>
				</td>
			</tr>
			<c:set var="cindex" value="0" />
			<c:forEach items="${result.children}" var="child">
				<c:set var="cindex" value="${cindex + 1}" />
				<tr ondblclick="company.detail('${child.id}');" <c:if test="${childDisplay==0}">style="display:none;"</c:if> pathCode="${child.pathCode}">
					<td class="grid-checkbox">
						<c:if test="${child.childrenNum == 0}">
							<input type="checkbox" name="company_checkbox" />
						</c:if>
						<c:if test="${child.childrenNum != 0}">
							<input type="checkbox" disabled="disabled"/>
							<input type="hidden" name="company_checkbox" />
						</c:if>
						<input type="hidden" name="company_id" value="${child.id}"/>
					</td>
					<td style="text-indent: ${child.pathCode.length()*3}px;padding:0;">
							<c:if test="${child.childrenNum == 0}">
								<img src="/static/css/images/defaultLeaf.gif" />
							</c:if>
							<c:if test="${child.childrenNum > 0}">
								<img id="open_${child.id}" rel="close_${child.id}" onclick="company.closeFolder(event)" src="/static/css/images/folderOpen.gif" <c:if test="${childDisplay==0}">style="display:none;"</c:if>/>
								<img id="close_${child.id}" rel="open_${child.id}" onclick="company.openFolder(event)" src="/static/css/images/folderClose.gif" <c:if test="${childDisplay!=0}">style="display:none;"</c:if>/>
							</c:if>
							${child.name}
					</td>
					<td>${child.simpleName}</td>
					<td>${result.tel}</td>
					<td>${typeMap[fn:trim(child.type)].value}</td>
					<td>
						<a href="javascript:company.detail('${child.id}');">查看</a>
						<a href="javascript:company.update('${child.id}');">修改</a>
						<c:if test="${child.childrenNum == 0}">
							<a href="javascript:company.deleteSingle('${child.id}');">删除</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>
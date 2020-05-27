<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${boardList}" var="list">
	<tr>
		<td align="center">
			${list.comcodeVo.codeName}
		</td>
		<td align="center">
			${list.boardNum}
		</td>
		<td>
			<a href = "/board/${list.boardType}/${list.boardNum}/boardView.do?pageNo=${pageNo}">${list.boardTitle}</a>
		</td>
	</tr>	
</c:forEach>
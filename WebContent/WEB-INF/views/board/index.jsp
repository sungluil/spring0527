<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
$j(document).ready(function() {
	
	var top_page = "/board/navbar.do";
	$j("#div_top_page").load(top_page);
	
	var board = "/board/boardList.do";
	$j("#board").load(board);
	
});
</script>
</head>
<body>
<div id="div_top_page" class="round_box"></div>
<div id="board" class="round_box"></div>
</body>
</html>
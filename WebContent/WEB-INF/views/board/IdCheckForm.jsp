<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div id="wrap">
	<br>
	<b><font size="4" color="grey">아이디 중복체크</font></b>
	<hr size="1" width="460">
	<br>
	<div id="chk">
		<form id="checkForm">
			<input type="text" name="idinput" id="userId">
			<input type="button" value="중복확인" onclick="idCheck()">
		</form>
		<div id="msg"></div>
		<br>
		<input id="cancleBtn" type="button" value="취소" onclick="window.close()">
		<input id="userBtn" type="button" value="사용" onclick="sendCheckValue()">
	</div>
</div>
</body>
</html>
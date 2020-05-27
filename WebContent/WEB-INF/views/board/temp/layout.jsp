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
		<div>
			<div>
				<div>
					<tiles:insertAttribute name="header" />
				</div>
			</div>
			<div>
				<div>
					<tiles:insertAttribute name="body" />
				</div>
			</div>
			<div>
				<div>
					<tiles:insertAttribute name="footer" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>
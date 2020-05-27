<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<style type="text/css">
body {
	background-color: #eeeff1;
	font: 18px 'Malgun Gothic', dotum;
	width: 100%;
}
.navbar {
	height: 70px;
}
#navbarSupportedContent ul li {
	width: 140px;
	text-align: center;
	display: table;
  	margin-left: auto;
  	margin-right: auto;
}
#div_board {
	
}
</style>
<title>navbar</title>
<script type="text/javascript">
$j(document).ready(function() {
	var div_board = "boardList.do";
	$j("#div_board").load(div_board);
});

</script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar navbar-dark bg-dark" >
  <a style="font-size: 25px;" class="navbar-brand" href="#"><img src="/docs/4.0/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="">
    Bootstrap
  </a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Link</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">DashBoard</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">회원가입</a>
          <a class="dropdown-item" href="#">로그인</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
    </ul>
  </div>
</nav>
<div id="div_board" class="round_box"></div>
</body>
</html>
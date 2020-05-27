<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
<style type="text/css">
body {
	font-size:14px;
    font-family: 맑은고딕, Malgun Gothic, dotum, gulim, sans-serif;
}
.formDiv {
	width: 260px;
}

.form-control {
	margin-left:10px;
	margin-top: 5px;
	height: 33px;
	background-color: #fafafafa;
}
.btn-primary{
	margin-left:10px;
	margin-top: 10px;
	width: 260px;
	height: 30px;
}
input::placeholder {
	color: #fafafafa;
	font-size: 0.8em;
	opacity: 1;
}
.title {
	font-style: italic;
}

/* 첫번째 컨테이너박스 */
.containerOne {
	width: 283px;
	height: 335px;
	border: 0.3px solid grey;
	background-color: white;
	position: absolute;
	top: 116px;
	left: 40%;
	padding: 5px,5px,5px,5px;
/* 	margin-top: -300px; */
/* 	margin-left: -130px; */
/* 	z-index: 100; */
}

/* 두번째 컨테이너박스 */
.containerTwo {
	text-align: center;
	width: 283px;
	height: 82px;
	border: 0.3px solid grey;
	background-color: white;
	position: absolute;
	top: 457px;
	left: 40%;
/* 	margin-top: -140px; */
/* 	margin-left: -130px; */
	padding: 5px,5px;
	font-size:16px;
}

/* 세번째 컨테이너박스 */
.containerThree {
	text-align: center;
	width: 283px;
	height: 40px;
	border: 0.3px solid grey;
	background-color: white;
	position: absolute;
	top: 545px;
	left: 40%;
/*  	margin-top: -27px; */
/* 	margin-left: -130px; */
	padding: 5px,5px;
}
.label {
	margin-left:70px;
	font-size: 0.7em;
	color: grey;
}
.loginButton {
	margin-top: 30px;
	font-size: 0.7em;
}
.text {
	margin-top: 10px;
	margin-left: 10px;
	margin-right: 10px;
	
}

/* 체크박스 이미지 변환 */
#chk_cookie {display: none;}
#chk_cookie + label {
display: inline-block;
cursor: pointer;
background: url('/resources/images/checkbox_empty.png') no-repeat 0;
line-height: 16px;
padding-left: 20px; 
margin-top:11px;
font-size: 12px;
}
#chk_cookie:checked + label {
background: url('/resources/images/checkbox_check.png') no-repeat 0;
line-height: 16px;
padding-left: 20px;
}
#idSaveCheck {display: none;}
#idSaveCheck + label {
display: inline-block;cursor: pointer;
background: url('/resources/images/box2.png') no-repeat 0;
line-height: 16px;
padding-left: 20px;
font-size: 12px;
}
#idSaveCheck:checked + label {background: url('/resources/images/box.png') no-repeat 0;line-height: 16px;padding-left: 20px;}

/* 경고메세지 */
#msg {
	color:red;
	font-size: 12px;
}
</style>
</head>
<body>
	<div class="containerOne">
		<div>
			<img style="margin-left: 40px;margin-top: 10px;" alt="로고" src="${pageContext.request.contextPath }/resources/images/photogram.png" width="200">
		</div>
		<form id="form" name="f" method="post" class="login" >
		<div class="formDiv">
			<input class="form-control" name="userId" id="id" type="text" placeholder="아이디">
			<span style="color: red">${message }</span>
		</div>	
		<div class="formDiv">
			<input class="form-control" name="userPw" id="pw" type="password" placeholder="비밀번호"">
		</div>
		</form>
		<div>
			<button type="button" id="submit" class="btn btn-primary btn-sm" >로그인</button>
		</div>
		<div>
			<p align="center" style="font-size: 0.7em; opacity:80%; color: gery; margin-top: 10px;">또는		
		</div>
		<div>
			<p align="center" style="font-size: 0.8em; color: gery;"><a href="${pageContext.request.contextPath }/board/memberMailpassword.do">비밀번호를 잊으셨나요?</a>
		</div>
		<div id="msg" align="center">
		</div>
	</div>
	<div class="containerTwo">
		<div class="loginButton">
		계정이 있으신가요? <a href="${pageContext.request.contextPath }/board/memberWrite.do"><b>가입하기</b></a>		
		</div>
	</div>
	<div class="containerThree">
		<div>
			<input type="checkbox" id="chk_cookie" name="isuserCookie" value="Y"><label for="chk_cookie"><span>로그인 상태 유지</span></label>
			<input type="checkbox" id="idSaveCheck" name="isuserSave" value="Y"><label for="idSaveCheck"><span>아이디 저장</span></label>
<!-- 			<label><input type="checkbox" id="chk_cookie" name="isuserCookie" value="Y" width="5px;" height="10px;">로그인 유지</label> -->
<!-- 			<label><input type="checkbox" id="idSaveCheck" name="isuserSave" value="Y">아이디 저장</label> -->
<%-- 			<a href="${pageContext.request.contextPath }/board/boardList.do">List</a> --%>
		</div>
<!-- 		<input type="checkbox" id="test"><label for="test"><span>Remember</span></label> -->
	</div>
</body>
<script type="text/javascript">
$j(document).ready(function(){
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie("userInputId");
    $j("input[name='userId']").val(userInputId); 
     
    if($j("input[name='userId']").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
        $j("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
     
    $j("#idSaveCheck").change(function(){ // 체크박스에 변화가 있다면,
        if($j("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
            var userInputId = $j("input[name='userId']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("userInputId");
        }
    });
     
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $j("input[name='userId']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($j("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userInputId = $j("input[name='userId']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }
    });
});


function setCookie(cookieName, value, exdays){
	   var exdate = new Date();
	   exdate.setDate(exdate.getDate() + exdays);
	   var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	   document.cookie = cookieName + "=" + cookieValue;
	}

function deleteCookie(cookieName){
   var expireDate = new Date();
   expireDate.setDate(expireDate.getDate() - 1);
   document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
   cookieName = cookieName + '=';
   var cookieData = document.cookie;
   var start = cookieData.indexOf(cookieName);
   var cookieValue = '';
   if(start != -1){
      start += cookieName.length;
      var end = cookieData.indexOf(';', start);
      if(end == -1)end = cookieData.length;
      cookieValue = cookieData.substring(start, end);
   }
   return unescape(cookieValue);
}


//로그인버튼 클릭시 실행되는 ajax
$j("#submit").on("click",function(){
	var userId = $j("#id").val();
	var userPw = $j("#pw").val();
	var isuserCookie = $j("#chk_cookie").prop("checked") == true ? "Y" : "N";
	var isuserSave = $j("#idSaveCheck").prop("checked") == true ? "Y" : "N";

	console.log("isuserCookie = "+isuserCookie);
	console.log("isuserSave = "+isuserSave);
	
	if($j("#id").val() == "") {
 		alert("아이디를 입력하세요");
 		f.userId.focus();
	} else if($j("#pw").val() == "") {
 		alert("비밀번호를 입력하세요");
 		f.userId.focus();
	} else {
		$j.ajax({
			url:"/board/idchecked.do",
			type:"POST",
			data:{
				  "userId":userId
				, "userPw":userPw
				, "isuserCookie":isuserCookie
				, "isuserSave":isuserSave
			},
			success:function(result) {
				if(result == 0) {
					$j("#msg").text("아이디가 존재하지않거나 비밀번호가 틀렸습니다.");
					return false;
				} else {
					alert("환영합니다.");
					location.href = "/board/boardList.do";	
					return true;
				}
				
				alert(data);
			},
			error:function(req) {
				alert("에러 = "+req);
			}
			
		});
	}
});

</script>
</html>
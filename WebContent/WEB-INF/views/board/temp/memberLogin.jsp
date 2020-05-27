<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>로그인</title>
<style type="text/css">
input::placeholder {
	color: gray;
	font-style: italic;
	opacity: 0.5;	
	text-align: center;
}
select {
	width:53px;
	height: 26px;
}
.t1 {
	width:150px;
	text-align: center;	
}
.t2 {
	width: 175px;
}
.error {
	color: red;
}
</style>
<%@include file="/WEB-INF/views/common/common.jsp"%>
</head>
<body>
<table  align="center">
	<tr>
		<td align="left">
			<a href="${pageContext.request.contextPath }/board/boardList.do">List</a>
		</td>
		</td>
	</tr>
	<tr>
		<td>
		<form id="form" name="f" method="post" class="login" >
			<table id="boardTable" border = "1">
			<thead>
				<tr>
					<td height="40" width="80" align="center" colspan="3">
						로그인
					</td>
				</tr>
			</thead>
			<tbody>	
				<tr>
					<td class="t1">
						Id
					</td>
					<td class="t2">
						<input name="userId" id="id" type="text" placeholder="아이디">
						<span id="msg" class="error"></span>
					</td>
				</tr>
				<tr>
					<td class="t1">
						pw
					</td>
					<td class="t2">
						<input name="userPw" id="pw" type="password" placeholder="비밀번호">
					</td>
				</tr>
				
			</tbody>
			</table>
			<div>
			<label><input type="checkbox" id="chk_cookie" name="isuserCookie" value="Y">로그인 유지</label>
			<label><input type="checkbox" id="idSaveCheck" name="isuserSave" value="Y">아이디 저장</label>
			<label>
			<button type="button" class="email"><a style="text-decoration:none; color: black;" href="${pageContext.request.contextPath }/board/memberMailpassword.do">find</a></button>
			</label>			
			<label>
			<button type="button" class="join"><a style="text-decoration:none; color: black;" href="${pageContext.request.contextPath }/board/memberWrite.do">join</a></button>
			</label>			
			<label>
			<button type="button" class="submit">login</button>
			</label>
			</div>
		</form>
		</td>
	</tr>
</table>
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


$j(".submit").on("click",function(){
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
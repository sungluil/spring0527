<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.2.0/handlebars.min.js"></script>
<style type="text/css">
#btnDiv { margin: 10px; }

#insertDiv, #updateDiv {
	display: none;
	width: 240px;
	height: 80px;
	border: 2px solid black;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -40px;
	margin-left: -120px;
	padding: 5px;
	z-index: 100;
}
</style>
</head>
<body>
	<h1>RestBoard</h1>
	<div id="btnDiv">
		<button type="button" id="writeBtn">글쓰기</button>
	</div>
	
	<%-- 게시글 목록을 출력하는 영역 --%>	
	<div id="restBoardListDiv"></div>
	
	<%-- 페이지 번호를 출력하는 영역 --%>	
	<div id="pageNumDiv"></div>
	
	<%-- 게시글 정보를 입력하는 영역 --%>
	<div id="insertDiv">
	<table>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="writer" id="insertWriter" class="input"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><input type="text" name="content" id="insertContent" class="input"></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="insertBtn">저장</button>
				<button type="button" id="cancelInsertBtn">취소</button>
			</td>
		</tr>	
	</table>
	</div>
	
	<%-- 게시글 정보를 변경하는 영역 --%>
	<div id="updateDiv">
	<input type="hidden" name="num" id="updateNum">
	<table>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="writer" id="updateWriter" class="update"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><input type="text" name="content" id="updateContent" class="update"></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="updateBtn">변경</button>
				<button type="button" id="cancelUpdateBtn">취소</button>
			</td>
		</tr>	
	</table>
	</div>
	
	<script id="template" type="text/x-handlebars-template">
	<table border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th width="50">번호</th>
			<th width="100">작성자</th>
			<th width="350">내용</th>
			<th width="200">작성일자</th>
			<th width="50">변경</th>
			<th width="50">삭제</th>
		</tr>
		{{#each .}}
		<tr>
			<td align="center">{{num}}</td>
			<td align="center">{{writer}}</td>
			<td>{{content}}</td>
			<td align="center">{{regdate}}</td>
			<td align="center"><button type="button" onclick="updateForm('{{num}}');">변경</button></td>
			<td align="center"><button type="button" onclick="deleteAction('{{num}}');">삭제</button></td>
		</tr>
		{{/each}}
	</table>
	</script>
	
	<script type="text/javascript">
	var page=1;
	boardDisplay(page);
	
	//게시글 목록을 요청하여 출력하는 함수
	function boardDisplay(pageNum) {
		page=pageNum;
		$.ajax({
			type: "POST",
			url: "board_list",
			data: {"pageNum":pageNum},
			dataType: "json",
			success: function(json) {
				if(json.restBoardList.length==0) {
					$("#restBoardListDiv").html("검색된 게시글이 존재하지 않습니다.")
					return;
				}
				
				var source=$("#template").html();
				var template=Handlebars.compile(source);
				$("#restBoardListDiv").html(template(json.restBoardList));
				
				pageDisplay(json.pager);
			},
			error: function(req) {
				alert("응답 에러 코드 = "+req.status);
			}
		});
	}
	
	//페이지 번호를 출력하기 위한 함수
	function pageDisplay(pager) {
		var html="";
		if(pager.startPage>pager.blockSize) {
			html+="<a href='javascript:boardDisplay(1);'>[처음]</a>";
			html+="<a href='javascript:boardDisplay("+pager.prevPage+");'>[이전]</a>";
		} else {
			html+="[처음][이전]";
		}
		
		for(i=pager.startPage;i<=pager.endPage;i++) {
			if(pager.pageNum!=i) {
				html+="<a href='javascript:boardDisplay("+i+");'>["+i+"]</a>";
			} else {
				html+="["+i+"]";
			}
		}
		
		if(pager.endPage!=pager.totalPage) {
			html+="<a href='javascript:boardDisplay("+pager.nextPage+");'>[다음]</a>";
			html+="<a href='javascript:boardDisplay("+pager.totalPage+");'>[마지막]</a>";
		} else {
			html+="[다음][마지막]";
		}
		
		$("#pageNumDiv").html(html);
	}
	
	//입력창을 화면에 출력하는 이벤트 핸들러 등록
	$("#writeBtn").click(function() {
		//변경창이 출력되지 않도록 설정
		$(".update").val("");
		$("#updateDiv").hide();
		
		//입력창이 출력되도록 설정
		$("#insertDiv").show(300);
	});
	
	//입력값으로 게시글을 저장하는 페이지 요청하는 이벤트 핸들러 등록
	$("#insertBtn").click(function() {
		var writer=$("#insertWriter").val();
		var content=$("#insertContent").val();
		
		if(writer=="") {
			alert("작성자를 입력해 주세요.");
			return;
		}
		
		if(content=="") {
			alert("내용을 입력해 주세요.");
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "board_add",
			//headers : 요청 페이지의 변경될 헤더 정보를 설정하는 속성
			// => Content-Type 속성으로 입력값에 대한 텍스트 형식을 변경
			headers: {"Content-Type":"application/json"},
			//JSON.stringify(Object object) : JavaScript 객체를 문자열로 변환하는 메소드
			// => JavaScript 객체의 프로퍼티값을 문자열로 전달하여 JavaBean 인스턴스의 필드값으로 저장하기 위해 변환
			data: JSON.stringify({"writer":writer,"content":content}),
			dateType: "text",
			success: function(text) {
				if(text=="success") {
					$(".input").val("");
					$("#insertDiv").hide(300);
					boardDisplay(1);
				}
			},
			error: function(req) {
				alert("응답 에러 코드 = "+req.status);
			}
		});
	});
	
	$("#cancelInsertBtn").click(function() {
		$(".input").val("");
		$("#insertDiv").hide(300);
	});
	
	//변경창을 화면에 출력하는 이벤트 핸들러 함수
	// => 변경하고자 하는 게시글 정보를 AJAX로 요청하여 제공받아 입력태그에 출력
	function updateForm(num) {
		//입력창이 출력되지 않도록 설정
		$(".input").val("");
		$("#insertDiv").hide();
		
		//변경창이 출력되도록 설정
		$("#updateDiv").show(300);
		
		$.ajax({
			type: "GET",
			//url: "board_view?num="+num,
			url: "board_view/"+num,
			dateType: "json",
			success: function(json) {
				$("#updateNum").val(json.num);
				$("#updateWriter").val(json.writer);
				$("#updateContent").val(json.content);
			},
			error: function(req) {
				alert("응답 에러 코드 = "+req.status);
			}
		});
	}
	
	$("#updateBtn").click(function() {
		var num=$("#updateNum").val();
		var writer=$("#updateWriter").val();
		var content=$("#updateContent").val();
		
		if(writer=="") {
			alert("작성자를 입력해 주세요.");
			return;
		}
		
		if(content=="") {
			alert("내용을 입력해 주세요.");
			return;
		}
		
		$.ajax({
			type: "PUT",
			url: "board_modify",
			//GET 또는 POST를 제외한 요청방식을 인식하지 못하는 브라우저를 위해
			//요청에 대한 헤더정보 중 X-HTTP-Method-override 속성을 이용하여 
			//PUT, PATCH, DELETE 등이 인식되도록 변경 설정
			headers: {"Content-Type":"application/json","X-HTTP-Method-override":"PUT"},
			data: JSON.stringify({"num":num,"writer":writer,"content":content}),
			dataType: "text",
			success: function(result) {
				if(result=="success") {
					$(".updatge").val("");
					$("#updateDiv").hide(300);
					boardDisplay(page);
				}				
			},
			error: function(req) {
				alert("응답 에러 코드 = "+req.status);
			}
		});
		
	});
	
	$("#cancelUpdateBtn").click(function() {
		$(".updatge").val("");
		$("#updateDiv").hide(300);
	});
	
	function deleteAction(num) {
		if(confirm("정말로 삭제 하시겠습니까?")) {
			$.ajax({
				type: "DELETE",
				url: "board_erase/"+num,
				headers: {"X-HTTP-Method-override":"DELETE"},
				dataType: "text",
				success: function(result) {
					if(result=="success") {
						boardDisplay(1);
					}					
				},
				error: function(req) {
					alert("응답 에러 코드 = "+req.status);
				}
			});
		}
	}
	</script>
</body>
</html>






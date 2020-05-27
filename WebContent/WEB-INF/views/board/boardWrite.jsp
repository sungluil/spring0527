<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>boardWrite</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/smarteditorSample/WebContent/SE2/js/HuskyEZCreator.js" charset="utf-8" ></script>
<style type="text/css">
body {
	font-size:14px;
    font-family: 맑은고딕, Malgun Gothic, dotum, gulim, sans-serif;
    color: #676663;
}
td, th {
	padding: 5px;
}
.filebox label {
  display: inline-block;
  padding: .5em .75em;
  color: #fff;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #5cb85c;
  cursor: pointer;
  border: 1px solid #4cae4c;
  border-radius: .25em;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
}

.filebox label:hover {
  background-color: #6ed36e;
}

.filebox label:active {
  background-color: #367c36;
}

.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
</style>
</head>
<script type="text/javascript">
/**
  스마트에디터 적용 
 **/
var oEditors = [];
var page = 1;
$j(function(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "boardComment",
		//SmartEditor2Skin.html 파일이 존재하는 경로
		sSkinURI: "<%=request.getContextPath() %>/smarteditorSample/WebContent/SE2/SmartEditor2Skin.html",  
		htParams : {
			// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseToolbar : true,             
			// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,     
			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,         
			fOnBeforeUnload : function(){
                   
			}
		}, 
// 		fOnAppLoad : function(){
// 			//기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
// 			oEditors.getById["boardComment"].exec("PASTE_HTML", [""]);
// 		},
		fCreator: "createSEditor2"
	});   
	
    /**
     글쓰기버튼 Ajax
    **/
    $j("#submit").click(function(){
        oEditors.getById["boardComment"].exec("UPDATE_CONTENTS_FIELD", []);
		var boardTitle = $j('.boardTitle').val();        
        var boardComment = $j('.boardComment').val();
        
//         var length = document.forms[0].length - 1;
//         for ( var i = 0; i < length; i++) { // 길이만큼 루프를 돌면서
//         	if (document.forms[0][i].value == null || document.forms[0][i].value == "" || document.forms[0][i].value == '&nbsp;' || document.forms[0][i].value == '<p>&nbsp;</p>') {
//                 // 경고창을 띄우고
//                 alert(document.forms[0][i].name + "을(를) 입력하세요.");
//                 // null인 태그에 포커스를 줌
//                 document.forms[0][i].focus();
//                 return false;
//             }//end if
//         }//end for
        
//         var arrTitle =[];
// 		$j("input[name='boardTitle']").each(function() {
// 			arrTitle.push($j(this).val());
// 		});
// 		alert(arrTitle[0])
// 		if(arrTitle[0][i] == "" || arrTitle[0] == null) {
// 			alert("제목을 입력 해주세요.");
// 			$j("input[name='boardTitle']").focus();
// 			return false;
// 		}

// 		$j(".boardComment").each(function(){
// 			arrComment.push($j(this).val());
// 	        if( arrComment == ""  || arrComment == null || arrComment == '&nbsp;' || arrComment == '<p>&nbsp;</p>') {
// 				alert("내용을 입력하세요.")
// 				$j(".boardComment").focus();
// 			}
// 			return false;
// 		});
		//var boardTitle = arrTitle.join(",");
		//var boardComment = arrComment.join(",");
		
		
        //유효성검사
        if( boardTitle == "" || boardTitle == null) {
			alert("제목을 입력하세요.")
			 $j('.boardTitle').focus();
			return false;
		} 
        if( boardComment == ""  || boardComment == null || boardComment == '&nbsp;' || boardComment == '<p>&nbsp;</p>') {
			alert("내용을 입력하세요.")
			$j(".boardComment").focus();
			return false;
		} 
    	
//     	var fileCheck = document.getElementsByName("file").value;
//         if(!fileCheck){
//             alert("파일을 첨부해 주세요");
//             return false;
//         }
		//폼의 모든input값을 $frm넣고 param에 serialize()를 이용해서 담기!
		//var $frm = $j('.boardWrite :input');
		//var param = $frm.serialize();
		//var formData = new FormData($j('#fileUploadForm')[0]);
		var formData = new FormData($j("#boardWriterForm")[0]);
		
		
		//작성버튼으로 통신 ajax
		$j.ajax({
		    url : "/board/boardWriteAction.do",
		    dataType: "json",
		    type: "POST",
		    data : formData,
		    enctype: 'multipart/form-data',
		    contentType: false,
		    processData: false,
		    success: function(data, textStatus, jqXHR)
		    {
				//alert("전송");
				
				//alert("메세지:"+data.success);
				
				//location.href = "/board/boardList.do?pageNo="+page;
				
				//글작성후 뒤로가기 막기
				location.replace("/board/boardList.do?pageNo="+page);
				
		    },
// 		    error: function (jqXHR, textStatus, errorThrown)
		    error: function (request,status,error)
		    {
		    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    }
		});
    }); 
});
// //글쓰기
// var page=1;
// $j(document).ready(function() {
// 	$j("#submit").on("click",function(){
		
// 		//입력값 체크
// 		var boardTitle = $j("#boardTitle").val();
// 		//var boardComment = $j("#boardComment").val();
// 		var boardComment = oEditors.getById["#boardComment"].exec("PASTE_HTML", [""]);
		
// 		if(boardTitle.length < 1 ) {
// 			alert("제목을 입력하세요.")
// 			 $j('.boardTitle').focus();
// 			return false;
// 		} 
// 		if(boardComment.length < 1 ) {
// 			alert("내용을 입력하세요.")
// 			$j(".boardComment").focus();
// 			return false;
// 		} 
		
// 		//폼의 모든input값을 $frm넣고 param에 serialize()를 이용해서 담기
// 		var $frm = $j('.boardWrite :input');
// 		var param = $frm.serialize();
		
// 		//작성버튼으로 통신 ajax
// 		$j.ajax({
// 		    url : "/board/boardWriteAction.do",
// 		    dataType: "json",
// 		    type: "POST",
// 		    data : param,
// 		    success: function(data, textStatus, jqXHR)
// 		    {
// 				//alert("전송");
				
// 				//alert("메세지:"+data.success);
				
// 				location.href = "/board/boardList.do?pageNo="+page;
// 		    },
// // 		    error: function (jqXHR, textStatus, errorThrown)
// 		    error: function (request,status,error)
// 		    {
// 		    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
// 		    }
// 		});
// 	});	
// });
//자바스크립트에서 JSTL 쓰려면...
var listName = new Array(); 
<c:forEach var="list" items="${codeList}">
	<c:if test="${list.codeType eq 'menu' }">
	listName.push("${list.codeName}");
	</c:if> 
</c:forEach>

var username = "${userinfo.userName }";
	

//폼 추가하기
$j(document).ready(function() {
	
	$j('#addKeywork').click(function() {
		/**
		*  추가될 테이블
		*/
		var rowItem = "<table class='addTab' align='center'>"			
			rowItem += "<tr>"
			rowItem += "<td align='right'>"
			rowItem += "<input id='delKeywork' class='btn btn-primary btn-sm' type='button' value='삭제'>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "<tr>"			
			rowItem += "<td>"						
			rowItem += "<table border='1' bordercolor='#CFDBE2'>"			
			rowItem += "<tr>"
			rowItem += "<td width='120' align='center' style='background-color: #F2F5F6; color: #666666'>Type</td>"
			rowItem += "<td width='650' height='30'>"
			rowItem += "<select name='boardType' style='height: 100%;'>"
			rowItem += "<option value='a01'>일반</option>"
			rowItem += "<option value='a02'>Q&A</option>"
			rowItem += "<option value='a03'>익명</option>"
			rowItem += "<option value='a04'>자유</option>"
			rowItem += "</select>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "<tr>"
			rowItem += "<td width='120' align='center' style='background-color: #F2F5F6; color: #666666'>Title</td>"
			rowItem += "<td width='400' height='30'><input class='boardTitle' name='boardTitle' type='text' maxlength='20' style='width: 100%; height: 100%;'></td>"
			rowItem += "</tr>"
			rowItem += "<tr>"
			rowItem += "<td height='300' align='center' style='background-color: #F2F5F6; color: #666666'>Comment</td>"
			rowItem += "<td align='top'><textarea class='boardComment' name='boardComment' rows='10' cols='30' style='width:650px; height:350px;'></textarea></td>"
			rowItem += "</tr>"
			rowItem += "<tr>"
			rowItem += "<td align='center' style='background-color: #F2F5F6; color: #666666'>Writer</td>"
			rowItem += "<td height='30'>"
			rowItem += "<input name='creator' type='hidden' size='0' readonly='readonly' value='${userinfo.userName }'>${userinfo.userName }"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "</table>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "<tr>"
			rowItem += "<td>"
			rowItem += "<table width='100%' border='1' bordercolor='#CFDBE2'>"
			rowItem += "<tbody>"
			rowItem += "<tr>"
			rowItem += "<td width='120' style='background-color: #F2F5F6; color: #666666'>"
			rowItem += "<div style='font-size: 12px' align='center'>파일첨부</div>"
			rowItem += "<div style='margin: 5px 0px 0px 10px; font-size: 12px'>[파일최대]60MB</div>"
			rowItem += "</td>"
			rowItem += "<td>"
			rowItem += "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
			rowItem += "<tbody>"
			rowItem += "<tr>"
			rowItem += "<td>"
			rowItem += "<iframe frameborder='0' framespacing='0' marginheight='0' marginwidth='0' scrolling='no' space='0' src='${pageContext.request.contextPath }/board/FileFrame.do' width='100%' height='50px'></iframe>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "<tr>"
			rowItem += "<td>"
			rowItem += "<div class='form-group'>"
			rowItem += "<a href='#this' onclick='addFile()'>파일추가</a>"
			rowItem += "<div class='file-group'>"
			rowItem += "<input style='display: inline;' type='file' name='file' class='fileRoot'>"
			rowItem += "</div>"
			rowItem += "</div>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "</tbody>"
			rowItem += "</table>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "</tbody>"
			rowItem += "</table>"
			rowItem += "</td>"
			rowItem += "</tr>"
			rowItem += "</table>"
			rowItem += "<br>"
			//최대 5개까지 - 추가4개생성후 더이상생성안되게 막음
			if($j(".addTab").length <= 3) {
// 				console.log($j('.addTab').length)
				$j('#addtable').append(rowItem);
				//$j(this).closest('#addtable').append(rowItem);
			}
	});
	
	//행 삭제	
	$j('#addtable').on("click", "input:button[id=delKeywork]", function() {
	    $j(this).closest("table").remove()
	});
});

function addFile() {
    var str = "<div class='file-group'><input style='display: inline;' type='file' name='file' class='fileRoot' multiple><a href='#this' name='file-delete'>삭제</a></div>";
    $j("#file-list").append(str);
    $j("a[name='file-delete']").on("click", function(e) {
        e.preventDefault();
        $j(this)[0].closest("div").remove();
    });
}
</script>
<body>
<!-- 파일전달을 위해서는 post로만 가능 -->
<form id="boardWriterForm" class="boardWrite" method="post" enctype="multipart/form-data" id="fileUploadForm">
	<table>
		<tbody>
		</tbody>
	</table>
	<!-- 버튼 테이블 -->
	<table align="center" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td align="right" width="780px">
				<input id="addKeywork" type="button" class="btn btn-primary btn-sm" value="추가">
				<input id="submit"     type="button" class="btn btn-primary btn-sm" value="작성">
				</td>
			</tr>
		</tbody>
	</table>
	<table align="center">
		<tr>
			<td>
				<table border="1" bordercolor="#CFDBE2"> 
					<tr>
						<td width="120" align="center" style="background-color: #F2F5F6; color: #666666">
						Type
						</td>
						<td id="type" width="650" height="30">
				        <select name="boardType" style="height: 100%;">
						<c:forEach var="list" items="${codeList}">
							<c:if test="${list.codeType eq 'menu' }"> 
								<option value="${list.codeId }">${list.codeName}</option>
							</c:if>
						</c:forEach>
				        </select>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" style="background-color: #F2F5F6; color: #666666">
						Title
						</td>
						<td height="30">
						<input id="boardTitle" class="boardTitle" name="boardTitle" type="text" maxlength="20" style="width: 100%; height: 100%;">
						</td>
					</tr>
					<tr>
						<td height="300" align="center" style="background-color: #F2F5F6; color: #666666">
						Comment
						</td>
						<td align="top">
						<textarea id="boardComment" class="boardComment" name="boardComment" rows="10" cols="30" style="width:635px; height:350px; border: none;"></textarea>
						</td>
					</tr>
					<tr>
						<td align="center" style="background-color: #F2F5F6; color: #666666">
						Writer
						</td>
						<td height="30">
						<input name="creator" type="hidden" size="0" readonly="readonly" value="${userinfo.userName }">${userinfo.userName }
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="1" bordercolor="#CFDBE2">
					<tbody>
						<tr>
							<td width="120" style="background-color: #F2F5F6; color: #666666">
								<div style="font-size: 12px" align="center">파일첨부</div>
								<div style="margin: 5px 0px 0px 10px; font-size: 12px">[파일최대]60MB</div>
							</td>
							<td>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td>
												<iframe frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="no"
												space="0" src="${pageContext.request.contextPath }/board/FileFrame.do" width="100%" height="50px"></iframe>
											</td>
										</tr>
										<tr>
											<td>
												<!-- 파일 업로드 -->
												<div class="form-group" id="file-list">
												    <a href="#this" onclick="addFile()">파일추가</a>
												    <div class="file-group">
												        <input style="display: inline;" id="file" type="file" name="file" class="fileRoot" multiple>
												    </div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" style="font-size: 15px;">
				<a href="${pageContext.request.contextPath }/board/boardList.do">List</a>
			</td>
		</tr>
	</table>
	<div id="addtable"></div>
</form>	
</body>
</html>
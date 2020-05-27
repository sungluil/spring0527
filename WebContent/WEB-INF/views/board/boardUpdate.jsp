<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="<%=request.getContextPath() %>/smarteditorSample/WebContent/SE2/js/HuskyEZCreator.js" charset="utf-8" ></script>
<title>boardUpdate</title>
<style type="text/css">
td, th {
	padding: 5px;
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
//		fOnAppLoad : function(){
//			//기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
//			oEditors.getById["boardComment"].exec("PASTE_HTML", [""]);
//		},
		fCreator: "createSEditor2"
	}); 
	//업데이트
	$j(document).ready(function(){
		
		$j("#submit").on("click",function(){
			var $frm = $j('.boardUpdate :input');
			var param = $frm.serialize();
			//alert(param);
			
			$j.ajax({
			    url : "/board/boardUpdateAction.do",
			    dataType: "json",
			    type: "POST",
			    data : param,
			    success: function(data, textStatus, jqXHR)
			    {
					//alert("작성완료");
					
					//alert("메세지:"+data.success);
					
					location.href = "/board/boardList.do";
			    },
			    error: function (jqXHR, textStatus, errorThrown)
			    {
			    	alert("실패");
			    }
			});
		});	
	});
});




</script>
<body>
<form class="boardUpdate">
	
	<table align="center">
		<tr>
			<td style="padding-top: 10px;" align="right">
			<input id="submit" type="button" class="btn btn-primary btn-sm" value="수정">
			</td>
		</tr>
		<tr>
			<td>
				<table border ="1" bordercolor="#CFDBE2">
					<tr>
						<td width="120" align="center" style="background-color: #F2F5F6; color: #666666">
						Type
						</td>
						<td height="30" width="650">
						<input name="boardType" type="hidden" id="boardType" value="${board.boardType }">${board.comcodeVo.codeName }
						</td>
					</tr>
					<tr>
						<td width="120" align="center" style="background-color: #F2F5F6; color: #666666">
						Title
						</td>
						<td height="30"> 
						<input name="boardTitle" type="text" size="53" id="boardTitle" style="width: 100%; height: 100%" value="${board.boardTitle}">
						</td>
					</tr>
					<tr>
						<td height="300" align="center" style="background-color: #F2F5F6; color: #666666">
						Comment
						</td>
						<td valign="top">
						<textarea id="boardComment" class="boardComment" name="boardComment" rows="10" cols="30" style="width:635px; height:350px; border: none;">${board.boardComment}</textarea>
						</td>
					</tr>
					<tr>
						<td align="center" style="background-color: #F2F5F6; color: #666666">
						Writer
						</td>
						<td>
						<input name="creator" type="hidden" size="0" readonly="readonly" value="${board.creator }">${board.creator }
						<input name="boardNum" type="hidden" id="boardNum" value="${board.boardNum }">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href = "${pageContext.request.contextPath }/board/boardList.do">취소</a>
			</td>
		</tr>
	</table>
</form>	
</body>
</html>
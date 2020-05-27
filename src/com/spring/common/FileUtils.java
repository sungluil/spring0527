package com.spring.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//빈과 컴포넌트 차이
//@bean : 외부라이브러리를 등록하고싶은 경우
//@Component : 개발자가 직접 등록하고싶은 경우

@Component("fileUtils")
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	//bean의 id가 uploadPath인 태그참조 - 위치 root-context.xml
	@Resource(name = "uploadPath")
	String uploadPath;
	
	public List<Map<String, Object>> parseFileInfo(Map<String, Object> map, MultipartFile[] file) throws Exception {
		
		String fileWriter = (String) map.get("creator");//사용자아이디
		String boardNum = String.valueOf(map.get("boardNum"));//게시글번호
		
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		
		File target = new File(uploadPath);//타겟 저장경로 생성
		if(!target.exists()) {//파일의 존재유무 판단하기위해 exists() 사용
			//File.mkdir() : 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 생성 불가
			//mkdirs() : 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성
			target.mkdirs();
		}
		//파일의 갯수만큼 불러옴
		for(int i=0; i<file.length; i++) {
			String orgFileName = file[i].getOriginalFilename();//원본이름
			String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));//이름의 마지막에서.이걸기준으로 잘라서 가져옴
			String saveFileName = UUID.randomUUID().toString().replace("-", "") + orgFileExtension;//랜덤으로 이름변경 후 서버에저장
			Long saveFileSize = file[i].getSize();//파일크기
			String contentType = file[i].getContentType();//확장자
			
			logger.debug(">> 파일 업로드 시스템... start..");
			logger.debug(" 파일원본이름 [" + orgFileName + "]");
			logger.debug(" 파일저장이름 [" + saveFileName + "]");
			logger.debug(" 파일크기 [" + saveFileSize + "]");
			logger.debug(" content type [" + file[i].getContentType() + "]");
			logger.debug(">> 시스템 종료... End..");
			
			target = new File(uploadPath, saveFileName);//저장경로에 저장
			file[i].transferTo(target);//파일업로드
			
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			
			fileInfo.put("fileWriter",fileWriter);
			fileInfo.put("fileOrgName",orgFileName);
			fileInfo.put("fileSaveName",saveFileName);
			fileInfo.put("fileType",contentType);
			fileInfo.put("fileSize",saveFileSize);
			fileInfo.put("filePath",target);
			fileInfo.put("boardNum",boardNum);
			fileList.add(fileInfo);
		}
		return fileList;
	}
}

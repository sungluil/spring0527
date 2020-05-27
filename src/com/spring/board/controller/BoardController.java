package com.spring.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.board.vo.PostFileVo;
import com.spring.board.vo.UserInfoVo;
import com.spring.common.CommandMap;
import com.spring.common.CommonUtil;


@Controller
public class BoardController {
	
	@Autowired 
	boardService boardService;

	@Autowired
	JavaMailSender mailSender;
	
	//bean의 id가 uploadPath인 태그참조
	@Resource(name = "uploadPath")
	String uploadPath;
	
	final static int pageSize = 10; 
	final static int blockSize = 5; 
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private MultipartFile files;

	@RequestMapping("/board/navbar.do")
	public String navBar() {
		return "board/navbar";
	}
	@RequestMapping("/board/index.do")
	public String index() {
		return "board/index";
	}
	
	/**
	 * 게시판 리스트
	 * PageNo를 초기에 1로 설정
	 * Model로 뷰페이지에서 사용가능한 값 boardList를 설정
	 */
	@RequestMapping(value = "/board/boardList.do", method = RequestMethod.GET)
	public String boardList( Locale locale 
							, ComCodeVo comCodeVo
							, Model model
							, @RequestParam(defaultValue = "1") int pageNo
							, BoardVo boardVo) throws Exception {
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		//List<ComCodeVo> codeList = new ArrayList<ComCodeVo>();
		

		PageVo pageVo = new PageVo(pageNo, pageSize, boardService.selectBoardCnt(), blockSize);
		
		if(pageNo <= 0) {
			pageVo.setPageNo(pageNo);
			return "redirect:/board/boardList.do?pageNo=1";
		}
		
		model.addAttribute("boardList", boardService.SelectBoardList(pageVo));
		model.addAttribute("totalCnt", boardService.selectBoardCnt());
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("codeList", boardService.codeList(comCodeVo));
		model.addAttribute("comCodeVo", comCodeVo);
		model.addAttribute("pageNo", pageNo);

		return "board/boardList";
	}
	/**
	 * 게시판 글 상세보기
	 * GET방식으로 Type과 Num을 받아와 주소값에 넘겨줌
	 */
	@RequestMapping(value = "/board/{boardType}/{boardNum}/boardView.do", method = RequestMethod.GET)
	public String boardView( Locale locale
							, Model model
							, @PathVariable("boardType")String boardType
							, @PathVariable("boardNum")int boardNum
							, BoardVo boardVo
							) throws Exception {
		
		//조회수 증가 +1
		boardService.updateReadHits(boardType,boardNum);
		
		boardVo = boardService.selectBoard(boardType,boardNum);
		List<PostFileVo> postFileVo = new ArrayList<PostFileVo>();
		postFileVo = boardService.selectFile(boardType, boardNum);
		
		//model.addAttribute("boardType", boardType);
		//model.addAttribute("boardNum", boardNum);
		model.addAttribute("board", boardVo);
		model.addAttribute("file", postFileVo);
		
		
		return "board/boardView";
	}
	
	/**
	 * 게시글 작성
	 */
	@RequestMapping(value = "/board/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite(Locale locale,ComCodeVo comCodeVo,PageVo pageVo, Model model, HttpServletRequest request) throws Exception{
		
		List<ComCodeVo> codeList = new ArrayList<ComCodeVo>();
		
		codeList = boardService.codeList(comCodeVo);
		
		
		model.addAttribute("codeList", codeList);
		model.addAttribute("comCodeVo", comCodeVo);
		
		
		return "board/boardWrite";
	}
	
	/**
	//단일행 Insert
	@RequestMapping(value = "/board/boardWriteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteAction(Locale locale
			,MultipartFile file
			,BoardVo boardVo,Model model) throws Exception{
		

		Map<String, Object> result = new HashMap<String, Object>();
		CommonUtil commonUtil = new CommonUtil();
		
		String orgFileName = file.getOriginalFilename();//원본이름
		String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));//이름의 마지막에서.이걸기준으로 잘라서 가져옴
		String saveFileName = UUID.randomUUID().toString().replace("-", "") + orgFileExtension;//랜덤으로 이름변경 후 서버에저장
		Long saveFileSize = file.getSize();//파일크기
		File target = new File(uploadPath, saveFileName);//타겟 저장경로에 본래이름으로 
		
		boardVo.setFileRoot(saveFileName);
		
		FileCopyUtils.copy(file.getBytes(), target);
		
		//List<BoardVo> list = new ArrayList<BoardVo>();
		int resultCnt = boardService.boardInsert(boardVo);

		result.put("success", (resultCnt > 0)?"Y":"N");

		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);

		return callbackMsg;
		
	}
	 **/
	
	/**
	 * 멀티 게시글 -다중글입력
	 * 각각 입력된값을 전달받아 배열[]에 ','구분지어 담기
	 * 리스트 생성후 For문으로 갯수만큼 반복문을 돌린뒤 vo에 저장
	 * 서비스 호출후 SQL에서는 foreach을 써서 여러글작성
	 */
	@RequestMapping(value = "/board/boardWriteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardInsertAll(String boardTitle, String boardType, String boardComment, String creator
			,Model model
			,MultipartHttpServletRequest mtfRequest
			,@RequestParam(value = "file") MultipartFile[] file) throws Exception{

		Iterator<String> itr =  mtfRequest.getFileNames();
		MultipartFile files = mtfRequest.getFile(itr.next());
		
		//넘겨져오는 값
		logger.info(" boardTitle   : " + boardTitle );
		logger.info(" boardType    : " + boardType );
		logger.info(" boardComment : " + boardComment );
		logger.info(" creator      : " + creator );
		
		// 스트링 배열로 타입,제목,내용,작성자를 각각 가져오는데 "," 구분지어서 담기
		String[] type    = boardType.split(",");
		String[] title   = boardTitle.split(",");
		String[] comment = boardComment.split(",");
		String[] writer  = creator.split(",");
		
		//담을 List를 하나 생성후에
		List<BoardVo> list2 = new ArrayList<BoardVo>();
		//파일업로드
		List<PostFileVo> fileList = new ArrayList<PostFileVo>();
		int c = 0;
		int a = 0;
		//포문으로 한개의 갯수만큼 돌려준뒤		
		int resultCnt=0;

		for (MultipartFile mf : file) {
			if (!mf.isEmpty()) {
				c += 1;
			}
		}
		
		for(int h=0;h<5;h++) {
			System.out.println(h+"번째 바깥 for 문 시작");
			for(int k=0;k<5;k++) {
				System.out.println(k+"번째 안쪽 for 문");
			}
			System.out.println(h+"번째 바깥 for 문 종료");
		}
		
		
		logger.info("넘어오는 글 갯수는 = "+type.length);
		for(int i=0;i<type.length;i++) {//2개 넘어오고
			BoardVo vo = new BoardVo();
			vo.setBoardType(type[i]);
			vo.setBoardTitle(title[i]);
			vo.setBoardComment(comment[i]);
			vo.setCreator(writer[i]);
			list2.add(vo);
			
			resultCnt = boardService.boardInsertAll(list2);	
		}
		logger.info("넘어오는 파일 갯수는 = "+file.length);
		if(c > 0) {
			for(int j=0;j<file.length;j++) {
				
				PostFileVo fileVo = new PostFileVo();
				String orgFileName = file[j].getOriginalFilename();//원본이름
				//String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));//이름의 마지막에서.이걸기준으로 잘라서 가져옴
				String saveFileName = UUID.randomUUID().toString().replace("-", "");//+orgFileExtension;//랜덤으로 이름변경 후 서버에저장
				Long saveFileSize = file[j].getSize();//파일크기
				File target = new File(uploadPath, saveFileName);//저장경로에 저장
				String fileType = file[j].getContentType();
				file[j].transferTo(target);//파일업로드
				String fileWriter = mtfRequest.getParameter("creator");
				String fileGroup = mtfRequest.getParameter("boardType");
				
				logger.info(">> 파일 업로드 시스템... start..<<");
				logger.info("   작  성  자   [" + fileWriter  + "]" );
				logger.info("   원본파일이름 [" + orgFileName + "]" );
				logger.info("   저 장 이 름  [" + saveFileName + "]" );
				logger.info("   content type [" + fileType + "]" );
				logger.info("   파일사이즈   [" + saveFileSize + "]" );
				logger.info("   파 일 경 로  [" + target.getPath() + "]" );
				logger.info("   T  y  p  e   [" + fileGroup + "]" );
				logger.info(">> 시스템 종료... End..<<");
				//파일인서트
				fileVo.setFileWriter(fileWriter);
				fileVo.setFileOrgName(orgFileName);
				fileVo.setFileSaveName(saveFileName);
				fileVo.setFileType(fileType);
				fileVo.setFileSize(saveFileSize);
				fileVo.setFilePath(target.getPath());
				fileVo.setFileGroup(fileGroup);
				
				fileList.add(fileVo);	
				
				
			}
			boardService.postFileInsertAll(fileList);
		}
		
		//해쉬맵을 하나생성
		Map<String, Object> result = new HashMap<String, Object>();
		//Json을 읽고쓰기위한 CommonUtil 생성
		CommonUtil commonUtil = new CommonUtil();
		
		// 서비스 호출
		//int resultCnt = boardService.boardInsertAll(list2);
		
		
		//글쓴갯수가 0보다크면 Y 아니면 N을 돌려받아 "success"로 담기
		result.put("success", (resultCnt > 0)?"Y":"N");
		
		//json타입으로 반환
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		/**========================================================================================**/

		//결과적으로 흐름이 배열=>for문=>리스트담기=>해쉬맵에결과값담기=>스트링으로 json보내기
		return callbackMsg;
	}
	
	/**
	 * 작성글 업데이트페이지
	 */
	@RequestMapping("/board/{boardType}/{boardNum}/boardUpdate.do")
	public String boardUpdate(Model model
			,@PathVariable("boardType")String boardType
			,@PathVariable("boardNum")int boardNum, BoardVo boardVo) throws Exception{
		

		boardVo = boardService.selectBoard(boardType,boardNum);
		
		model.addAttribute("board", boardVo);
		
		return "board/boardUpdate";
	}

	/**
	 * 작성글 업데이트 실행 컨트롤러
	 */
	@RequestMapping(value = "/board/boardUpdateAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardUpdate(BoardVo boardVo) throws Exception {
		
		HashMap<String, String> result = new HashMap<String, String>();
		CommonUtil commonUtil = new CommonUtil();
		
		int resultCnt = boardService.boardUpdate(boardVo);
		
		result.put("success", (resultCnt > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		return callbackMsg;
	}
	
	/**
	 * 게시글 삭제
	 */
	@RequestMapping(value = "/booard/boardDeleteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardDelete(BoardVo boardVo) throws Exception {
		

		int resultCnt = boardService.boardDelete(boardVo);
		
		if(resultCnt > 0) {
			return "success";
		} else {
			throw new RuntimeException("실패.");
		}
	
	}
	
	/**
	 * 체크박스 타입별 게시글 검색기능
	 */
	@RequestMapping(value = "/board/boardSearch.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchBoardList(String boardType, PageVo pageVo,BoardVo boardVo, Model model) throws Exception{

		int page = 1;

		if(pageVo.getPageNo() == 0){
			pageVo.setPageNo(page);;
		}
		
		String[] str=boardType.split(",");
		
		List<String> List = new ArrayList<String>();
		for(int i=0;i<str.length;i++) {
			List.add(str[i]);
		}
		System.out.println("List = "+List);
		
		
		Map<String, Object> keyList = new HashMap<String, Object>();
		
		keyList.put("keyList1", List);
		keyList.put("pageNo", page);
		System.out.println("keyList = "+keyList);
		
		
		List<BoardVo> boardList = boardService.searchBoardList(keyList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardList", boardList);
		

		return map;
		
	}
	/**
	 * 체크박스 검색 기능
	 * HTML방식의 검색기능
	 * handlebars가 아닌 HTML jsp파일로 교체하는식
	 */
	/**
	@RequestMapping(value = "/board/boardSearch.do", method = RequestMethod.POST)
	public String searchBoardList(String boardType, PageVo pageVo,BoardVo boardVo, Model model) throws Exception{
		
		int page = 1;

		if(pageVo.getPageNo() == 0){
			pageVo.setPageNo(page);;
		}
		
		List<String> List = new ArrayList<String>();
		
		String[] str=boardType.split(",");
		
		for(int i=0;i<str.length;i++) {
			List.add(str[i]);
		}
		
		Map<String, Object> keyList = new HashMap<String, Object>();
		
		keyList.put("keyList", List);
		keyList.put("pageNo", page);

		List<BoardVo> boardList = boardService.searchBoardList(keyList);
		model.addAttribute("boardList", boardList);
		
		return "board/searchTable";
	
	}
	**/
	
	
	/**
	 * 회원가입
	 */
	@RequestMapping("/board/memberWrite.do")
	public String memberWrite(UserInfoVo userinfoVo, ComCodeVo comCodeVo,Model model) throws Exception {
		
		List<ComCodeVo> codeList = new ArrayList<ComCodeVo>();
		
		UserInfoVo userinfo=new UserInfoVo();
		
		codeList = boardService.codeList(comCodeVo);
		
		model.addAttribute("codeList1", codeList);
		model.addAttribute("comCodeVo", comCodeVo);
		model.addAttribute("userinfo", userinfo);
		return "board/memberWrite";
	}
	
	/**
	 * 로그인페이지
	 */
	@RequestMapping("/board/memberLogin.do")
	public String memberLogin() {
		
		
		return "board/memberLogin";
	}
	
	/**
	//로그인
	@RequestMapping(value = "/board/idchecked.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request
			, HttpServletResponse response, HttpSession session,com.spring.common.CommandMap commandMap
			,UserInfoVo userinfo) throws Exception {
		
		//사용자 정보 조회
		Map<String, Object> loginInfo = boardService.userinfoSelect(commandMap.getMap());
		//요청에 응답하기 위한 맵 객체 생성
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//로그인 정보가 있다면 로그인시킴
		if(loginInfo == null) {
			resultMap.put("status", 1);
			resultMap.put("msg", "입력하신 정보가 없습니다.");
			
		} else {
			//로그인 세션 생성
			request.getSession().setAttribute("loginInfo", userinfo);
			request.getSession().setMaxInactiveInterval(60 * 30);
			//쿠키 생성 판단 유무
			if(commandMap.get("userCookie").equals("Y")) {
				//쿠키생성해서 아이디저장
				Cookie cookie = new Cookie("loginCookie", request.getSession().getId());
				//쿠키 경로 컨텍스트 경로로 변경
				cookie.setPath("/");
				//쿠키 유효기간 7일
				cookie.setMaxAge(60*60*24*7);
				//쿠키를 response객체에 넣음
				response.addCookie(cookie);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("SESSIONID", request.getSession().getId());
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000*(60*60*24*7)));
				map.put("SESSIONLIMIT", sessionLimit);
				map.put("loginInfo", loginInfo);
				boardService.KeepLogin(map);
			}	
			resultMap.put("status", 0);
			resultMap.put("msg", "정상적으로 로그인되었습니다.");
		}
		return resultMap;
	}
	**/
	
	/**
	 * 로그인페이지
	 * 세션과 아이디저장 체크박스 값을 request.getParameter로 전달받아 저장
	 * 로그인성공하면 세션 userinfo에 유저아이디값을 담아 로그인유지에 체크박스되어있을때
	 * 쿠키를 만든뒤 날짜와 경로를 지정하고 DB에도 쿠키를저장할수있도록 설정해준다
	 */
	@RequestMapping(value = "/board/idchecked.do", method = RequestMethod.POST)
	@ResponseBody
	public int idchecked(Model model,HttpServletRequest request
			,HttpServletResponse response,CommandMap commandMap ,HttpSession session,UserInfoVo userinfo) throws Exception, ClassCastException {
		
//		String userId = request.getParameter("userId");
		String isuserCookie = request.getParameter("isuserCookie");//로그인세션쿠키
		String isuserSave = request.getParameter("isuserSave");//아이디저장
		
		System.out.println(isuserCookie);
		int result = 0;
		result = boardService.pwCheckCount(userinfo);
		
		System.out.println(result);
		
		if(result != 0) {
			result=1;
		}
		if(result == 1) {
			session.setAttribute("userinfo", boardService.userinfoSelect(userinfo.getUserId()));//키 값을 설정
			if(isuserCookie.equals("Y")) {
				Cookie cookie = new Cookie("userCookie", request.getSession().getId());
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/");
				response.addCookie(cookie);
				
				//DB에 쿠키를 저장
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("SESSIONID", request.getSession().getId());
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000*(60*60*24*7)));//60*60*24*7
				map.put("SESSIONLIMIT", sessionLimit);
				map.put("userinfo", userinfo);
				boardService.KeepLogin(map);
				
			}
			if(isuserSave.equals("Y")) {
				System.out.println("체크박스 값 = Y");
			} else {
				System.out.println("체크박스 값 = N");
			}
//			Cookie loginCookie = WebUtils.getCookie(request, "userCookie");
//			
//			if(loginCookie != null) {
//				String SESSIONID = loginCookie.getValue();
//				System.out.println("SESSIONID = "+SESSIONID);
//				UserInfoVo userInfoVo = boardService.checkUserInfoCookie(SESSIONID);
//				System.out.println("userInfoVo = "+userInfoVo);
//				if(userInfoVo != null) {
//					session.setAttribute("userinfo", userInfoVo);
//				}
//			}
		}
		return result;
	}
	
	/**
	 * 로그인 성공후 이동시키기
	 */
	@RequestMapping(value = "/board/boardList.do")
	@ResponseBody
	public String memberLoginAction(@ModelAttribute UserInfoVo userinfoVo, Model model, HttpSession session) throws Exception {
		
		//UserInfoVo userinfo=boardService.userinfoSelect(userinfoVo.getUserId());
		
		session.setAttribute("userinfo", boardService.userinfoSelect(userinfoVo.getUserId()));
		return "redirect:/board/boardList.do";
	}
	
	/**
	 * 회원가입 버튼 컨트롤러
	 */
	@RequestMapping(value = "/board/memberWriteAction.do",method = RequestMethod.POST)
	public String addMemberJoin(UserInfoVo userinfoVo, Model model) throws Exception {

		try {
			boardService.userInsert(userinfoVo);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "board/memberWrite";
		}
		
		return "board/memberLogin";
	}

	@RequestMapping("/board/IdCheckForm.do")
	public String loginCheck() {
		
		return "board/IdCheckForm";
	}
	/**
	 * 로그아웃
	 */
	@RequestMapping("/board/memberLogout.do")
	public String logout(UserInfoVo userinfo,HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Cookie loginCookie = WebUtils.getCookie(request,"userCookie");
        if ( loginCookie !=null ){
            // null이 아니면 존재하면!
        	loginCookie.setMaxAge(0);
        	loginCookie.setPath("/");
        	response.addCookie(loginCookie);
        	// 사용자 테이블에서도 유효기간을 현재시간으로 다시 세팅해줘야함.
        	Map<String, Object> map = new HashMap<String, Object>();
			map.put("SESSIONID", session.getId());
			Date sessionLimit = new Date(System.currentTimeMillis());//60*60*24*7
			map.put("SESSIONLIMIT", sessionLimit);
			map.put("userinfo", session.getAttribute("userinfo"));
			boardService.KeepLogin(map);
        }
        session.invalidate();
		return "redirect:/board/memberLogin.do";
	}
	@RequestMapping("/board/MemberModify.do")
	public String memberModify() {
		
		
		return "board/memberModify";
	}
	/**
	 * Json파일로 넘기기위해 테스트로 만든 컨트롤러
	 * @param locale
	 * @param boardVo
	 * @return Json
	 */
	@RequestMapping(value = "/board/test.do", method = RequestMethod.GET)
	@ResponseBody
	public String test(Locale locale,BoardVo boardVo) throws Exception{
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		CommonUtil commonUtil = new CommonUtil();
		
		
		result.put("test", 1111);
		result.put("test1", 2222);
		result.put("test2", 3333);
		result.put("test3", 4444);
		result.put("test4", 55555);
		
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		return callbackMsg;
	}
	
	@RequestMapping(value = "/idcheck.do", method = RequestMethod.POST)
	@ResponseBody
	public int idcheck(HttpServletRequest req) throws Exception {
		
		logger.info("idCheck");
		
		String userId = req.getParameter("userId");
		
		UserInfoVo idCheck = boardService.idcheck(userId);
		
		
		int result=0;
		
		if(idCheck != null) {
			result = 1;
		}
		
		return result;
	}
	
	
	
	
	@RequestMapping("/board/{boardType}/{boardNum}/boardRefWrite.do")
	public String refPage(
			@PathVariable("boardType")String boardType
			,@PathVariable("boardNum")int boardNum, Model model) throws Exception {
		
		BoardVo boardVo = boardService.selectBoard(boardType, boardNum);
		model.addAttribute("board", boardVo);
		
		return "board/boardRefWrite";
	}

	@RequestMapping(value = "/board/boardWriteRefAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteRef(BoardVo boardVo) throws Exception {
		boardService.boardUpdateRef(boardVo);
		boardService.boardInsertRef(boardVo);
//		int result = 0;
//		
//		result = boardService.boardInsertRef(boardVo);
//		
//		if(result !=0) {
//			result = 1;
//		}
//		
//		if(result)
		
		//System.out.println("result"+result);
		
		return "success";
	}
	@RequestMapping("/board/memberMailpassword.do")
	public String mailsend() {
		
		return "board/memberMailpassword";
	}
	
	/**
	 * 이메일 보내기 
	 */
	@RequestMapping("/board/sendpw.do")
	public String emailSenderPW(HttpServletRequest request, UserInfoVo userInfo, Model model) throws Exception {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable","true"); 
        props.put("mail.smtp.EnableSSL.enable","true");
        
//		props.put("mail.debug", "true");
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
//        props.setProperty("mail.smtp.port", "465");   
//        props.setProperty("mail.smtp.socketFactory.port", "465"); 

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			String id = "chosr1126@gmail.com";
			String password = "whtjdfbf1!@";
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(id, password);
			}
		});
		//디버그 로그출력됨 true
		session.setDebug(false);
		
		
//		MimeMessage message=mailSender.createMimeMessage();
//		MimeMessageHelper messageHelper=new MimeMessageHelper(message, true);
		String userId = request.getParameter("userId");
		String email = request.getParameter("userCompany");
		UserInfoVo pw = boardService.getPassword(userId, email);
		
		if(pw!=null) {
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("chosr1126@gmail.com"));
				InternetAddress[] address = {new InternetAddress(email)};
				message.setRecipients(MimeMessage.RecipientType.TO, address);
				message.setSubject("테스트 이메일");
				
				MimeBodyPart part1 = new MimeBodyPart();
				part1.setDataHandler(new DataHandler(new FileDataSource(new File("D:/sample.jpg"))));
				part1.setFileName("jisoo.png");
				
				MimeBodyPart part2 = new MimeBodyPart();
				part2.setContent("<p style='font-size:20px;'>"+userId+"님의 패스워드는 <p style='color: red; font-size:20px;'> ["+pw.getUserPw()+"] 입니다.","text/html; charset=utf-8");
				
				Multipart multipart = new MimeMultipart();
				
				multipart.addBodyPart(part1);
				multipart.addBodyPart(part2);
				
				message.setContent(multipart);
				Transport.send(message);
				
			} catch (MessagingException e) {
				e.printStackTrace();
//				logger.error("메일이 발송되지않았습니다.");
			}

//			messageHelper.setSubject("메일보내기 테스트.");
//			messageHelper.setText("<html><body><p style='color: red; font-size:20px;'>"+pw.getUserPw()+"</body></html>", true);
//			FileSystemResource file = new FileSystemResource(new File("D:/sample.jpg"));
//			messageHelper.addAttachment("jisoo.png", file);
//			messageHelper.setTo(email);
//			mailSender.send(message);

//			Email emailSend = new Email();
//			emailSend.setContent(userId+" 님의 패스워드는 ["+pw.getUserPw()+"] 입니다.");
//			emailSend.setSubject("java에서 보내드립니다.");
//			emailSend.setReceiver(email);
//			emailSender.SendEmail(emailSend);
			
//			ApplicationContext context=new ClassPathXmlApplicationContext("email.xml");
//			EmailSender bean=context.getBean("emailSender", EmailSender.class);
//			bean.SendEmail(emailSend);
//			((ClassPathXmlApplicationContext)context).close();
		} else {
			logger.info("아이디 또는 이메일이 잘못 입력되었습니다.");
			model.addAttribute("message", "아이디 또는 이메일이 잘못 입력되었습니다.");
			return "board/memberMailpassword";
		}
		
		
		return "redirect:/board/memberLogin.do";
	}
	
	/**
	 * 엑셀 다운로드
	 */
	@RequestMapping("/board/excelDown.do")
	public void excelDown(HttpServletResponse response
			, @RequestParam(defaultValue = "1") int pageNo, ComCodeVo comCodeVo) throws Exception {
		
		
		PageVo pageVo = new PageVo(pageNo, pageSize, boardService.selectBoardCnt(), blockSize);
		

		int endRow=boardService.selectBoardCnt();//총페이지수
		pageVo.setEndRow(endRow);//마지막까지 출력하기위해
		
		//게시판 목록부터 조회
		List<BoardVo> boardList = boardService.SelectBoardList(pageVo);
		
		//엑셀 워크북 생성
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("게시판");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		//테이블 Header 스타일
		CellStyle headerStyle = wb.createCellStyle();
		/**
		 * 얇은 경계선 스타일
		 */
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		//배경색 지정
		//headerStyle.setFillForegroundColor(HSSFColorPredefined.AQUA.getIndex());
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(FillPatternType.BRICKS);
		
		//데이터 가운데 정렬
		//headerStyle.setAlignment(HorizontalAlignment.LEFT);
		headerStyle.setAlignment(HorizontalAlignment.CENTER); //가운데 정렬
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); //중앙 정렬
		
		//폰트 설정
		Font fontOfGothic = wb.createFont();
		fontOfGothic.setFontName("나눔고딕");
		
		//데이터용 경계 스타일 테투리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		//헤더 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Type");
		cell = row.createCell(1);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("No");
		cell = row.createCell(2);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Title");
		cell = row.createCell(3);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Comment");
		cell = row.createCell(4);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Date");
		cell = row.createCell(5);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Writer");
		
		//데이터 생성
		for(BoardVo vo:boardList) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getComcodeVo().getCodeName());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardNum());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardTitle());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardComment());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			
			//0에서10까지의 자릿수만 나오게 변경 예)2020-01-21
			if(vo.getCreateTime().length() > 10 ) {
				cell.setCellValue(vo.getCreateTime().substring(0, 10));				
			}
			cell = row.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getCreator());
		}
		
		//컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		
		//저장될이름에 날짜붙여서 저장시키기위해 date사용
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS");
		Date date = new Date();
		String today = sdf.format(date);
		response.setHeader("Content-Disposition", "attachment;filename=test-"+ today +".xls");
		
		//엑셀 출력
		wb.write(response.getOutputStream());
		wb.close();
	}
	
	//페이지 호출 메소드
	@RequestMapping(value = "/board/fileUpload.do")
	public void uploadForm() {
		//fileUpload.jsp 페이지 포워딩
	}

	/**
	 * 파일 업로드
	 * @param file
	 */
	@RequestMapping(value = "/board/fileUpload.do", method = RequestMethod.POST)
	public String Fileupload(MultipartFile file
			,MultipartHttpServletRequest request
			, Model model) throws Exception{
		
		logger.info("파일이름 : "+file.getOriginalFilename());
		logger.info("파일크기 : "+file.getSize());
		logger.info("컨텐츠 타입 : "+file.getContentType());
		
		String saveName = file.getOriginalFilename();//저장이름
		File target = new File(uploadPath, saveName);//타겟 저장경로에 본래이름으로 
		
		//임시디렉토리에 저장된 업로드 파일을 지정한 디렉토리로복사
		//바이트배열, 파일객체
		FileCopyUtils.copy(file.getBytes(), target);
		
//		mv.setViewName("upload/uploadResult");
//		mv.addObject("saveName", saveName);
		model.addAttribute("saveName", saveName);
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("title", request.getParameter("title"));
		model.addAttribute("content", request.getParameter("content"));
		
		return "board/fileResult";
	}

	
	@RequestMapping(value = "/board/FileFrame.do")
	public String FileList(BoardVo boardVo, Model model
			,@RequestParam(defaultValue = "1") int pageNo) throws Exception {
		
		List<BoardVo> fileList = new ArrayList<BoardVo>();
		PageVo pageVo = new PageVo(pageNo, pageSize, boardService.selectBoardCnt(), blockSize);
		
		model.addAttribute("fileList", boardService.SelectBoardList(pageVo));
		model.addAttribute("pageVo", pageVo);
		
		return "board/FileFrame";
	}
	/**
	//다중파일 테스트
	@RequestMapping(value = "/board/fileUploadList.do")
	public ModelAndView fileUploadList(MultipartFile[] file) {
		ModelAndView mv = new ModelAndView("redirect:/board/boardList.do");
		for(int i=0; i<file.length; i++) {
            logger.debug("================== file start ==================");
            logger.debug("파일 이름: "+file[i].getName());
            logger.debug("파일 실제 이름: "+file[i].getOriginalFilename());
            logger.debug("파일 크기: "+file[i].getSize());
            logger.debug("content type: "+file[i].getContentType());
            logger.debug("================== file   END ==================");
        }
		return mv;
	}
	**/
	
	/**
	 * 파일 다운로드
	 * @param map
	 */
	@RequestMapping("/board/fileDown.do")
	public void fileDown(@RequestParam Map<String, Object> map,HttpServletResponse response) throws Exception{
		
		/** 
		 * 뷰 페이지에서 fileNum을 전달해 컨트롤러단에 던져준걸 Map을 만들어서 서비스를 호출
		 * Resultmap을 하나 만들어서담는다 거기에 파일번호로 파일원본이름과 파일저장이름을 
		 * 검색해서 그걸로 byte로 다시 불러들여 저장함 @RequestParam 필수
		**/ 
		Map<String, Object> resultMap = boardService.fileDownlad(map);
		String storedFileName = (String) resultMap.get("FILE_SAVE_NAME");
		String originalFileName = (String) resultMap.get("FILE_ORG_NAME");
		logger.info("resultMap = "+resultMap);
		logger.info("storedFileName = "+storedFileName);
		logger.info("originalFileName = "+originalFileName);
		
		//파일을 저장경로에서 다시 불러 읽어들여서 byte로 변환
		byte[] fileByte = org.apache.commons.io.FileUtils.readFileToByteArray(new File("d:\\uploadImage\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}










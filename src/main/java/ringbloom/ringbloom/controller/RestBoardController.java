/* RestBoardController.java
 * Description : REST 게시판 컨트롤러
 * ver 0.1 : 초기 구성
 * ver 0.3 : 페이징 처리
 * ver 0.6 : 댓글기능 추가
 */
package ringbloom.ringbloom.controller;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ringbloom.common.firebase.FCMService;
import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.dto.BoardFileDto;
import ringbloom.ringbloom.dto.NotificationDto;
import ringbloom.ringbloom.dto.PagingDto;
import ringbloom.ringbloom.dto.ReplyDto;
import ringbloom.ringbloom.service.BoardService;

@Slf4j
@Api(description = "게시판 REST")
@Controller
public class RestBoardController {
	
	// 비즈니스 로직을 처리하는 서비스 빈 연결
	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value = "게시판 리스트 조회")
	@RequestMapping(value = {"/board", "/board/{curPage}"}, method = RequestMethod.GET)
	public ModelAndView openBoardList(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Optional<Integer> curPage,
			@RequestParam(value = "searchType", defaultValue = "TITLE") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) throws Exception {
		ModelAndView mv = new ModelAndView("board/restBoardList");
		
		// 전체 게시물 레코드의 갯수
		int count = boardService.boardListGetCount(searchType, searchWord);
		
		// 현재 페이지 번호 받아오기
		int current;
		if(curPage.isPresent()) {
			current = curPage.get();
		} else {
			current = 1;
		}
		
		PagingDto paging = new PagingDto(count, current);
		
		int start = (current - 1) * paging.getPageScale() + 1;
		int end = (current - 1) * paging.getPageScale() + paging.getPageScale();
		
		log.debug("현재 페이지 블록 : " + paging.getCurBlock() + " 전체 페이지 블록 : " + paging.getTotalBlock());
		
		List<BoardDto> list = boardService.selectBoardList(start, end, searchType, searchWord);
		mv.addObject("list", list);
		mv.addObject("count", count);
		mv.addObject("paging", paging);
		mv.addObject("searchType", searchType);
		mv.addObject("searchWord", searchWord);
		mv.addObject("nickname", request.getSession().getAttribute("nickname"));
		mv.addObject("email", request.getSession().getAttribute("email"));
		
		return mv;
	}
	
	@ApiOperation(value = "게시글 작성 화면")
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public ModelAndView openBoardWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("board/restBoardWrite");
		mv.addObject("nickname", request.getSession().getAttribute("nickname"));
		mv.addObject("email", request.getSession().getAttribute("email"));
		
		return mv;
	}
	
	@ApiOperation(value = "게시글 등록")
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String insertBoard(HttpServletRequest request, BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.insertBoard(board, request.getSession().getAttribute("nickname").toString(), multipartHttpServletRequest);
		return "redirect:/board";
	}
	
	@ApiOperation(value = "게시글 상세 화면")
	@RequestMapping(value = "/board/{curPage}/{boardIdx}", method = RequestMethod.GET)
	public ModelAndView openBoardDetail(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("curPage") int curPage, 
			@PathVariable("boardIdx") int boardIdx,
			@RequestParam(value = "searchType", defaultValue = "TITLE") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) throws Exception {
		ModelAndView mv = new ModelAndView("board/restBoardDetail");
		int commetCount = boardService.commentListGetCount(boardIdx);
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		boardService.addHitCount(boardIdx);
		mv.addObject("curPage", curPage);
		mv.addObject("board", board);
		mv.addObject("searchType", searchType);
		mv.addObject("searchWord", searchWord);
		mv.addObject("nickname", request.getSession().getAttribute("nickname"));
		mv.addObject("email", request.getSession().getAttribute("email"));
		mv.addObject("commentCount", commetCount);
		
		return mv;
	}
	
	@ApiOperation(value = "게시글 수정 화면")
	@RequestMapping(value = "/board/{curPage}/{boardIdx}/edit", method = RequestMethod.GET)
	public ModelAndView updateBoardDetail(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("curPage") int curPage, 
			@PathVariable("boardIdx") int boardIdx,
			@RequestParam(value = "searchType", defaultValue = "TITLE") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) throws Exception {
		ModelAndView mv = new ModelAndView("board/restBoardEdit");
		int commentCount = boardService.commentListGetCount(boardIdx);
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		if(!request.getSession().getAttribute("nickname").toString().equals(board.getCreatorId()) ) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정 권한이 없습니다.'); window.location.href='/board';</script>");
			out.flush();
		}
		mv.addObject("curPage", curPage);
		mv.addObject("board", board);
		mv.addObject("searchType", searchType);
		mv.addObject("searchWord", searchWord);
		mv.addObject("nickname", request.getSession().getAttribute("nickname"));
		mv.addObject("email", request.getSession().getAttribute("email"));
		mv.addObject("commentCount", commentCount);
		
		return mv;
	}
	
	@ApiOperation(value = "게시글 수정")
	@RequestMapping(value = "/board/{curPage}/{boardIdx}/edit", method = RequestMethod.PUT)
	public String updateBoard(HttpServletRequest request, HttpServletResponse response, BoardDto board, 
			@PathVariable("curPage") int curPage, 
			@PathVariable("boardIdx") int boardIdx,
			@RequestParam(value = "searchType", defaultValue = "TITLE") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) throws Exception {
		board.setUpdaterId(request.getSession().getAttribute("nickname").toString());
		boardService.updateBoard(board);
		return "redirect:/board/" + curPage + "/" + boardIdx + "?searchType=" + searchType + "&searchWord=" + searchWord;
	}
	
	@ApiOperation(value = "게시글 삭제")
	@RequestMapping(value = "/board/{curPage}/{boardIdx}", method = RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
	
	@ApiOperation(value = "첨부파일 정보 출력")
	@RequestMapping(value = "/board/file", method = RequestMethod.GET)
	public void downloadBoardFile(HttpServletResponse response, 
			@RequestParam int idx, 
			@RequestParam int boardIdx) throws Exception {
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	@ApiOperation(value = "첨부파일 업로드")
	@RequestMapping(value = "/board/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public void fileUpload(HttpServletRequest request, BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.fileUpload(board, request.getSession().getAttribute("nickname").toString(),multipartHttpServletRequest);
	}
	
	@ApiOperation(value = "댓글 목록 보기")
	@RequestMapping(value = "/board/comment", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> viewComment(@RequestParam int boardIdx, @RequestParam(value="curPage", defaultValue = "1") int curPage) throws Exception {
		int count = boardService.commentListGetCount(boardIdx);
		
		PagingDto paging = new PagingDto(count, curPage);
		
		int start = (curPage - 1) * paging.getPageScale() + 1;
		int end = (curPage - 1) * paging.getPageScale() + paging.getPageScale();
		
		List<ReplyDto> list = boardService.selectCommentList(boardIdx, start, end);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("paging", paging);
		return map;
	}
	
	@ApiOperation(value = "댓글등록")
	@RequestMapping(value = "/board/comment/write", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertComment(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam int boardIdx, 
			@RequestParam String user_id, 
			@RequestParam String title,
			@RequestParam String comment) throws Exception {
		ReplyDto reply = new ReplyDto();
		reply.setBoardIdx(boardIdx);
		reply.setCreatorId(user_id);
		reply.setContents(comment);
		
		boardService.insertComment(reply);
		
		String creatorToken = boardService.checkToken(boardIdx);
		log.debug("작성자 토큰 : " + creatorToken);
		
		if(creatorToken != null && !user_id.equals(request.getSession().getAttribute("nickname").toString())) {
			log.debug("푸시 알림 전송 시도");
			FCMService fcmService = new FCMService();
			String sender = request.getSession().getAttribute("nickname").toString();
			String messagetitle = sender+"님이 댓글을 달았습니다.";
			String message = "[" + title + "] 글에 " + sender + "님이 댓글을 달았습니다!";
			NotificationDto notificationRequest = NotificationDto.builder()
					.title(messagetitle)
					.token(creatorToken)
					.message(message)
					.icon("/icons/android-icon-192x192.png")
					.build();
			try {
				fcmService.send(notificationRequest);
			} catch (InterruptedException | ExecutionException e) {
				log.error(e.getMessage());
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", 1);
		
		return map;
	}
	
	@ApiOperation(value = "댓글삭제")
	@RequestMapping(value = "/board/comment/delete", method = RequestMethod.POST)
	@ResponseBody
	public void deleteComment(@RequestParam int replyIdx) throws Exception {
		boardService.deleteComment(replyIdx);
	}
}

/* BoardServiceImpl.java
 * Description : 게시판 서비스 구현
 * ver 0.1 : 초기 구성
 * ver 0.3 : 게시판 CRUD 구현
 * ver 0.4 : 파일처리 추가
 * ver 0.5 : 페이징 처리
 * ver 0.8 : 댓글 기능 추가
 */
package ringbloom.ringbloom.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import ringbloom.common.FileUtils;
import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.dto.BoardFileDto;
import ringbloom.ringbloom.dto.ReplyDto;
import ringbloom.ringbloom.mapper.BoardMapper;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	// 게시물 총 갯수 카운트
	@Override
	public int boardListGetCount(String searchType, String searchWord) throws Exception {
		if(searchWord.equals("")) {
			searchWord = null;
		}
		log.debug("서치타입 : " + searchType + " 서치워드 : " + searchWord);
		return boardMapper.boardListGetCount(searchType, searchWord);
	}
	
	// 게시글 목록 조회
	@Override
	public List<BoardDto> selectBoardList(int start, int end, String searchType, String searchWord) throws Exception {
		return boardMapper.selectBoardList(start, end, searchType, searchWord);
	}
	
	// 게시글 등록
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
	}

	// 게시글 상세 화면 출력
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);  // 게시글 내용 조회
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);  // 게시글 조회수 증가
		
		return board;
	}

	// 게시글 수정
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);		
	}
	
	// 첨부파일 정보 출력
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}

	// 댓글 목록 보기
	@Override
	public List<ReplyDto> selectCommentList(int boardIdx, int start, int end) throws Exception {
		return boardMapper.selectCommentList(boardIdx, start, end);
	}

	// 댓글 등록
	@Override
	public void insertComment(ReplyDto reply) throws Exception {
		boardMapper.insertComment(reply);
	}

	// 해당 게시물 댓글 개수 카운트
	@Override
	public int commentListGetCount(int boardIdx) throws Exception {
		return boardMapper.commentListGetCount(boardIdx);
	}

	// 댓글 삭제
	@Override
	public void deleteComment(int replyIdx) throws Exception {
		boardMapper.deleteComment(replyIdx);
	}
	
	// 해당 게시물 작성자 토큰 검색
	@Override
	public String checkToken(int boardIdx) throws Exception {
		return boardMapper.checkToken(boardIdx);
	}
}

/* BoardServiceImpl.java
 * Description : 게시판 서비스 구현
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.3 : 게시판 CRUD 구현
 * ver 0.4 : 파일처리 추가
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
import ringbloom.ringbloom.mapper.BoardMapper;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	// 게시글 목록 조회
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
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
}
